# Kafka mirror maker
Use the kafka mirror maker for replication, disaster recovery, aggregation, streaming, etc. This repository has the examples for my posts.

## Kafka docker image
Download my kafka docker image:

```
docker pull lozuwa/kafka:v2.5.0
``` 

Otherwise use the Dockerfile on docker_kafka if you need customization.

## mmv1
Examples for kafka mirror maker version 1.

## mmv2
Examples for kafka mirror maker version 2.

## References
* https://issues.apache.org/jira/browse/KAFKA-7500
* https://cwiki.apache.org/confluence/display/KAFKA/KIP-382%3A+MirrorMaker+2.0#KIP-382:MirrorMaker2.0-Aggregation
* https://github.com/apache/kafka/tree/2.5.0/connect/mirror
* https://kafka.apache.org/25/javadoc/org/apache/kafka/connect/mirror/DefaultReplicationPolicy.html#configure-java.util.Map-

