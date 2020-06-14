# !/bin/bash
export KAFKA_HEAP_OPTS="-Xmx2G -Xms2G"

CONFIGURATIONS_FILEPATH="/opt/kafka/configuration-files/mm2.properties"

# Start kafka mirror maker.
/opt/kafka/bin/connect-mirror-maker.sh $(echo "${CONFIGURATIONS_FILEPATH}")

