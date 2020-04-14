package com.rebwon.taskagile.web.socket;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.utils.JsonUtils;

/**
 * WebSocketSession Wrapper class.
 */
public class RealTimeSession {
  private static final Logger log = LoggerFactory.getLogger(RealTimeSession.class);
  private static final String KEY_USER_ID = "KEY_USER_ID";

  private WebSocketSession webSocketSession;

  RealTimeSession(WebSocketSession webSocketSession) {
    this.webSocketSession = webSocketSession;
  }

  public String id() {
    return webSocketSession.getId();
  }

  public WebSocketSession wrapped() {
    return webSocketSession;
  }

  public void setUserId(UserId userId) {
    addAttribute(KEY_USER_ID, userId);
  }

  public UserId getUserId() {
    return getAttribute(KEY_USER_ID);
  }

  void addAttribute(String key, Object value) {
    webSocketSession.getAttributes().put(key, value);
  }

  @SuppressWarnings("unchecked")
  public <T> T getAttribute(String key) {
    Object value = webSocketSession.getAttributes().get(key);
    if(value == null) {
      return null;
    }
    return (T) value;
  }

  public String getToken() {
    URI uri = webSocketSession.getUri();
    UriComponents uriComponents = UriComponentsBuilder.fromUri(uri).build();
    return uriComponents.getQueryParams().getFirst("token");
  }

  public void error(String error) {
    sendMessage(WebSocketMessages.error(error));
  }

  public void fail(String failure) {
    sendMessage(WebSocketMessages.failure(failure));
  }

  public void reply(String reply) {
    sendMessage(WebSocketMessages.reply(reply));
  }

  private void sendMessage(Object message) {
    try{
      String textMessage = JsonUtils.toJson(message);
      webSocketSession.sendMessage(new TextMessage(textMessage));
    } catch (IOException e) {
      log.error("Failed to send message through web socket session", e);
    }
  }
}
