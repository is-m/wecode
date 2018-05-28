package com.chinasoft.it.wecode.security;

import java.util.Date;
import java.util.Map;

/**
 * Token 上下文
 * 
 * @author Administrator
 *
 */
public class TokenContext {

	/**
	 * 用户 ID
	 */
	private String uid;

	/**
	 * token 负荷对象，用于承载参数，应为该参数最终会被外部获取，不建议设计安全性较高的内容
	 */
	private Map<String, Object> payload;

	/**
	 * token 创建事件
	 */
	private Date created;

	/**
	 * token 过期时间
	 */
	private Date expired;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Map<String, Object> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, Object> payload) {
		this.payload = payload;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public TokenContext() {
	}

	public TokenContext(String uid, Map<String, Object> payload) {
		this(uid, payload, null, null);
	}

	public TokenContext(String uid, Map<String, Object> payload, Long created, Long expired) {

		this.uid = uid;
		this.payload = payload;

		if (null != created)
			this.created = new Date(created);
		if (null != expired)
			this.expired = new Date(expired);
	}

}
