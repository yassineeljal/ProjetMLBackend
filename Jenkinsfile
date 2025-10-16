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
    }

    post {
        success {
            echo '✅ BUILD SUCCESS — Tout fonctionne parfaitement !'
        }
        failure {
            echo '❌ BUILD FAILED — Vérifie les logs pour identifier le problème.'
        }
    }
}
