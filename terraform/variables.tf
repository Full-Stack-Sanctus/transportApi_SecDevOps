variable "aws_region" {
  default = "us-east-1"
}

variable "my_ip" {
  description = "Your public IP for SSH access"
  type        = string
}
