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
//                 sh "chmod +x gradlew"
//                 sh "docker version"
                bat "docker version"
            }
        }

        stage('Unit Test') {
             steps {
//                  sh "./gradlew test"
                 bat "./gradlew test"
             }
        }
        stage("Code Coverage") {
              steps {
//                   sh "./gradlew jacocoTestReport"
                  bat "./gradlew jacocoTestReport"
                  publishHTML(target: [
                                      allowMissing: false,
                                      alwaysLinkToLastBuild: false,
                                      keepAll: true,
                                      reportDir: 'build/reports/jacoco/test/html',
                                      reportFiles: 'index.html',
                                      reportName: 'JaCoCo Report'
                                  ])
//                   sh "./gradlew jacocoTestCoverageVerification"
                  bat "./gradlew jacocoTestCoverageVerification"
              }
        }
        stage("Static code analysis") {
                       steps {
//                            sh "./gradlew checkstyleMain"
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
//                     sh './gradlew clean'
//                     sh './gradlew build'
                    bat "./gradlew clean"
                    bat "./gradlew build"
                }
            }
            stage('docker build') {
                steps {
//                      sh "docker build -t calculator1."
                        bat "docker build -t khaddy08/calculator1."
               }
            }
            stage('docker push') {
                steps {
//                         sh "docker push calculator1."
                           bat "docker push khaddy08/calculator1."
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
