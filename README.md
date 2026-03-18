# 🚀 Transport API – SecDevOps-Driven Spring Boot Project

## 📌 Overview

This project is a **Spring Boot-based Transport API** designed with a strong emphasis on **Security, DevOps, and Infrastructure as Code (SecDevOps)** principles.

It demonstrates how to integrate:

* Continuous Integration / Continuous Deployment (CI/CD)
* Infrastructure as Code (Terraform)
* Policy as Code (OPA & Conftest)
* Compliance as Code (Checkov)
* Containerization (Docker)
* Artifact storage (AWS S3)

The pipeline is fully automated using **Jenkins**, ensuring that security and compliance checks are enforced at every stage of development and deployment.

---

## 🏗️ Architecture Overview

The system follows a **SecDevOps pipeline architecture**:

1. Developer pushes code → GitHub
2. Jenkins pipeline is triggered
3. Application is built (Maven)
4. Artifact (JAR) uploaded to AWS S3
5. Infrastructure is provisioned using Terraform
6. Security policies are enforced (OPA, Conftest)
7. Compliance scanning is executed (Checkov)
8. Docker image is built and pushed to GHCR
9. Deployment is executed (manual approval required)

---

## 🔐 SecDevOps Principles Implemented

### 1. Shift-Left Security

Security checks are integrated early in the pipeline:

* Terraform plans are validated before deployment
* Policies are enforced before infrastructure changes

### 2. Policy as Code

Implemented using:

* **OPA (Open Policy Agent)**
* **Conftest**

Ensures:

* No insecure infrastructure is provisioned
* Custom organizational rules are enforced

### 3. Compliance as Code

Using **Checkov**:

* Scans Terraform configurations
* Detects misconfigurations and security risks

### 4. Immutable Infrastructure

* Infrastructure is provisioned using Terraform
* Changes are version-controlled and reproducible

### 5. Secrets Management

* AWS credentials are securely injected via Jenkins credentials
* No hardcoded secrets in the codebase

---

## ⚙️ Jenkins Pipeline Breakdown

### 🔹 1. Checkout

* Pulls source code from GitHub repository

### 🔹 2. Build

* Uses Maven to package the Spring Boot application

### 🔹 3. Upload Artifact to S3

* Uploads the generated JAR file to AWS S3 for storage and deployment

### 🔹 4. Terraform Init

* Initializes Terraform working directory

### 🔹 5. Terraform Plan

* Generates execution plan
* Outputs JSON for policy evaluation

### 🔹 6. Conftest Policy Check

* Validates Terraform plan against custom policies
* Fails pipeline if violations are found

### 🔹 7. OPA Policy Check

* Runs advanced policy evaluation
* Blocks insecure infrastructure provisioning

### 🔹 8. Terraform Apply (Manual Approval)

* Requires human approval before applying changes
* Prevents accidental deployments

### 🔹 9. Checkov Scan

* Performs compliance scanning
* Outputs results as JSON artifact

### 🔹 10. Docker Build & Push

* Builds Docker image
* Pushes to GitHub Container Registry (GHCR)

### 🔹 11. Testing

* Executes unit tests using Maven

### 🔹 12. Deployment

* Placeholder for deployment logic (e.g., Kubernetes, EC2, ECS)

---

## 🧰 Technologies Used

| Category         | Tool/Technology           |
| ---------------- | ------------------------- |
| Backend          | Spring Boot (Java)        |
| CI/CD            | Jenkins                   |
| Build Tool       | Maven                     |
| IaC              | Terraform                 |
| Policy as Code   | OPA, Conftest             |
| Compliance       | Checkov                   |
| Containerization | Docker                    |
| Registry         | GitHub Container Registry |
| Cloud            | AWS (S3)                  |

---

## 📂 Project Structure

```
transportApi_SecDevOps/
│
├── src/                    # Spring Boot source code
├── target/                 # Compiled JAR output
├── terraform/              # Infrastructure as Code
│   ├── policies/           # OPA/Conftest policies
│   ├── *.tf                # Terraform configuration files
│
├── Dockerfile              # Docker image definition
├── Jenkinsfile             # CI/CD pipeline definition
└── README.md               # Project documentation
```

---

## 🚦 How to Run Locally

### 1. Clone Repository

```
git clone https://github.com/Full-Stack-Sanctus/transportApi_SecDevOps.git
cd transportApi_SecDevOps
```

### 2. Build Application

```
mvn clean package
```

### 3. Run Application

```
java -jar target/app.jar
```

---

## 🐳 Docker Usage

### Build Image

```
docker build -t transport-api .
```

### Run Container

```
docker run -p 8080:8080 transport-api
```

---

## 🌍 Terraform Usage

### Initialize

```
cd terraform
terraform init
```

### Plan

```
terraform plan
```

### Apply

```
terraform apply
```

---

## 🛡️ Security Highlights

* Automated policy enforcement before infrastructure changes
* Compliance scanning integrated into CI/CD
* Secure credential handling via Jenkins
* Manual approval gate for production changes
* Artifact integrity ensured via centralized storage (S3)

---

## 📊 Future Improvements

* Integrate Kubernetes deployment (EKS)
* Add API security (OAuth2 / JWT)
* Introduce SAST/DAST tools (e.g., SonarQube, OWASP ZAP)
* Implement monitoring (Prometheus + Grafana)
* Add centralized logging (ELK stack)

---

## 👨‍💻 Author

**Sanctus (Full-Stack-Sanctus)**

Passionate about building **secure, scalable, and automated systems** using modern DevOps and cloud-native practices.

---

## 📜 License

This project is open-source and available under the MIT License.
