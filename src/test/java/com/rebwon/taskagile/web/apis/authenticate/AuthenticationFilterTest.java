package com.rebwon.taskagile.web.apis.authenticate;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AuthenticationFilterTest {
  @MockBean
  private AuthenticationManager authenticationManager;

  @Test(expected = InsufficientAuthenticationException.class)
  public void attemptAuthentication_emptyRequestBody_shouldFail() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
    AuthenticationFilter filter = new AuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager);
    filter.attemptAuthentication(request, new MockHttpServletResponse());
  }

  @Test(expected = InsufficientAuthenticationException.class)
  public void attemptAuthentication_invalidJsonStringRequestBody_shouldFail() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
    request.setContent("username=testusername&password=TestPassword!".getBytes());
    AuthenticationFilter filter = new AuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager);
    filter.attemptAuthentication(request, new MockHttpServletResponse());
  }

  @Test
  public void attemptAuthentication_validJsonStringRequestBody_shouldSucceed() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
    request.setContent("{\"username\": \"testusername\", \"password\": \"TestPassword!\"}".getBytes());
    AuthenticationFilter filter = new AuthenticationFilter();
    filter.setAuthenticationManager(authenticationManager);
    filter.attemptAuthentication(request, new MockHttpServletResponse());
    UsernamePasswordAuthenticationToken token =
      new UsernamePasswordAuthenticationToken("testusername", "TestPassword!");
    verify(authenticationManager).authenticate(token);
  }
}
