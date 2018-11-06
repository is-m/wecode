package com.chinasoft.it.wecode.base;

import javax.persistence.Version;

/**
 * 带有乐观锁的实体父类（更新的时候一定要带上version，如果没有version，只有ID，系统认为是新增。）
 * 
 * 当锁更新失败是会抛出该异常 org.springframework.orm.ObjectOptimisticLockingFailureException
 * @author Administrator
 *
 */
public abstract class BaseAuditableLockEntity extends BaseAuditableEntity {

  @Version
  private Integer version;

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }



}
