FROM lozuwa/kafka:v2.5.0

COPY mmv2-replication-policies.jar /opt/kafka/libs/
COPY run.sh /run.sh
COPY configuration-files/ /opt/kafka/configuration-files/

ENTRYPOINT ["/bin/bash", "-c"]
CMD ["/run.sh"]

