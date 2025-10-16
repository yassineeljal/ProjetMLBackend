pipeline {
  agent any

  options {
    timestamps()
    skipDefaultCheckout(true)
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        dir('backend') {
          sh 'mvn -B clean test'
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
          sh 'mvn -B package'
        }
      }
      post {
        success {
          archiveArtifacts artifacts: 'backend/target/*.jar', fingerprint: true
        }
      }
    }

    stage('JaCoCo (verify)') {
      steps {
        dir('backend') {
          sh 'mvn -B verify'
        }
      }
      post {
        always {
          jacoco(
            execPattern: 'backend/target/jacoco.exec',
            classPattern: 'backend/target/classes',
            sourcePattern: 'backend/src/main/java',
            changeBuildStatus: true
          )
          publishHTML(target: [
            reportDir: 'backend/target/site/jacoco',
            reportFiles: 'index.html',
            reportName: 'JaCoCo HTML'
          ])
        }
      }
    }

    stage('Docker Build (monapp:${BUILD_NUMBER})') {
      steps {
        sh 'docker build -t monapp:${BUILD_NUMBER} -f Dockerfile.app .'
        sh 'docker images | head -n 10'
      }
    }
  }
}
