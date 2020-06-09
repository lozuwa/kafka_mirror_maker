# Build TopicMapping
(cd ../MessageHandlers && ./gradlew clean build --stacktrace)
(mv ../MessageHandlers/build/libs/*.jar .)
# Build docker image.
docker build -t lozuwa/kafka:mmv1-v2.5.0 .

