package com.rebwon.taskagile.web.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketSession;

import com.rebwon.taskagile.domain.model.user.UserId;

public final class SubscriptionHub {
  private static final Logger log = LoggerFactory.getLogger(SubscriptionHub.class);
  // Key는 채널, 값은 구독 중인 웹소켓세션의 집합.
  private static final Map<String, Set<WebSocketSession>> subscriptions = new HashMap<>();
  // 고객이 구독한 채널을 유지함.
  // 키는 세션 ID이고 값은 구독 중인 세션의 집합
  private static final Map<String, Set<String>> subscribedChannels = new HashMap<>();

  public static void subscribe(RealTimeSession session, String channel) {
    Assert.hasText(channel, "Parameter `channel` must not be null");

    Set<WebSocketSession> subscribers = subscriptions.computeIfAbsent(channel, k -> new HashSet<>());
    subscribers.add(session.wrapped());

    UserId userId = session.getUserId();
    log.debug("RealTimeSession[{}] Subscribed user[id={}] to channel `{}`", session.id(), userId, channel);

    // 클라이언트의 구독 목록에 채널을 추가함.
    Set<String> channels = subscribedChannels.computeIfAbsent(session.id(), k -> new HashSet<>());
    channels.add(channel);
  }

  public static void unsubscribe(RealTimeSession session, String channel) {
    Assert.hasText(channel, "Parameter `channel` must not be empty");
    Assert.notNull(session, "Parameter `session` must not be null");

    Set<WebSocketSession> subscribers = subscriptions.get(channel);
    if (subscribers != null) {
      subscribers.remove(session.wrapped());
      UserId userId = session.getUserId();
      log.debug("RealTimeSession[{}] Unsubscribed user[id={}] from channel `{}`", session.id(), userId, channel);
    }

    // 클라이언트의 구독 목록에서 채널을 제거.
    Set<String> channels = subscribedChannels.get(session.id());
    if (channels != null) {
      channels.remove(channel);
    }
  }

  public static void unsubscribeAll(RealTimeSession session) {
    Set<String> channels = subscribedChannels.get(session.id());
    if (channels == null) {
      log.debug("RealTimeSession[{}] No channels to unsubscribe.", session.id());
      return;
    }

    for (String channel: channels) {
      unsubscribe(session, channel);
    }

    // 클라이언트의 모든 채널을 제거.
    subscribedChannels.remove(session.id());
  }

  public static void send(String channel, String update) {
    Assert.hasText(channel, "Parameter `channel` must not be empty");
    Assert.hasText(update, "Parameter `update` must not be null");

    Set<WebSocketSession> subscribers = subscriptions.get(channel);
    if (subscribers == null || subscriptions.isEmpty()) {
      log.debug("No subscribers of channel `{}` found", channel);
      return;
    }

    for (WebSocketSession subscriber: subscribers) {
      sendTo(subscriber, channel, update);
    }
  }

  private static void sendTo(WebSocketSession subscriber, String channel, String update) {
    try{
      subscriber.sendMessage(WebSocketMessages.channelMessage(channel, update));
      log.debug("RealTimeSession[{}] Send message `{}` to subscriber at channel `{}`",
        subscriber.getId(), update, channel);
    } catch (IOException e) {
      log.error("Failed to send message to subscriber `" + subscriber.getId() +
        "` of channel `" + channel + "`. Message: " + update, e);
    }
  }
}
