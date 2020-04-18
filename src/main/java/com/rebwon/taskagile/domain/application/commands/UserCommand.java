package com.rebwon.taskagile.domain.application.commands;

import org.springframework.util.Assert;

import com.rebwon.taskagile.domain.common.event.TriggeredBy;
import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.utils.IpAddress;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class UserCommand implements TriggeredBy {
  private UserId userId;
  private IpAddress ipAddress;

  public void triggeredBy(UserId userId, IpAddress ipAddress) {
    Assert.notNull(userId, "Parameter `userId` must not be null");
    Assert.notNull(ipAddress, "Parameter `ipAddress` must not be null");

    this.userId = userId;
    this.ipAddress = ipAddress;
  }

  @Override
  public UserId getUserId() {
    return userId;
  }

  @Override
  public IpAddress getIpAddress() {
    return ipAddress;
  }
}
