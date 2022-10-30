pipeline {
    agent {
        docker { image 'openjdk:17-jdk-slim' }
    }
    stages {
        stage('Build') {
            steps {
                echo "branch: ${env.BRANCH_NAME}"
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
                expression { env.BRANCH_NAME == 'main' }
            }
            steps {
                echo 'Deploying...'
            }
        }
    }
}
