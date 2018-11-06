package com.chinasoft.it.wecode.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 具备简单审计能力的实体父类
 * TODO:审计能力是否要单独抽取到简单审计表,而主表中只包含审计主表ID，审计主表包含ID，操作人，操作时间（当然这种做法），审计内容表包含ID，审计主表ID，操作的对象，源数据，修改后的数据,执行顺序，执行时间
 * {@code @EntityListeners 用于实现@CreatedBy @CreatedDate @LastModifiedBy @LastModifiedDate 等注解字段的自动补充，但是需要项目中有配置AuditorAware的实现类 的 spring bean 并添加 @EnableJpaAuditing}
 * @author Administrator
 *
 */
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseAuditableEntity extends BaseEntity {

  @CreatedBy
  @Column(name = "creation_by",updatable=false)
  private String createdBy;

  @CreatedDate
  @Column(name = "creation_date",updatable=false)
  private Date createdDate;

  @LastModifiedBy
  @Column(name = "last_updated_by")
  private String lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_updated_date")
  private Date lastModifiedDate;

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

}
