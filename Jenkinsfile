pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh "./gradlew clean build"
            }
        }

        stage('Test') {
            steps {
                sh "./gradlew test"
            }
        }

        stage('Package') {
            steps {
                sh "./gradlew assemble"
            }
        }
        stage('Run') {
            steps {
                sh './gradlew bootRun &'
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
