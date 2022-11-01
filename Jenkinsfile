pipeline {
    agent {
        docker { image 'openjdk:17-jdk-slim' }
    }
    stages {
        stage('Build') {
            steps {
                echo "job: ${env.JOB_NAME}"
                sh 'java --version'
                echo 'Building...'
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
}
