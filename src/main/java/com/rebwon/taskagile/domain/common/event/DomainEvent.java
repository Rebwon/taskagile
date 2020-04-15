package com.rebwon.taskagile.domain.common.event;

import java.io.Serializable;
import java.util.Date;

import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.utils.IpAddress;
import lombok.Getter;

@Getter
public abstract class DomainEvent implements Serializable {
	private static final long serialVersionUID = -444783093811334147L;

	private UserId userId;
	private IpAddress ipAddress;
	private Date occurredAt;

	public DomainEvent(TriggeredBy triggeredBy) {
    this.userId = triggeredBy.getUserId();
    this.ipAddress = triggeredBy.getIpAddress();
    this.occurredAt = new Date();
	}

  public DomainEvent(UserId userId, TriggeredFrom triggeredFrom) {
    this.userId = userId;
    this.ipAddress = triggeredFrom.getIpAddress();
    this.occurredAt = new Date();
  }
}
