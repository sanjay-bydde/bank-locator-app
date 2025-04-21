pipeline { 
    agent any

    environment {
        WAR_NAME = 'SpringDemo-0.0.1-SNAPSHOT.war'
        TOMCAT_WEBAPPS = 'C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps'
        CONTEXT_PATH = 'SpringDemo'
        WAR_DEST = "${TOMCAT_WEBAPPS}\\${CONTEXT_PATH}.war"
        TOMCAT_URL = 'http://localhost:8081'
    }

    tools {
        maven 'Maven_3.9.9' // Make sure this name matches your Jenkins Maven installation
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
                script {
                    echo "🧹 Removing existing WAR and exploded folder..."
                    bat "del /Q /F \"${WAR_DEST}\""
                    bat "rmdir /S /Q \"${TOMCAT_WEBAPPS}\\${CONTEXT_PATH}\""

                    echo "📦 Copying new WAR file to Tomcat webapps..."
                    bat "copy \"target\\${WAR_NAME}\" \"${WAR_DEST}\""
                }
            }
        }

        stage('Test Deployment') {
            steps {
                echo '🧪 Testing the deployed application...'
                // Wait a bit to let Tomcat deploy the WAR
                sleep time: 10, unit: 'SECONDS'
                script {
                    def testUrl = "${TOMCAT_URL}/${CONTEXT_PATH}/banks/nearby?zipcode=10001"
                    def response = httpRequest url: testUrl, validResponseCodes: '200'
                    echo "✅ Response: ${response.status} - App is deployed and responding!"
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
