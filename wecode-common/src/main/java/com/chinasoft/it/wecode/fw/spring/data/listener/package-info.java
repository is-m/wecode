/**
 * 该包主要用于存放通用的实体监听器
 * 
 * 实体监听器可以实现实体类的各个场景下的监听，比如
 * {@code javax.persistence.*
 * 新增前@PrePersist
 * 新增后@PostPersist
 * 修改前 @PreUpdate
 * 修改后 @PostUpdate
 * 删除前@PreRemove
 * 删除后 @PostRemove
 * 加载后 @PostLoad
 * }
 * 需要在实体类上添加@EntityListeners 就能生效了
 * 随着DDD的设计模式逐渐被大家认可和热捧。JPA通过Listener这种机制可以很好地实现事件分离，状体分离。假如，订单的状态变化可能对我们来说比较重要，我们需要定一个类去监听订单状态变更，通知相应的逻辑代码各自去干各自的活。
 * @author Administrator
 *
 */
package com.chinasoft.it.wecode.fw.spring.data.listener;
