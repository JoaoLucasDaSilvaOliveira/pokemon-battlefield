pipeline {
    agent any

    tools {
        jdk 'Java_21'
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
                sh 'mvn clean package -DskipTests'
            }
        }
    }

    stage('Build da Imagem Docker') {
        steps {
            // Constrói a imagem usando o Dockerfile da raiz do projeto.
            // Usamos o BUILD_NUMBER para criar tags únicas (cumprindo o item 4 da entrega)
            sh "docker build -t meu-app-springboot:latest -t meu-app-springboot:${env.BUILD_NUMBER} ."
        }
    }

    stage('Deploy no Docker (Tomcat)') {
        steps {
            // Remove o container antigo (se houver) para liberar a porta 8181 e sobe o novo
            sh '''
                docker rm -f meu-app-tomcat || true
                docker run -d -p 8181:8080 --name meu-app-tomcat meu-app-springboot:latest
            '''
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
