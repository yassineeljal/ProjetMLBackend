pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "monapp:${BUILD_NUMBER}"
        CONTAINER_NAME = "monapp-container"
    }

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
                    junit '**/target/surefire-reports/*.xml'
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
                sh 'ls -la backend/target'
                sh 'cp backend/target/backend-0.0.1-SNAPSHOT.jar .'
                sh 'ls -la'
                sh 'docker build -t monapp:${BUILD_NUMBER} -f Dockerfile.app .'
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker rm -f monapp-container || true

                existing=$(docker ps -q --filter "publish=9090")
                if [ -n "$existing" ]; then
                  echo "Un conteneur utilise déjà le port 9090, on le supprime..."
                  docker rm -f $existing
                fi

                
                docker run -d --name monapp-container -p 9090:8080 monapp:${BUILD_NUMBER}
                '''
            }
        }
    }

    post {
        success {
            echo "BUILD & DEPLOY SUCCESS"
        }
        failure {
            echo "BUILD FAILED"
        }
    }
}
