pipeline {
    agent {
        docker {
            image 'openjdk:17-jdk-slim'
            args  '-v /var/cache/gradle:/tmp/gradle-user-home:rw'
        }
    }
    environment {
        HOME = '/home/azureuser'
        GRADLE_CACHE = '/tmp/gradle-user-home'
        NEXUS = credentials('nexus-user-credentials')
        LAUNCH = credentials('launch-azure')
    }
    stages {
        stage('Load cache') {
            steps {
                // Copy the Gradle cache from the host, so we can write to it
                sh "rsync -a --include /caches --include /wrapper --exclude '/*' ${GRADLE_CACHE}/ ${HOME}/.gradle || true"
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }

        // stage('Tests') {
        //     parallel {
        //         stage('Checkstyle') {
        //             steps {
        //                 sh './gradlew checkstyleMain'
        //             }
        //         }

        //         stage('Test') {
        //             steps {
        //                 sh './gradlew test'
        //             }
        //         }
        //     }
        // }
        stage('Final steps') {
            parallel {
                stage('PR Coverage to Github') {
                    when {
                        expression { env.JOB_NAME != 'Deployment' }
                    }
                    steps {
                        script {
                            currentBuild.result = 'SUCCESS'
                        }
                        step([$class: 'CompareCoverageAction', publishResultAs: 'statusCheck', scmVars: [GIT_URL: env.GIT_URL]])
                    }
                }

                stage('Main Coverage to Github') {
                    when {
                        expression { env.JOB_NAME == 'Deployment' }
                    }
                    steps {
                        script {
                            currentBuild.result = 'SUCCESS'
                        }
                        step([$class: 'MasterCoverageAction', scmVars: [GIT_URL: env.GIT_URL]])
                    }
                }

                stage('Deploy') {
                    when {
                        expression { env.JOB_NAME == 'Deployment' }
                    }
                    steps {
                        sh "./gradlew -DnexusUsername=${env.NEXUS_USR} -DnexusPassword=${env.NEXUS_PSW} publish"
                    }
                }
                stage('Launch') {
                    when {
                        expression { env.JOB_NAME == 'Deployment' }
                    }
                    steps {
						sh "apt-get update && apt-get install ssh -y"
						script{
							remote = [:]
							remote.name = "name"
							remote.host = "34.118.59.193"
							remote.allowAnyHosts = true
							remote.failOnError = true
							remote.user = env.LAUNCH_USR
							remote.password = env.LAUNCH_PSW
							sshCommand remote: remote, command: "sudo nohup ./launch.sh" , sudo: true// &> /dev/null"
						}
                    }
                }
            }
        }
    }
    post {
        success {
            // Write updates to the Gradle cache back to the host
            sh "rsync -au ${HOME}/.gradle/caches ${HOME}/.gradle/wrapper ${GRADLE_CACHE}/ || true"
        }
    }
}
