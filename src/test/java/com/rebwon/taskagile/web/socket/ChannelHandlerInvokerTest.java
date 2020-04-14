package com.rebwon.taskagile.web.socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class ChannelHandlerInvokerTest {

  @ChannelHandler("/test/**")
  private static class TestChannelHandler {
    @Action("execute")
    public void execute(@ChannelValue String channel, RealTimeSession session, @Payload TestMessage message) {
    }

    @Action("subscribe")
    public void subscribe(RealTimeSession session) {
    }

    @Action("empty")
    public void empty() {
    }
  }

  @Getter
  @Setter
  @EqualsAndHashCode
  private static class TestMessage {
    private String message;

    public static TestMessage create(String message) {
      TestMessage testMessage = new TestMessage();
      testMessage.message = message;
      return testMessage;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullHandler_shouldFail() {
    new ChannelHandlerInvoker(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_noHandlerAnnotation_shouldFail() {
    new ChannelHandlerInvoker(new Object());
  }

  @Test
  public void constructor_validHandler_shouldSucceed() {
    new ChannelHandlerInvoker(new TestChannelHandler());
  }

  @Test
  public void supports_notFoundAction_shouldReturnFalse() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    assertFalse(invoker.supports("not exist action"));
  }

  @Test
  public void supports_existAction_shouldReturnTrue() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    assertTrue(invoker.supports("execute"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void handle_wrongChannelValueInIncomingMessage_shouldFail() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/abc", "execute", ""), session);
  }

  @Test(expected = IllegalArgumentException.class)
  public void handle_wrongActionValueInIncomingMessage_shouldFail() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test", "find", ""), session);
  }

  @Test
  public void handle_validIncomingMessageAndEmptyParameterInActionMethod_shouldSucceed() {
    TestChannelHandler mockHandler = mock(TestChannelHandler.class);
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(mockHandler);
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test/abc", "empty", null), session);

    verify(mockHandler).empty();
  }

  @Test
  public void handle_validIncomingMessageAndOnlySessionParameterRequired_shouldSucceed() {
    TestChannelHandler mockHandler = mock(TestChannelHandler.class);
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(mockHandler);
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test/abc", "subscribe", null), session);

    verify(mockHandler).subscribe(session);
  }

  @Test
  public void handle_validIncomingMessageAndSessionPayloadAllRequired_shouldSucceed() {
    TestChannelHandler mockHandler = mock(TestChannelHandler.class);
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(mockHandler);
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test/abc", "execute", "{\"message\": \"ABC\"}"), session);

    verify(mockHandler).execute("/test/abc", session, TestMessage.create("ABC"));
  }
}
