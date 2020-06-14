# Build TopicMapping
(cd ../ReplicationPolicies/ && ./gradlew clean build --stacktrace)
(mv ../ReplicationPolicies/build/libs/*.jar .)
# Build docker image.
docker build -t lozuwa/kafka:mmv2-v2.5.0 .
