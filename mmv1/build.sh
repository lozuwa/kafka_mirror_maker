# Build TopicMapping
(cd TopicMapping && ./gradlew clean build --stacktrace)
# Build docker image.
docker build -t lozuwa/kafka:mm .

