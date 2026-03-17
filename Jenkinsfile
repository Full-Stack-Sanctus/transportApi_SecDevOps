pipeline {
    agent any

    environment {
        TF_VERSION = ">=1.5.0"
        CHECKOV_OUTPUT = "checkov-results.json"
        POLICY_DIR = "policies/"
        PLAN_JSON = "terraform.plan.json"
    }

    tools {
        maven 'Maven 3'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'f6bdf992-9da3-49fa-bf54-06bcc93df8ac',
                    url: 'https://github.com/Full-Stack-Sanctus/transportApi_SecDevOps.git',
                    branch: 'main'
            }
        }
        
        stage('Build') {
            steps {
                echo '📦 Building the Spring Boot project...'
                bat 'mvn clean package -DskipTests'
            }
        }
        
        stage('Upload JAR to S3') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    bat '''
                    echo ☁️ Uploading app.jar to S3...
                    aws s3 cp target\\app.jar s3://my-bucket/app.jar
                    '''
                }
            }
        }

        stage('Terraform Init') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    dir('terraform') {
                        bat 'terraform init'
                    }
                }
            }
        }
    
        stage('Terraform Plan') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    dir('terraform') {
                        bat '''
                        terraform plan -out=tfplan
                        terraform show -json tfplan > terraform.plan.json
                        '''
                    }
                }
            }
        }

        stage('Run Policy as Code (Conftest)') {
            steps {
                dir('terraform') {
                    bat '''
                    echo ⚡ Running Conftest...
                    conftest test %PLAN_JSON% --policy %POLICY_DIR%
                    IF %ERRORLEVEL% NEQ 0 (
                        echo ❌ Conftest failed. Policy violations detected.
                        exit /b 1
                    )
                    '''
                }
            }
        }

        stage('Run Policy as Code (OPA)') {
            steps {
                dir('terraform') {
                    bat '''
                    echo 🔎 Running OPA Policy Checks...
                    opa eval --input terraform.plan.json --data policies/ --format=pretty "data.terraform.deny" > policy-results.txt

                    type policy-results.txt

                    findstr "deny" policy-results.txt
                    IF %ERRORLEVEL% EQU 0 (
                        echo ❌ OPA Policy check failed.
                        exit /b 1
                    ) ELSE (
                        echo ✅ OPA Policy check passed.
                    )
                    '''
                }
            }
        }

        stage('Terraform Apply') {
            when {
                expression { return currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                input "Approve to Apply?"
                withCredentials([
                    string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    dir('terraform') {
                        bat 'terraform apply tfplan'
                    }
                }
            }
        }

        stage('Run Compliance as Code (Checkov)') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-secret-access-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    dir('terraform') {
                        bat '''
                        echo 🧪 Running Checkov scan...
                        checkov -d . --output json > checkov-results.json
                        '''
                    }
                    archiveArtifacts artifacts: 'terraform/checkov-results.json'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'ghcr-creds', 
                    usernameVariable: 'DOCKER_USERNAME', 
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    script {
                        def imageName = "ghcr.io/full-stack-sanctus/transport-api"
                        def imageTag = "v${env.BUILD_NUMBER}"

                        bat """
                        docker build -f Dockerfile -t ${imageName}:${imageTag} .
                        echo %DOCKER_PASSWORD% | docker login ghcr.io -u %DOCKER_USERNAME% --password-stdin
                        docker push ${imageName}:${imageTag}
                        """
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running tests...'
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Deploying application (placeholder step)'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully.'
        }
        failure {
            echo '❌ Pipeline failed.'
            // Commented out to avoid SMTP error
            // mail to: 'secops@company.com',
            // subject: "Terraform Pipeline Failed ❌",
            // body: "Terraform or policy checks failed. Review Jenkins logs for full details."
        }
    }
}