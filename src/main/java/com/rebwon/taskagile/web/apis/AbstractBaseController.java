package com.rebwon.taskagile.web.apis;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.Assert;

import com.rebwon.taskagile.domain.application.commands.AnonymousCommand;
import com.rebwon.taskagile.domain.application.commands.UserCommand;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.utils.RequestUtils;

public abstract class AbstractBaseController {
  void addTriggeredBy(UserCommand command, HttpServletRequest request) {
    Assert.notNull(request.getUserPrincipal(), "User principal must be present in the request");
    UsernamePasswordAuthenticationToken userPrincipal = (UsernamePasswordAuthenticationToken)request.getUserPrincipal();
    SimpleUser currentUser = (SimpleUser)userPrincipal.getPrincipal();
    command.triggeredBy(currentUser.getUserId(), RequestUtils.getIpAddress(request));
  }

  void addTriggeredBy(AnonymousCommand command, HttpServletRequest request) {
    command.triggeredBy(RequestUtils.getIpAddress(request));
  }
}
