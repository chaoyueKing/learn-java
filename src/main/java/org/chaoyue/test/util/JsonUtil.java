package org.chaoyue.test.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

public class JsonUtil extends JsonFormatter {


 // private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

  public JsonUtil() {
  }

  public static String getString(Map<String, Object> map, String key) {
    if(null == map) {
      return "";
    } else {
      try {
        return (String)map.get(key);
      } catch (Exception var3) {
        return toString(map.get(key));
      }
    }
  }

  public static List<Map<String, Object>> getList(Map<String, Object> map, String key) {
    if(null == map) {
      return null;
    } else {
      try {
        return (List)map.get(key);
      } catch (Exception var3) {
        return toList(map.get(key));
      }
    }
  }

  public static Map<String, Object> getMap(Map<String, Object> map, String key) {
    if(null == map) {
      return null;
    } else {
      try {
        return (Map)map.get(key);
      } catch (Exception var3) {
        return (Map)toBean(map.get(key), Map.class);
      }
    }
  }

  public static int getInt(Map<String, Object> map, String key, int defaultValue) {
    try {
      return ((Integer)map.get(key)).intValue();
    } catch (Exception var6) {
      try {
        return Integer.parseInt(toString(map.get(key)));
      } catch (Exception var5) {
        return defaultValue;
      }
    }
  }

  public static BigDecimal getBigDecimal(Map<String, Object> map, String key, BigDecimal defaultValue) {
    return new BigDecimal(getDouble(map, key, defaultValue.doubleValue()));
  }

  public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
    return new BigDecimal(getDouble(map, key));
  }

  public static int getInt(Map<String, Object> map, String key) {
    return getInt(map, key, 0);
  }

  public static boolean getBoolean(Map<String, Object> map, String key, boolean defaultValue) {
    try {
      return ((Boolean)map.get(key)).booleanValue();
    } catch (Exception var6) {
      try {
        return Boolean.parseBoolean(toString(map.get(key)));
      } catch (Exception var5) {
        return defaultValue;
      }
    }
  }

  public static boolean getBoolean(Map<String, Object> map, String key) {
    return getBoolean(map, key, false);
  }

  public static void add(List<HashMap<String, Object>> list, Object obj) {
    list.add(toBean(list, HashMap.class));
  }

  public static double getDouble(Map<String, Object> map, String key, double defaultValue) {
    try {
      return ((Double)map.get(key)).doubleValue();
    } catch (Exception var7) {
      try {
        return Double.parseDouble(toString(map.get(key)));
      } catch (Exception var6) {
        return defaultValue;
      }
    }
  }

  public static double getDouble(Map<String, Object> map, String key) {
    return getDouble(map, key, 0.0D);
  }

  public static long getLong(Map<String, Object> map, String key, long defaultValue) {
    try {
      return ((Long)map.get(key)).longValue();
    } catch (Exception var7) {
      try {
        return Long.parseLong(toString(map.get(key)));
      } catch (Exception var6) {
        return defaultValue;
      }
    }
  }

  public static long getLong(Map<String, Object> map, String key) {
    return getLong(map, key, 0L);
  }

  public static String toString(Object obj) {
    if(obj instanceof String) {
      return (String)obj;
    } else if(null == obj) {
      return null;
    } else {
      try {
        return toJsonString(obj);
      } catch (Exception var2) {
      //  LOG.error("JsonFormatter.toJsonString error: ", var2);
        return null;
      }
    }
  }

  public static String toString(Object obj, DateFormat dateFormat) {
    if(obj instanceof String) {
      return (String)obj;
    } else if(null == obj) {
      return null;
    } else {
      try {
        setDateFormat(dateFormat);
        return toJsonString(obj);
      } catch (Exception var3) {
        return null;
      }
    }
  }

  public static <T> T toBean(Object obj, Class<T> cls) {
    if(null == obj) {
      return null;
    } else {
      try {
        return toObject(toString(obj), cls);
      } catch (Exception var3) {
       // LOG.error("JsonFormatter.toObject error: ", var3);
        return null;
      }
    }
  }

  public static List<Map<String, Object>> toList(Object obj) {
    LinkedList lists = new LinkedList();
    List list = (List)toBean(obj, List.class);
    if(null != list) {
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
        Object object = i$.next();
        Map map = (Map)toBean(object, HashMap.class);
        if(null != map) {
          lists.add(map);
        }
      }
    }

    return lists;
  }

  public static <T> List<T> toList(Object obj, Class<T> cls) {
    LinkedList lists = new LinkedList();
    List list = (List)toBean(obj, List.class);
    if(null != list) {
      Iterator i$ = list.iterator();

      while(i$.hasNext()) {
        Object object = i$.next();
        Object t = toBean(object, cls);
        if(null != t) {
          lists.add(t);
        }
      }
    }

    return lists;
  }
}
