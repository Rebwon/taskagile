package com.rebwon.taskagile.domain.model.user.events;

import org.springframework.util.Assert;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
public class UserRegisteredEvent extends DomainEvent {
	private static final long serialVersionUID = 2580061707540917880L;

	private User user;

	public UserRegisteredEvent(User user) {
		super(user);
		Assert.notNull(user, "Parameter `user` must not be null");
		this.user = user;
	}
}
