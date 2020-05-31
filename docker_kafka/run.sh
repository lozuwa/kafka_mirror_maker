# !/bin/bash
OVERRIDE_PARAMS=""
env_vars_array=($(env))
declare -A env_vars_hmap

for env_var in "${env_vars_array[@]}"; do
  key=$(echo "${env_var}" | awk -F'=' '{ print $1 }')
  value=$(echo "${env_var}" | awk -F'=' '{ print $2 }')
  env_vars_hmap["${key}"]="${value}"
done;

for key in "${!env_vars_hmap[@]}"; do
  if [[ "${key}" =~ ^KAFKA_ ]]; then
    value="${env_vars_hmap[${key}]}"
    key=$(echo "${key}" | awk -F'KAFKA_' '{ print $2 }' | tr '[:upper:]' '[:lower:]' | tr "_" ".")
    OVERRIDE_PARAMS="${OVERRIDE_PARAMS} --override ${key}=${value}"
  fi;
done;

export KAFKA_HEAP_OPTS = "-Xmx2G -Xms2G"

echo "Overriding params: ${OVERRIDE_PARAMS}"
echo "${WORK_HOME}"kafka/bin/kafka-server-start.sh "${WORK_HOME}"kafka/config/server.properties "${OVERRIDE_PARAMS}"
/opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties $(echo "${OVERRIDE_PARAMS}")

