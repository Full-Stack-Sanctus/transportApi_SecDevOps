# 🔐 Secure DevOps CI/CD Project

## 🔧 Tools We'll Use
| Purpose                     | Tools                                 |
| --------------------------- | ------------------------------------- |
| Build & Test                | Maven / Gradle                        |
| Static Code Analysis (SAST) | **SpotBugs**, **PMD**, **Checkstyle** |
| Dependency Scanning         | **OWASP Dependency-Check**            |
| Container Security          | Docker + **Trivy**                    |
| Secrets Detection           | **Gitleaks**                          |
| Infrastructure (optional)   | Terraform                             |
| Secrets Management          | HashiCorp Vault / Doppler             |
| CI/CD                       | GitHub Actions                        |


## 👨‍💻 Tech Stack
- Java + Docker + GitHub Actions
- Trivy, Terraform, Vault

## 📦 Features
- Secure CI pipeline
- Container scanning
- Infra as Code deployment
- Secrets management

## 🛡️ Security Tools
- ✅ Trivy
- ✅ Vault

## 🐳 DockerFile
- A dockerfile is in repo root directory, the dockerfile is to install necessary tools for this project in the jenkins container


Note: Errors have been alloweed in my code to demonstrate SecDevOps operations, using the tools above.