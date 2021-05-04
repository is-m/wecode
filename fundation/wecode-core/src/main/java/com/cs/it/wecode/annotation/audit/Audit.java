package com.cs.it.wecode.annotation.audit;

/**
 * 审计注解
 */
public @interface Audit {

    /**
     * 模块
     *
     * @return module
     */
    String module() default "";

    /**
     * 领域
     *
     * @return domain
     */
    String domain() default "";

    /**
     * 审计对象ID，
     *
     * @return objectId
     * @deprecated 该属性已经不建议使用，建议使用@ObjectId来获取相应的对象ID，未废弃时可能需要3个注解属性才能描述，比如
     */
    @Deprecated
    String objectId() default "";



}
