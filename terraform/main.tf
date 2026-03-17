module "vpc" {
  source    = "./modules/vpc"
  vpc_cidr  = "10.0.0.0/16"
  vpc_name  = "MyMainVPC"
}

# resource "aws_vpc" "main" {
  # cidr_block = "10.0.0.0/16"
  # tags = {
    # Name = "springboot-vpc"
  # }
# }

resource "aws_internet_gateway" "gw" {
  vpc_id = module.vpc.vpc_id
}

resource "aws_subnet" "public" {
  vpc_id                  = module.vpc.vpc_id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = true
}

resource "aws_security_group" "springboot_sg" {
  vpc_id = module.vpc.vpc_id
  name   = "springboot-sg"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [var.my_ip]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "app" {
  ami           = "ami-0c02fb55956c7d316" # Ubuntu 20.04
  instance_type = "t2.micro"
  subnet_id     = aws_subnet.public.id
  vpc_security_group_ids = [aws_security_group.springboot_sg.id]

  user_data = <<-EOF
              #!/bin/bash
              sudo apt update -y
              sudo apt install openjdk-17-jdk -y
              wget https://my-bucket.s3.amazonaws.com/app.jar -O /home/ubuntu/app.jar
              nohup java -jar /home/ubuntu/app.jar > /home/ubuntu/app.log 2>&1 &
              EOF

  tags = {
    Name = "SpringBootServer"
  }
}
