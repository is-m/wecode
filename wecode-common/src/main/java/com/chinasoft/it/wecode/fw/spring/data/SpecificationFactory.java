package com.chinasoft.it.wecode.fw.spring.data;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationFactory {


  // like
  public static <T> Specification<T> like(String attr, String val) {
    return (root, query, cb) -> cb.like(root.get(attr), "%" + val + "%");
  }


  // equal
  public static <T> Specification<T> equals(String attr, Object val) {
    return (root, query, cb) -> cb.equal(root.get(attr), val);
  }

  // between
  public static <T> Specification<T> between(String attr, int min, int max) {
    return (root, query, cb) -> cb.between(root.get(attr), min, max);
  }

  // between
  public static <T> Specification<T> between(String attr, double min, double max) {
    return (root, query, cb) -> cb.between(root.get(attr), min, max);
  }

  // between
  public static <T> Specification<T> between(String attr, Date min, Date max) {
    return (root, query, cb) -> cb.between(root.get(attr), min, max);
  }

  // in
  public static <T> Specification<T> in(String attr, Collection<?> c) {
    return (root, query, cb) -> root.get(attr).in(c);
  }

  // 大于等于
  public static <T> Specification<T> greaterThan(String attr, BigDecimal val) {
    return (root, query, cb) -> cb.greaterThan(root.get(attr), val);
  }

  // 大于等于
  public static <T> Specification<T> greaterThan(String attr, long val) {
    return (root, query, cb) -> cb.greaterThan(root.get(attr), val);
  }

  // 大于等于
  public static <T> Specification<T> greaterThan(String attr, int val) {
    return (root, query, cb) -> cb.greaterThan(root.get(attr), val);
  }

  // 大于等于
  public static <T> Specification<T> greaterThan(String attr, Date val) {
    return (root, query, cb) -> cb.greaterThan(root.get(attr), val);
  }
}
