#!/bin/sh
echo "Begin: SQS -> Create Topic"
#
awslocal sqs create-queue \
    --region us-east-1 \
    --cli-input-json file://sqs-topic-definition.json
#
echo "End: SQS -> Create Topic"
#
echo "Begin: DynamoDB -> Create Table"
#
awslocal dynamodb create-table \
    --region us-east-1 \
    --cli-input-json file://dynamodb-table-definition.json
#
echo "End: DynamoDB -> Create Table"
#
echo "Begin: DynamoDB -> List Tables"
#
awslocal dynamodb list-tables
#
echo "End: DynamoDB -> List Tables"
#
echo "Begin: DynamoDB -> Load Base User"
#
awslocal dynamodb batch-write-item \
  --region us-east-1 \
  --request-items file://data/base-user.json
echo "End: DynamoDB -> Load Base User"
#