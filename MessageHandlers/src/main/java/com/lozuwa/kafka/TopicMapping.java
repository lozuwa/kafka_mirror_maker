package com.lozuwa.kafka;

import kafka.consumer.BaseConsumerRecord;
import kafka.tools.MirrorMaker;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TopicMapping implements MirrorMaker.MirrorMakerMessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(TopicMapping.class);

  private final String TOPIC_LIST_SPLITTER_TOKEN = ",";
  private final String TOPIC_MAP_SPLITTER_TOKEN = ":";

  private HashMap<String, String> topicMaps = new HashMap<>();

  public TopicMapping(String topicMappingArgument) {
    String[] listOfTopics = topicMappingArgument.split(TOPIC_LIST_SPLITTER_TOKEN);
    for (int index=0; index<listOfTopics.length; index++){
      String[] topicMap = listOfTopics[index].split(TOPIC_MAP_SPLITTER_TOKEN);
      logger.info(stringFormatter("Topic map. Source: {0} Target: {1}", topicMap[0], topicMap[1]));
      topicMaps.put(topicMap[0], topicMap[1]);
    }
  }

  @Override
  public List<ProducerRecord<byte[], byte[]>> handle(BaseConsumerRecord record) {
    try {
      // Get record information.
      String headers = record.headers().toString();
      //String key = new String(record.key(), Charset.forName("UTF-8"));
      String value = new String(record.value(), Charset.forName("UTF-8"));
      String topic = record.topic();
      Long timestamp = record.timestamp();
      String message = stringFormatter("New record arrived with headers: {0} value: {1} topic: {2}", headers, value, topic);
      logger.warn(message);
      // Check if the topic is in topicMaps.
      String targetTopic = topicMaps.getOrDefault(topic, null);
      if (targetTopic != null) {
        return Collections.singletonList(new ProducerRecord<byte[], byte[]>(
            targetTopic,
            null,
            timestamp,
            record.key(),
            record.value(),
            record.headers()
        ));
      } else {
        return Collections.singletonList(new ProducerRecord<byte[], byte[]>(
            topic,
            null,
            timestamp,
            record.key(),
            record.value(),
            record.headers()
        ));
      }
    }
    catch (Exception e){
      logger.error("NPE found in topic.");
      e.printStackTrace();
      return Collections.EMPTY_LIST;
    }
  }

  public String stringFormatter(String line, Object ... params){
    return new MessageFormat(line).format(params);
  }

}
