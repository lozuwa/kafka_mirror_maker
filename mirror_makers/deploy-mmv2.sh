docker service rm kafka_mmv2
docker config rm kafka_mm2.properties
sleep 3s
docker stack deploy -c mmv2.yaml kafka
