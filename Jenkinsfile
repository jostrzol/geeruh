pipeline {
  agent {
    docker { image 'openjdk:17-jdk-slim' }
  }
  stages {
    stage('Build') {
      steps {
        sh 'java --version'
        echo 'Building...'
      }
    }
    stage('Test') {
      steps {
        echo 'Testing...'
      }
    }
    if(env.BRANCH_NAME == 'main'){
      stage('Deploy') {
        steps {
          echo 'Deploying...'
        }
      }
    }
  }
}
