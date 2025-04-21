pipeline {
    agent any

    environment {
        WAR_NAME = 'SpringDemo-0.0.1-SNAPSHOT.war'
        TOMCAT_URL = 'http://localhost:8081'
        CONTEXT_PATH = 'SpringDemo'
    }

    stages {
        stage('Build WAR') {
            steps {
                echo '🔨 Building WAR file...'
                bat "mvn clean package"
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                echo '🚀 Deploying WAR to Tomcat...'
                deploy adapters: [tomcat9(credentialsId: 'tomcat-creds', url: "${TOMCAT_URL}")],
                       contextPath: "${CONTEXT_PATH}",
                       war: "target/${WAR_NAME}"
            }
        }

        stage('Test Deployment') {
            steps {
                echo '🧪 Testing the deployed application...'
                script {
                    def response = httpRequest url: "${TOMCAT_URL}/${CONTEXT_PATH}/", validResponseCodes: '200'
                    echo "✅ Response: ${response.status} - App is deployed!"
                }
            }
        }
    }

    post {
        success {
            echo '🎉 Deployment successful!'
        }
        failure {
            echo '❌ Deployment failed.'
        }
    }
}
