package com.chinasoft.it.wecode.example.mail.dto;

import javax.mail.Flags;
import javax.mail.Flags.Flag;

public class MailResponseDto {

	/**
	 * 响应标记，默认已读
	 */
	private Flags.Flag flag = Flag.SEEN;

	public Flags.Flag getFlag() {
		return flag;
	}

	public void setFlag(Flags.Flag flag) {
		this.flag = flag;
	}

	public MailResponseDto() {

	}

	public static MailResponseDto of(Flag flag) {
		return new MailResponseDto() {
			{
				setFlag(flag);
			}
		};
	}

}
