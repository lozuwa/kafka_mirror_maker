package com.lozuwa.kafka.mirrormaker.replicationpolicies.Utils;

import java.text.MessageFormat;

public class Utils {

  public static String StringFormatter(String line, Object ... params){
    return new MessageFormat(line).format(params);
  }

}
