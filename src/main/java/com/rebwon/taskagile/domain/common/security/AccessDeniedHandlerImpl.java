package com.rebwon.taskagile.domain.common.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.rebwon.taskagile.domain.model.user.SimpleUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request,
    HttpServletResponse response,
    AccessDeniedException e) throws IOException {
    if(log.isDebugEnabled()) {
      log.debug("Access to `" + request.getRequestURI() + "` denied.");
    }

    if(request.getRequestURI().startsWith("/api/")) {
      if(request.getUserPrincipal() instanceof SimpleUser) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
      } else{
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } else{
      response.sendRedirect("/login");
    }
  }
}
