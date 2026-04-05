pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = "your-docker-id/string-service:${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/devops-eval-yourname.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test package'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Push to Registry') {
            steps {
                // Requires Jenkins Docker Credentials configured
                echo "Tagging and pushing ${DOCKER_IMAGE}..."
                // sh "docker push ${DOCKER_IMAGE}"
            }
        }
    }
}
