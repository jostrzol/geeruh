pipeline {
  agent {
    docker { image 'openjdk:17-jdk-slim' }
  }
  stages {
    stage('Config Test') {
      steps {
        sh 'java --version'
      }
    }
  }
}
