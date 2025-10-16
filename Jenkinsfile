pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yassineeljal/ProjetMLBackend.git'
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
                sh 'cp backend/target/*.jar .'

                sh 'docker build -t monapp:${BUILD_NUMBER} -f Dockerfile.app .'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker rm -f monapp-container || true'

                sh 'docker run -d --name monapp-container -p 8080:8080 monapp:${BUILD_NUMBER}'
            }
        }
    }

    post {
        success {
            echo 'BUILD & DEPLOY SUCCESS — ton backend tourne dans Docker !'
        }
        failure {
            echo 'BUILD FAILED — vérifie les logs Jenkins pour corriger les erreurs.'
        }
    }
}
