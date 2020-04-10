package com.rebwon.taskagile.domain.common.event;

import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {
	private static final long serialVersionUID = -444783093811334147L;

	public DomainEvent(Object source) {
		super(source);
	}

	public long occurredAt() {
		// 기본 구현된 TimeStamp 반환.
		return getTimestamp();
	}
}
