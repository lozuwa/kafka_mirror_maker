docker stack deploy -c source-kafka/zookeeper.yaml mmv2
docker stack deploy -c source-kafka/kafka.yaml mmv2
docker stack deploy -c target-kafka/zookeeper.yaml mmv2
docker stack deploy -c target-kafka/kafka.yaml mmv2
