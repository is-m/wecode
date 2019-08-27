package com.chinasoft.it.wecode.common.service;

import com.chinasoft.it.wecode.common.exception.UnSupportException;
import com.chinasoft.it.wecode.common.util.ArrayUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.In;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SpringDataUtils {

    @SuppressWarnings("unchecked")
    public static <T> Page<T> findPagedData(Repository<T, String> repo, Pageable pageable, Object condition, Class<?>... groups) {
        if (repo instanceof JpaSpecificationExecutor) {
            return ((JpaSpecificationExecutor<T>) repo).findAll(buildSpecification(condition, groups), pageable);
        }

        throw new UnSupportException("un support for not implementation JpaSpecificationExecutor at repository");
    }

    public static <T> Specification<T> buildSpecification(Object condition, Class<?>... groups) {
        return new SpecificationImpl<T>(QueryResolver.resolve(condition, groups, true));
    }

    public static class SpecificationImpl<T> implements Specification<T> {

        private static final long serialVersionUID = -2343934078118202587L;
        /**
         * 查询对象
         */
        private List<QueryItem> queries;

        public SpecificationImpl(List<QueryItem> queries) {
            this.queries = queries;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            List<Predicate> predicates = new ArrayList<>();
            for (QueryItem item : queries) {
                Object val = item.getValue();
                Expression<?> expr = null;
                if (!Query.Type.LK.equals(item.getType()) && !Query.Type.LKI.equals(item.getType())) {
                    expr = root.get(item.getField());
                }

                if (val == null || StringUtils.isBlank(val.toString())) {
                    continue;
                }

                switch (item.getType()) {
                    case LK: // like
                        // 当值为字符串类型是，走 like %xx%，否则走 case EQ 分支
                        if (val instanceof String) {
                            String[] fieldArray = StringUtils.split(item.getField(), ",");
                            Objects.requireNonNull(fieldArray, "illegal field by like query");
                            if (fieldArray.length == 1) {
                                predicates.add(cb.like(root.get(item.getField()), "%" + val + "%"));
                            } else if (fieldArray.length > 1) {
                                Predicate[] likePredicates = new Predicate[fieldArray.length];
                                for (int i = 0; i < fieldArray.length; i++) {
                                    likePredicates[i] = cb.like(root.get(fieldArray[i]), "%" + val + "%");
                                }
                                predicates.add(cb.or(likePredicates));
                            }
                            break;
                        }

                    case LKI: // like and ignore case
                        // 当值为字符串类型是，走 like %xx% ，并且忽略大小写，否则走 case EQ 分支
                        if (val instanceof String) {
                            String[] fieldArray = StringUtils.split(item.getField(), ",");
                            Objects.requireNonNull(fieldArray, "illegal field by like query");
                            if (fieldArray.length == 1) {
                                predicates.add(cb.like(cb.lower(root.get(item.getField())), "%" + val.toString().toLowerCase() + "%"));
                            } else if (fieldArray.length > 1) {
                                Predicate[] likePredicates = new Predicate[fieldArray.length];
                                for (int i = 0; i < fieldArray.length; i++) {
                                    likePredicates[i] = cb.like(cb.lower(root.get(fieldArray[i])), "%" + val.toString().toLowerCase() + "%");
                                }
                                predicates.add(cb.or(likePredicates));
                            }
                            break;
                        }

                    case EQ: // equals
                        if (ArrayUtil.isArray(val)) {
                            In<Object> in = cb.in(expr);
                            for (Object v : (Object[]) val) {
                                in.value(v);
                            }
                            predicates.add(in);
                        } else {
                            predicates.add(cb.equal(expr, val));
                        }
                        break;
                    case GTE: // greater than equals
                        if (val instanceof Date) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(Date.class), (Date) val));
                        } else if (val instanceof Byte) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(Byte.class), (Byte) val));
                        } else if (val instanceof Short) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(Short.class), (Short) val));
                        } else if (val instanceof Integer) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(Integer.class), (Integer) val));
                        } else if (val instanceof Long) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(Long.class), (Long) val));
                        } else if (val instanceof Float) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(Float.class), (Float) val));
                        } else if (val instanceof Double) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(Double.class), (Double) val));
                        } else if (val instanceof BigDecimal) {
                            predicates.add(cb.greaterThanOrEqualTo(expr.as(BigDecimal.class), (BigDecimal) val));
                        } else {
                            throw new UnSupportException("un support GTE for value Type " + val.getClass());
                        }
                        break;
                    case LTE: // less than equals
                        if (val instanceof Date) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(Date.class), (Date) val));
                        } else if (val instanceof Byte) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(Byte.class), (Byte) val));
                        } else if (val instanceof Short) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(Short.class), (Short) val));
                        } else if (val instanceof Integer) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(Integer.class), (Integer) val));
                        } else if (val instanceof Long) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(Long.class), (Long) val));
                        } else if (val instanceof Float) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(Float.class), (Float) val));
                        } else if (val instanceof Double) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(Double.class), (Double) val));
                        } else if (val instanceof BigDecimal) {
                            predicates.add(cb.lessThanOrEqualTo(expr.as(BigDecimal.class), (BigDecimal) val));
                        } else {
                            throw new UnSupportException("un support LTE for value Type " + val.getClass());
                        }
                        break;
                    default:
                        throw new UnSupportException("un support query Type enum for" + item.getType());
                }
            }

            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        }

    }

}
