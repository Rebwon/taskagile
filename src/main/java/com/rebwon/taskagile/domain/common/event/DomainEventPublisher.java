package com.rebwon.taskagile.domain.common.event;

public interface DomainEventPublisher {
	void publish(DomainEvent event);
}
