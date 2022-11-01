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

        stage('Checkstyle') {
            steps {
                echo './gradlew checkstyleMain'
            }
        }

        stage('Test') {
            steps {
                echo './gradlew test'
            }
            post {
                success {
                    script {
                        // if we are in a PR
                        if (env.CHANGE_ID) {
                            // TODO: change from fixed to SonarQube (requires SonarQube configuration)
                            publishCoverageGithub(filepath:'build/reports/jacoco/test/jacocoTestReport.xml', coverageXmlType: 'jacoco', comparisonOption: [ value: 'optionFixedCoverage', fixedCoverage: '0.10' ], coverageRateType: 'Line')
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            when {
                expression { env.JOB_NAME == 'Deployment' }
            }
            steps {
                echo 'Deploying...'
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
