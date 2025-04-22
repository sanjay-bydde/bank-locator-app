pipeline {
    agent any

    tools {
        maven 'Maven_3.9.9' // Ensure Maven is configured under "Global Tool Configuration"
    }

    stages {
        stage('Clone') {
            steps {
                // Cloning the repository from GitHub
                git 'https://github.com/sanjay-bydde/bank-locator-app.git'
            }
        }

        stage('Build') {
            steps {
                // Building the .war file using Maven
                bat 'mvn clean package'
            }
        }

        stage('Run WAR') {
            steps {
                // Run the WAR file in the background using "start" on Windows
                bat 'start java -jar target\\SpringDemo.war'
            }
        }
    }
}
