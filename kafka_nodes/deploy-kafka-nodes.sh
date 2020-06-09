docker stack deploy -c source-kafka/zookeeper.yaml kafka 
docker stack deploy -c source-kafka/kafka.yaml kafka
docker stack deploy -c target-kafka/zookeeper.yaml kafka
docker stack deploy -c target-kafka/kafka.yaml kafka
