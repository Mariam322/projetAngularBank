pipeline {
    agent any

    tools {
        jdk 'JDK19'
        maven 'MAVEN3.3.9windows'
    }

    environment {
        DOCKER_REGISTRY = 'mariammseddi12'
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-19' 
        K8S_NAMESPACE = 'microservice'
        JENKINS_NOOP = "true"
        JENKINS_OPTS = "-Dorg.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL=300"
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
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('EurekaCompain') {  
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Gateway') {
                    steps {
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('Gatway') {  
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Compain Service') {
                    steps {
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('ProjetCompain') {  
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Facturation Service') {
                    steps {
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('Facturation') {  
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Depense Service') {
                    steps {
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('Depense') {  
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Bank Service') {
                    steps {
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('BanqueService') {  
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build ReglementAffectation Service') {
                    steps {
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('ReglementAffectation') {  
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

                stage('Build Documents Service') {
                    steps {
                        withMaven(maven: 'MAVEN3.3.9windows') {
                            dir('Documents') {  
                                bat 'mvn clean package -DskipTests'
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
                            bat "docker build -t mariammseddi12/eureka-server ."
                        }
                    }
                }

                stage('Build Gateway Image') {
                    steps {
                        dir('Gatway') {
                            bat "docker build -t mariammseddi12/gateway-service ."
                        }
                    }
                }

                stage('Build Compain Image') {
                    steps {
                        dir('ProjetCompain') {
                            bat "docker build -t  mariammseddi12/compain-service ."
                        }
                    }
                }

                stage('Build Facturation Image') {
                    steps {
                        dir('Facturation') {
                            bat "docker build -t  mariammseddi12/facturation-service ."
                        }
                    }
                }

                stage('Build Depense Image') {
                    steps {
                        dir('Depense') {
                            bat "docker build -t  mariammseddi12/depense-service ."
                        }
                    }
                }

                stage('Build Bank Image') {
                    steps {
                        dir('BanqueService') {
                            bat "docker build -t  mariammseddi12/bank-service ."
                        }
                    }
                }

                stage('Build ReglemetnAffecatation Image') {
                    steps {
                        dir('ReglementAffectation') {
                            bat "docker build -t  mariammseddi12/reglemetnaffecatation-service ."
                        }
                    }
                }

                stage('Build document Image') {
                    steps {
                        dir('Documents') {
                            bat "docker build -t  mariammseddi12/document-service ."
                        }
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'DockerHub',
                    passwordVariable: 'DockerHubPassword',
                    usernameVariable: 'DockerHubUsername'
                )]) {
                    bat "docker login -u mariammseddi12 -p ${env.DockerHubPassword}"
                    
                    bat "docker push mariammseddi12/eureka-server"
                    bat "docker push mariammseddi12/gateway-service"
                    bat "docker push mariammseddi12/compain-service"
                    bat "docker push mariammseddi12/facturation-service"
                    bat "docker push mariammseddi12/depense-service"
                    bat "docker push mariammseddi12/bank-service"
                    bat "docker push mariammseddi12/reglemetnaffecatation-service"
                    bat "docker push mariammseddi12/document-service"
                }
            }
        }

        stage('Deploy to OVH Kubernetes') {
            steps {
                script {
                    withKubeConfig([credentialsId: 'ovh-kubernetes-credentials']) {
                        bat """
                            kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                            
                            kubectl apply -f kubernetes/01-eureka.yaml -n ${K8S_NAMESPACE}
                            timeout /t 20 /nobreak
                            
                            kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/06-bank-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/07-reglementaffectation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/08-document-service.yaml -n ${K8S_NAMESPACE}
                        """
                        
                        // Vérifier tous les services BACKEND uniquement
                        def services = [
                            'eureka-server',
                            'gateway-service',
                            'compain-service',
                            'facturation-service',
                            'depense-service',
                            'bank-service',
                            'reglemetnaffecatation-service',
                            'document-service'
                        ]
                        
                        services.each { service ->
                            bat "kubectl rollout status deployment/${service} -n ${K8S_NAMESPACE} --timeout=300s"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                try {
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
                } catch (e) {
                    echo "Pas de tests à publier – on continue sans erreur."
                }
            }
        }
    }
}
