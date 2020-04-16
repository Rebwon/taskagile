package com.rebwon.taskagile.domain.model.user.events;

import com.rebwon.taskagile.domain.common.event.DomainEvent;
import com.rebwon.taskagile.domain.common.event.TriggeredFrom;
import com.rebwon.taskagile.domain.model.user.User;
import lombok.ToString;

@ToString
public class UserRegisteredEvent extends DomainEvent {
	private static final long serialVersionUID = 2580061707540917880L;

	public UserRegisteredEvent(User user, TriggeredFrom triggeredFrom) {
	  super(user.getId(), triggeredFrom);
  }
}
