#!/usr/bin/env bash
echo "begin"
./protoc -I=./proto/ --java_out=/Users/zhangzhiyong/IdeaProjects/myserver/common/src/main/java/ ./proto/*.proto
echo "end"