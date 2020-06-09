docker service rm kafka_mmv1
sleep 3s
docker stack deploy -c mmv1.yaml kafka
