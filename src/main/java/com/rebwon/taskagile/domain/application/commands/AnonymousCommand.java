package com.rebwon.taskagile.domain.application.commands;

import org.springframework.util.Assert;

import com.rebwon.taskagile.domain.common.event.TriggeredFrom;
import com.rebwon.taskagile.utils.IpAddress;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class AnonymousCommand implements TriggeredFrom {
  private IpAddress ipAddress;

  public void triggeredBy(IpAddress ipAddress) {
    Assert.notNull(ipAddress, "Parameter `ipAddress` must not be null");

    this.ipAddress = ipAddress;
  }

  @Override
  public IpAddress getIpAddress() {
    return ipAddress;
  }
}
