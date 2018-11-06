package com.chinasoft.it.wecode.base;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

/**
 * 
 * @author Administrator
 *
 */

@MappedSuperclass
public abstract class BaseEntity implements Persistable<String> {

  /**
   * IdentifierGenerator，分布式系统可能还需要 自定义主键策略
   * https://blog.csdn.net/xufei_0320/article/details/78707661
   */
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  /*
   * EnumType: ORDINAL 枚举序数 默认选项（int）。eg:TEACHER 数据库存储的是 0 STRING：枚举名称 (String)。eg:TEACHER 数据库存储的是
   * "TEACHER"
   * 
   * @Enumerated(EnumType.STRING) private AccountType accountType = AccountType.TEACHER;
   */

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public boolean isNew() {
    return this.id == null;
  }

  /**
   * 覆盖toString方法，目的显示所有JavaBean的属性值，省略写很多的getXxx的方法
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  // Common Lang API EqualsBuilder.reflection
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this == obj) {
      return true;
    }

    if (!getClass().equals(obj.getClass())) {
      return false;
    }

    BaseEntity rhs = (BaseEntity) obj;
    return this.id == null ? false : this.id.equals(rhs.id);
  }

  /**
   * child of int hashCode = super.hashCode() * field.hashCode();
   * 
   * HashCodeBuilder.reflectionHashCode(this);
   * https://baijiahao.baidu.com/s?id=1576860126059183009&wfr=spider&for=pc
   * https://blog.csdn.net/abinge317/article/details/51437179
   */
  @Override
  public int hashCode() {
    return 17 * (id == null ? (int) (Math.random() * 999999999) : id.hashCode());
  }
}
