docker service rm mmv1_mmv1
sleep 3s
docker stack deploy -c mmv1.yaml mmv1 
