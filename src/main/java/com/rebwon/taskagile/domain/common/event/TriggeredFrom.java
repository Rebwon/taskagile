package com.rebwon.taskagile.domain.common.event;

import com.rebwon.taskagile.utils.IpAddress;

public interface TriggeredFrom {
  /**
   * Get the IP address where the request originated from
   *
   * @return an IP address
   */
  IpAddress getIpAddress();
}
