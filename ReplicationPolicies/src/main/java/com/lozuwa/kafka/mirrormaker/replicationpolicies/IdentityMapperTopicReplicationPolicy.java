package com.lozuwa.kafka.mirrormaker.replicationpolicies;

import com.lozuwa.kafka.mirrormaker.replicationpolicies.Utils.Utils;
import org.apache.kafka.connect.mirror.DefaultReplicationPolicy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentityMapperTopicReplicationPolicy extends DefaultReplicationPolicy {

  private static final Logger logger = LoggerFactory.getLogger(IdentityMapperTopicReplicationPolicy.class);

  public static final String TOPIC_REPLICATION_MAPS_ENVIRONMENT_VARIABLE_NAME = "TOPIC_REPLICATION_MAPS";
  public static final String TOPICS_REPLICATION_MAPS_SEPARATOR = ":";
  public static final String TOPICS_REPLICATION_MAP_SEPARATOR = ",";

  private String sourceClusterAlias;
  private HashMap<String, String> topicMappings = new HashMap<>();

  @Override
  public void configure(Map<String, ?> props) {
    HashMap<String, List<String>> maps = new HashMap<>();
    // Load source cluster alias from props.
    sourceClusterAlias = props.get("source.cluster.alias").toString();
    // Load the topic mapping parameter from the environment variables.
    Map<String, String> environmentVariables = System.getenv();
    String topicMappingEnvVar = null;
    for (Map.Entry<String, String> environmentVariable : environmentVariables.entrySet()) {
      if (environmentVariable.getKey().equals(TOPIC_REPLICATION_MAPS_ENVIRONMENT_VARIABLE_NAME)){
        logger.info("TOPICS_REPLICATION_MAPS: " + environmentVariable.getValue());
        topicMappingEnvVar = environmentVariable.getValue();
        break;
      }
    }
    if (topicMappingEnvVar == null){
      String errorMessage = Utils.StringFormatter("{0} environment variable has not been set.", TOPIC_REPLICATION_MAPS_ENVIRONMENT_VARIABLE_NAME);
      throw new RuntimeException(errorMessage);
    }
    // Load the mappings to a hashmap.
    List<String> topicsMaps = Arrays.asList(topicMappingEnvVar.split(TOPICS_REPLICATION_MAPS_SEPARATOR));
    for (String topicsMap : topicsMaps){
      List<String> kafkaTopicsMap = Arrays.asList(topicsMap.split(TOPICS_REPLICATION_MAP_SEPARATOR));
      String sourceTopic = kafkaTopicsMap.get(0);
      String targetTopic = kafkaTopicsMap.get(1);
      topicMappings.put(sourceTopic, targetTopic);
    }
  }

  @Override
  public String formatRemoteTopic(String sourceClusterAlias, String topic) {
    String targetTopic = topicMappings.getOrDefault(topic, null);
    logger.info(Utils.StringFormatter("Source topic: {0} Target topic: {1}", topic, targetTopic));
    if (targetTopic != null) {
      return targetTopic;
    } else {
      return topic;
    }
  }

  @Override
  public String topicSource(String topic) {
    return topic == null ? null : sourceClusterAlias;
  }

  @Override
  public String upstreamTopic(String topic) {
    return null;
  }

  @Override
  public boolean isInternalTopic(String topic) {
    return topic.endsWith(".internal") || topic.endsWith("-internal") || topic.matches("__[a-zA-Z]+.*") || topic.startsWith(".");
  }

}


