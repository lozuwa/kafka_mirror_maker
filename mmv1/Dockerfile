FROM lozuwa/kafka:v2.5.0

COPY TopicMapping/build/libs/TopicMapping.jar /opt/kafka/libs/
COPY run.sh /run.sh
COPY mm/ /opt/kafka/mm/

ENTRYPOINT ["/bin/bash", "-c"]
CMD ["/run.sh"]

