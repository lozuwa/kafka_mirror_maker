package com.lozuwa.kafka.mirrormaker.replicationpolicies;

import com.lozuwa.kafka.mirrormaker.replicationpolicies.Utils.Utils;
import org.apache.kafka.connect.mirror.DefaultReplicationPolicy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentityMapTopicReplicationPolicy extends DefaultReplicationPolicy {

  private static final Logger logger = LoggerFactory.getLogger(IdentityMapTopicReplicationPolicy.class);

  public static final String TOPIC_REPLICATION_MAPS_ENVIRONMENT_VARIABLE_NAME = "TOPIC_REPLICATION_MAPS";
  public static final String ALL_TOPICS_MAPS_SEPARATOR = ";";
  public static final String TOPICS_MAP_SEPARATOR = ":";
  public static final String SOURCE_TOPICS_SEPARATOR = ",";

  private String sourceClusterAlias;
  private HashMap<String, List<String>> topicMappings = new HashMap<>();

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
        logger.info("TOPIC_REPLICATION_MAPS: " + environmentVariable.getValue());
        topicMappingEnvVar = environmentVariable.getValue();
        break;
      }
    }
    if (topicMappingEnvVar == null){
      String errorMessage = Utils.StringFormatter("{0} environment variable has not been set.", TOPIC_REPLICATION_MAPS_ENVIRONMENT_VARIABLE_NAME);
      throw new RuntimeException(errorMessage);
    }
    // Load the mappings to a hashmap.
    List<String> topicsMaps = Arrays.asList(topicMappingEnvVar.split(ALL_TOPICS_MAPS_SEPARATOR));
    for (String topicsMap : topicsMaps){
      List<String> kafkaTopicsMap = Arrays.asList(topicsMap.split(TOPICS_MAP_SEPARATOR));
      List<String> sourceTopics = Arrays.asList(kafkaTopicsMap.get(0).split(SOURCE_TOPICS_SEPARATOR));
      String targetTopic = kafkaTopicsMap.get(1);
      topicMappings.put(targetTopic, sourceTopics);
    }
  }

  /**
   * If the topic is in the mapping list, then it gets transformed.
   * If the topic is not in the mapping list, then it is copied as an identity.
   * @param sourceClusterAlias
   * @param topic
   * @return String
   */
  @Override
  public String formatRemoteTopic(String sourceClusterAlias, String topic) {
    logger.debug(Utils.StringFormatter("Formatting remote topic. Source cluster alias: {0} Topic: {1}", sourceClusterAlias, topic));
    String topicMapping = topicMappings.get(topic);
    if (topicMapping != null) {
      return topicMapping;
    } else {
      return topic;
    }
  }

  /**
   * Source topic is not null when it is a string which means it comes from the source cluster.
   * @param topic
   * @return String
   */
  @Override
  public String topicSource(String topic) {
    return topic == null ? null : sourceClusterAlias;
  }

  /**
   * Upstream topic is always null.
   * @param topic
   * @return String
   */
  @Override
  public String upstreamTopic(String topic) {
    return null;
  }

  /**
   * Check if the topic is internal. If it is, then it is not replicated.
   * Override the hardcoded values for internal topics.
   * @param topic
   * @return boolean
   */
  @Override
  public boolean isInternalTopic(String topic) {
    return topic.endsWith(".internal") || topic.endsWith("-internal") || topic.matches("__[a-zA-Z]+.*") || topic.startsWith(".");
  }

}


