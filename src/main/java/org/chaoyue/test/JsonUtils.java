package org.chaoyue.test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: chaoyue<chaoyue.fan @ chinaredstar.com>
 * @date: Create in 14:19 2017/12/28
 * @version: 1.0.0
 * @modified by:
 */
public class JsonUtils {
  private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);


  private static ObjectMapper getMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }


  /**
   * 将对象的大写转换为下划线加小写，例如：userName-->user_name
   *
   * @param object
   * @return
   */
  public static String toUnderlineJsonString(Object object) {
    ObjectMapper mapper = getMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    mapper.setSerializationInclusion(Include.NON_NULL);
    String reqJson = null;
    try {
      reqJson = mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOG.error("toUnderlineJSONString error:[{}] ", e);
    }
    return reqJson;
  }

  /**
   * 将下划线转换为驼峰的形式，例如：user_name-->userName
   *
   * @param json
   * @param clazz
   * @return
   * @throws IOException
   */
  public static <T> T toSnakeBean(String json, Class<T> clazz) {
    ObjectMapper mapper = getMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    T reqJson = null;
    try {
      reqJson = mapper.readValue(json, clazz);
    } catch (IOException e) {
      LOG.error("toSnakeBean json error:[{},{}] ", e);
    }
    return reqJson;
  }


  /**
   * 对象转jsonString
   *
   * @param object
   * @return
   */
  public static String toJsonString(Object object) {
    ObjectMapper mapper = getMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    String reqJson = null;
    try {
      reqJson = mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOG.error("toJsonString error:[{}] ", e);
    }
    return reqJson;
  }

  /**
   * jsonString对象转
   *
   * @param json
   * @param clazz
   * @return
   * @throws IOException
   */
  public static <T> T toBean(String json, Class<T> clazz) {
    ObjectMapper mapper = getMapper();
    T reqJson = null;
    try {
      reqJson = mapper.readValue(json, clazz);
    } catch (IOException e) {
      LOG.error("toBean json error:[{},{}] ", e);
    }
    return reqJson;
  }


  public static void main(String[] args) {
    Map ma = new HashMap();
    ma.put("userName","黄金卡");
    ma.put("aaaBaa","asdasasd");
    System.out.println(toUnderlineJsonString(ma));
  }

  class User{
    private String userName;

    private String aaaBaa;

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public String getAaaBaa() {
      return aaaBaa;
    }

    public void setAaaBaa(String aaaBaa) {
      this.aaaBaa = aaaBaa;
    }
  }
}
