package com.rebwon.taskagile.domain.common.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.filter.GenericFilterBean;

public class ApiRequestAccessDeniedExceptionTranslationFilter extends GenericFilterBean {
  private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

  @Override
  public void doFilter(ServletRequest req, ServletResponse res,
    FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    try{
      chain.doFilter(request, response);
    } catch (IOException ex) {
      throw ex;
    } catch (Exception ex) {
      // 요청이 API 요청이 아닌 경우 예외를 다시 던진다.
      if (!request.getRequestURI().startsWith("/api/") && !request.getRequestURI().startsWith("/rt/")) {
        throw ex;
      }

      // SpringSecurityException의 StackTrace를 추출한다.
      Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
      RuntimeException ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(
        AccessDeniedException.class, causeChain);

      // Spring Security의 AccessDeniedException이 아닙니다. 여기서는 예외처리를 따로할 필요가 없습니다.
      if (ase == null) {
        throw ex;
      }

      if (response.isCommitted()) {
        throw new ServletException("Unable to translate AccessDeniedException because the response" +
          " of this API request is already committed.", ex);
      }

      // The user is not authenticated. Instead of showing a 403 error, we should
      // send a 401 error to the client, indicating that accessing the requested
      // resources requires authentication and the client hasn't been authenticated.
      if (request.getUserPrincipal() == null) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      } else {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      }
    }
  }

  /**
   * Default implementation of <code>ThrowableAnalyzer</code> which is capable of also
   * unwrapping <code>ServletException</code>s.
   */
  private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
    protected void initExtractorMap() {
      super.initExtractorMap();

      registerExtractor(ServletException.class, throwable -> {
        ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
        return ((ServletException) throwable).getRootCause();
      });
    }
  }
}
