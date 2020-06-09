# !/bin/bash
export KAFKA_HEAP_OPTS="-Xmx2G -Xms2G"

CONFIGURATIONS_FILEPATH="/opt/kafka/configuration-files/mm2.properties"

# Configurations.
sed -i "s/bootstrap.servers=.*/bootstrap.servers=${CONSUMER_BOOTSTRAP_SERVERS}/g" "${CONFIGURATION_FILEPATH}"

# Start kafka mirror maker.
/opt/kafka/bin/kafka-mirror-maker.sh

