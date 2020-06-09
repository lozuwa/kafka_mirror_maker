# Build TopicMapping
(cd ../ReplicationPolicies/ && ./gradlew clean build --stacktrace)
(mv ../ReplicationPolicies/build/libs/*.jar .)
# Build docker image.
docker build -t lozuwa/kafka:mmv1-v2.5.0 .
