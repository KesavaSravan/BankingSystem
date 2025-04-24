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
