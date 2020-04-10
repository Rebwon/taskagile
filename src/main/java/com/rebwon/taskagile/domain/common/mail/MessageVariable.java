package com.rebwon.taskagile.domain.common.mail;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class MessageVariable {
	private String key;
	private Object value;

	private MessageVariable(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public static MessageVariable from(String key, Object value) {
		return new MessageVariable(key, value);
	}
}
