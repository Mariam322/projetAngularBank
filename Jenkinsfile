pipeline {
    agent any

    tools {
        jdk 'jdk-17'
    }

    environment {
        DOCKER_REGISTRY = 'mariammseddi12' 
    }

    stages {
        stage('code') {
            steps {
                git url: 'https://github.com/Mariam322/projetAngularBank.git', branch: 'main'
            }
        }

        stage('Build and Package') {
            parallel {
                stage('Build Eureka') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('EurekaCompain') {  
                                sh '$MVN_CM Dmvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Gateway') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('Gatway') {  
                                sh '$MVN_CMD mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Compain Service') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('ProjetCompain') {  
                                sh '$MVN_CMD mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Facturation Service') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('Facturation') {  
                                sh '$MVN_CMD mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Depense Service') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('Depense') {  
                                sh '$MVN_CMD mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Bank Service') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('BanqueService') {  
                                sh '$MVN_CMD mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build ReglementAffectation Service') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('ReglementAffectation') {  
                                sh '$MVN_CMD mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Documents Service') {
                    steps {
                        withMaven(maven: 'maven-3.6.3') {
                            dir('Documents') {  
                                sh '$MVN_CMD mvn clean package -DskipTests'
                            }
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            parallel {
                stage('Build Eureka Image') {
                    steps {
                        dir('EurekaCompain') {
                            sh "docker build -t mariammseddi12/eureka-server ."
                        }
                    }
                }

                stage('Build Gateway Image') {
                    steps {
                        dir('Gatway') {
                            sh "docker build -t mariammseddi12/gateway-service ."
                        }
                    }
                }

                stage('Build Compain Image') {
                    steps {
                        dir('ProjetCompain') {
                            sh "docker build -t  mariammseddi12/compain-service ."
                        }
                    }
                }

                stage('Build Facturation Image') {
                    steps {
                        dir('Facturation') {
                            sh "docker build -t  mariammseddi12/facturation-service ."
                        }
                    }
                }

                stage('Build Depense Image') {
                    steps {
                        dir('Depense') {
                            sh "docker build -t  mariammseddi12/depense-service ."
                        }
                    }
                }

                stage('Build Bank Image') {
                    steps {
                        dir('BanqueService') {
                            sh "docker build -t  mariammseddi12/bank-service ."
                        }
                    }
                }

                stage('Build ReglemetnAffecatation Image') {
                    steps {
                        dir('ReglementAffectation') {
                            sh "docker build -t  mariammseddi12/ReglemetnAffecatation-service ."
                        }
                    }
                }

                stage('Build document Image') {
                    steps {
                        dir('Documents') {
                            sh "docker build -t  mariammseddi12/document-service ."
                        }
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'Docker_Hub',
                    passwordVariable: 'DockerHubPassword',
                    usernameVariable: 'DockerHubUsername'
                )]) {
                    sh "docker login -u mariammseddi12 -p ${env.DockerHubPassword}"
                    
                    sh "docker push mariammseddi12/eureka-server"
                    sh "docker push mariammseddi12/gateway-service"
                    sh "docker push mariammseddi12/compain-service"
                    sh "docker push mariammseddi12/facturation-service"
                    sh "docker push mariammseddi12/depense-service"
                    sh "docker push mariammseddi12/bank-service"
                    sh "docker push mariammseddi12/ReglemetnAffecatation-service"
                    sh "docker push mariammseddi12/document-service"
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker-compose down && docker-compose up -d'
            }
        }
    }
}

