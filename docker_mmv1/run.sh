# !/bin/bash
export KAFKA_HEAP_OPTS="-Xmx2G -Xms2G"

CONSUMER_FILEPATH="/opt/kafka/configuration-files/mm-consumer.properties"
PRODUCER_FILEPATH="/opt/kafka/configuration-files/mm-producer.properties"

# Consumer.
sed -i "s/bootstrap.servers=.*/bootstrap.servers=${CONSUMER_BOOTSTRAP_SERVERS}/g" "${CONSUMER_FILEPATH}"

# Producer.
sed -i "s/bootstrap.servers=.*/bootstrap.servers=${PRODUCER_BOOTSTRAP_SERVERS}/g" "${PRODUCER_FILEPATH}" 

# Start kafka mirror maker.
/opt/kafka/bin/kafka-mirror-maker.sh \
  --consumer.config "${CONSUMER_FILEPATH}" \
  --producer.config "${PRODUCER_FILEPATH}" 
  --whitelist my_topic_.* \
  --abort.on.send.failure false \
  --num.streams 1 \
  --new.consumer \
  --message.handler "${MESSAGE_HANDLER}" \
  --message.handler.args "${MESSAGE_HANDLER_ARGS}"

