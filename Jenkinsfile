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
                echo 'Testing...'
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
