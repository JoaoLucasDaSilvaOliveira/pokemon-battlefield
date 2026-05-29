pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {
        stage('Clonar repositório') {
            steps {
                checkout scm
            }
        }

        stage('Build com Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Testes') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }

        success {
            echo 'BUILD EXECUTADO COM SUCESSO'
        }

        failure {
            echo 'BUILD FALHOU'
        }
    }
}