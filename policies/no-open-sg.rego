package main

deny[reason] {
  resource := input.resource_changes[_]
  resource.type == "aws_security_group"
  ingress := resource.change.after.ingress[_]
  cidr := ingress.cidr_blocks[_]
  cidr == "0.0.0.0/0"
  reason := sprintf("Security Group %v allows ingress from 0.0.0.0/0", [resource.name])
}
