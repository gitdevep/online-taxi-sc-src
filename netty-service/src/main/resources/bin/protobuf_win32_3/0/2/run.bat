echo "begin"
protoc.exe -I=./proto/ --java_out=E:\work\yesincarServer\yesincar\nettyService\src\main\java\ ./proto/*.proto
echo "end"
