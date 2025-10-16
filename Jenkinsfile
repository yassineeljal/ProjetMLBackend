pipeline {
  agent any
  options { skipDefaultCheckout(true) }
  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        dir('backend') {
          sh 'chmod +x mvnw'
          sh './mvnw -B clean test'
        }
      }
      post {
        always {
          junit 'backend/target/surefire-reports/*.xml'
        }
      }
    }

    stage('Package') {
      steps {
        dir('backend') {
          sh './mvnw -B package -DskipTests=false'
        }
      }
    }

    stage('JaCoCo (verify)') {
      steps {
        dir('backend') {
          sh './mvnw -B verify'
        }
      }
    }

    stage('Docker Build (monapp:${BUILD_NUMBER})') {
      steps {
        sh 'docker build -t monapp:${BUILD_NUMBER} -f Dockerfile.app .'
      }
    }
  }
  post {
    success {
      archiveArtifacts artifacts: 'backend/target/*.jar', fingerprint: true
      echo "Build ${env.BUILD_NUMBER} OK"
    }
  }
}
