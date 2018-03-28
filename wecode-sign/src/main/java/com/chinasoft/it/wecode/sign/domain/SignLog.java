package com.chinasoft.it.wecode.sign.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinasoft.it.wecode.base.BaseEntity;

@Entity
@Table(name = "sn_sign_log")
public class SignLog extends BaseEntity {

	private static final long serialVersionUID = 5646332069260558367L;

	private String userId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date signTime;

	private String signPoint;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignPoint() {
		return signPoint;
	}

	public void setSignPoint(String signPoint) {
		this.signPoint = signPoint;
	}

}
