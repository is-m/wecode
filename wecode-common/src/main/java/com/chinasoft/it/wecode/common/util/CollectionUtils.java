package com.chinasoft.it.wecode.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {

  public static <E> boolean notEmpty(Collection<E> list) {
    return !isEmpty(list);
  }

  public static <E> boolean isEmpty(Collection<E> list) {
    return list == null || list.isEmpty();
  }

  /**
   * 判断集合是否全部为空
   * @param collections
   * @return
   */
  public static boolean isAllEmpty(Collection<?>... collections){
    if(collections != null){
      for(Collection<?> item : collections){
        if(item != null && !item.isEmpty()){
          return false;
        }
      }
    }
    return true;
  }

  public static <E, K> Map<K, E> list2Map(Collection<E> list, Function<E, K> keyProvider) {
    return isEmpty(list) ? new HashMap<>() : list.stream().collect(Collectors.toMap(keyProvider, el -> el));
  }

  /**
   * 遍历
   * 
   * @param collection
   * @param consumer
   */
  public static <T> void forEach(Collection<T> list, Consumer<T> consumer) {
    if (notEmpty(list)) {
      list.forEach(consumer);
    }
  }

  /**
   * 遍历并返回新集合
   * 
   * @param collection
   * @param func
   * @return
   */
  public static <T, R> List<R> forEach2(Collection<T> list, Function<T, R> func) {
    List<R> result = new ArrayList<>();
    if (notEmpty(list)) {
      list.forEach(item -> result.add(func.apply(item)));
    }
    return result;
  }

  /**
   * 集合合并
   * 
   * @param collections
   * @return
   */
  @SafeVarargs
  public static <T> List<T> megre(Collection<T>... collections) {
    int totalSize = 0;
    for (Collection<T> coll : collections) {
      if (notEmpty(coll)) {
        totalSize += coll.size();
      }
    }

    if (totalSize > 0) {
      List<T> result = new ArrayList<>(totalSize);
      for (Collection<T> coll : collections) {
        if (notEmpty(coll)) {
          result.addAll(coll);
        }
      }
      return result;
    }

    return new ArrayList<>(0);
  }

  /**
   * 创建Map
   * 
   * 注意：key需要是String，且参数长度必须能被2整除，否则会出现索引越界
   * 
   * @param keyValues
   * @return
   */
  public static Map<String, Object> newMap(Object... keyValues) throws ArrayIndexOutOfBoundsException {
    Map<String, Object> map = new HashMap<>();
    if (!ArrayUtil.isEmpty(keyValues)) {
      for (int i = 0, j = keyValues.length; i < j; i += 2) {
        map.put((String) keyValues[i], keyValues[i + 1]);
      }
    }
    return map;
  }
}
