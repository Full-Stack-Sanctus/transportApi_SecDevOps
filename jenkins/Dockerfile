FROM jenkins/jenkins:lts

USER root

# Install Java, Maven, Git, and dependencies
RUN apt-get update && \
    apt-get install -y \
        openjdk-17-jdk \
        maven \
        git \
        unzip \
        curl \
        wget \
        gnupg \
        software-properties-common \
        ca-certificates \
        jq \
        lsb-release && \
    apt-get clean

# Set JAVA environment
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Terraform
ENV TERRAFORM_VERSION=1.6.6
RUN wget https://releases.hashicorp.com/terraform/${TERRAFORM_VERSION}/terraform_${TERRAFORM_VERSION}_linux_amd64.zip && \
    unzip terraform_${TERRAFORM_VERSION}_linux_amd64.zip && \
    mv terraform /usr/local/bin/ && \
    rm terraform_${TERRAFORM_VERSION}_linux_amd64.zip

# Install OPA
RUN curl -L -o opa https://openpolicyagent.org/downloads/latest/opa_linux_amd64 && \
    chmod +x opa && \
    mv opa /usr/local/bin/

# Install Conftest
ENV CONFTEST_VERSION=0.46.0
RUN wget https://github.com/open-policy-agent/conftest/releases/download/v${CONFTEST_VERSION}/conftest_${CONFTEST_VERSION}_Linux_x86_64.tar.gz && \
    tar -xzf conftest_${CONFTEST_VERSION}_Linux_x86_64.tar.gz && \
    mv conftest /usr/local/bin/ && \
    rm conftest_${CONFTEST_VERSION}_Linux_x86_64.tar.gz

# Optional: Install tfsec (for Compliance as Code)
RUN curl -s https://raw.githubusercontent.com/aquasecurity/tfsec/master/scripts/install_linux.sh | bash

# Install pipx and checkov
RUN apt-get update && \
    apt-get install -y pipx && \
    pipx ensurepath && \
    pipx install checkov


# Optional: Install yq (for YAML processing in scripts)
RUN wget https://github.com/mikefarah/yq/releases/download/v4.43.1/yq_linux_amd64 -O /usr/local/bin/yq && \
    chmod +x /usr/local/bin/yq

# Switch back to Jenkins user
USER jenkins
