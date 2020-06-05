# !/bin/bash
export KAFKA_HEAP_OPTS="-Xmx2G -Xms2G"

# Consumer.
sed -i "s/bootstrap.servers=.*/bootstrap.servers=${CONSUMER_BOOTSTRAP_SERVERS}/g" /opt/kafka/mm/mm-consumer.properties

# Producer.
sed -i "s/bootstrap.servers=.*/bootstrap.servers=${PRODUCER_BOOTSTRAP_SERVERS}/g" /opt/kafka/mm/mm-producer.properties 

# Start kafka mirror maker.
/opt/kafka/bin/kafka-mirror-maker.sh \
  --consumer.config /opt/kafka/mm/mm-consumer.properties \
  --producer.config /opt/kafka/mm/mm-producer.properties \
  --whitelist my_topic_.* \
  --abort.on.send.failure false \
  --num.streams 1 \
  --new.consumer \
  --message.handler "${MESSAGE_HANDLER}" \
  --message.handler.args "${MESSAGE_HANDLER_ARGS}"

