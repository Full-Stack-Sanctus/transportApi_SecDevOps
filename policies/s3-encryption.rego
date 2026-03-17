package main

deny[reason] {
  resource := input.resource_changes[_]
  resource.type == "aws_s3_bucket"
  not resource.change.after.server_side_encryption_configuration
  reason := sprintf("S3 Bucket %v does not have encryption enabled", [resource.name])
}
