FROM openjdk:11-jre-slim

ENV FROZEN_KAFKA_RELEASE=2.5.0 \
    WORK_HOME=/opt/

WORKDIR ${WORK_HOME}

RUN apt-get update &&\
    apt-get install -y curl &&\
    apt-get install -y ca-certificates

RUN curl -LO https://downloads.apache.org/kafka/"${FROZEN_KAFKA_RELEASE}"/kafka_2.12-"${FROZEN_KAFKA_RELEASE}".tgz
RUN tar xf kafka_2.12-${FROZEN_KAFKA_RELEASE}.tgz &&\
    mv kafka_2.12-${FROZEN_KAFKA_RELEASE} kafka/ &&\
    rm -rf kafka_2.12-${FROZEN_KAFKA_RELEASE}.tgz

COPY run.sh /

ENTRYPOINT ["/bin/bash", "-c"]
CMD ["/run.sh"]

