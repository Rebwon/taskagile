package com.rebwon.taskagile.domain.common.event;

import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.utils.IpAddress;

public interface TriggeredBy {
  UserId getUserId();
  IpAddress getIpAddress();
}
