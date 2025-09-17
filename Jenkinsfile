pipeline {
    agent { label 'Pipeline' }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/KhaddyX/calculator.git', branch: 'main'
            }
        }
        stage('Compile') {
            steps {
                bat "docker version"
            }
        }
        stage('Unit Test') {
            steps {
                bat "./gradlew test"
            }
        }
        stage("Code Coverage") {
            steps {
                bat "./gradlew jacocoTestReport"
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Report'
                ])
                bat "./gradlew jacocoTestCoverageVerification"
            }
        }
        stage("Static code analysis") {
            steps {
                bat "./gradlew checkstyleMain"
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'build/reports/checkstyle',
                    reportFiles: 'main.html',
                    reportName: 'Checkstyle Report'
                ])
            }
        }
        stage('Build Jar') {
            steps {
                bat "./gradlew clean"
                bat "./gradlew build"
            }
        }
        stage('docker build') {
            steps {
                bat "dir Dockerfile" // Verify Dockerfile exists
                bat "docker build -t khaddy08/calculator1:latest ."
            }
        }
        stage('docker push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push khaddy08/calculator1:latest"
                }
            }
        }
    }
    post {
        always {
            mail to: 'silverkhaddy@gmail.com',
                subject: "Completed Pipeline: ${currentBuild.fullDisplayName}",
                body: "Your build completed, please check: ${env.BUILD_URL}"
            slackSend channel: '#test', color: 'red', message: "The pipeline ${currentBuild.fullDisplayName} result."
        }
    }
}