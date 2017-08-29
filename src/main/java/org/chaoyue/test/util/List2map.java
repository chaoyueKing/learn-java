package org.chaoyue.test.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class List2map {
  /**
   * 列转map
   * @param list 入参list
   * @param fieldName4Key bean的属性名
   * @param c bean
   * @param <K>
   * @param <V>
   * @return
   */
  public static <K, V> Map<K, V> list2Map(List<V> list, String fieldName4Key, Class<V> c) {
    Map<K, V> map = new HashMap<K, V>();
    if (list != null) {
      try {
        PropertyDescriptor propDesc = new PropertyDescriptor(fieldName4Key, c);
        Method methodGetKey = propDesc.getReadMethod();
        for (int i = 0; i < list.size(); i++) {
          V value = list.get(i);
          @SuppressWarnings("unchecked")
          K key = (K) methodGetKey.invoke(list.get(i));
          map.put(key, value);
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("field can't match the key!");
      }
    }
    return map;
  }
}
