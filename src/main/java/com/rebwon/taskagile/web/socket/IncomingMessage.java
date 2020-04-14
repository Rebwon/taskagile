package com.rebwon.taskagile.web.socket;

import lombok.Getter;
import lombok.Setter;

/**
 * Incoming message received via WebSocket. The raw message is a JSON
 * string in the following format:
 * <pre>
 * {
 *   "channel": required|String
 *   "action": required|String
 *   "payload": required|String
 * }
 * </pre>
 */
@Getter
@Setter
public class IncomingMessage {

  /**
   * Specify the channel for this message. {@link WebSocketRequestDispatcher}
   * will route the request to the corresponding {@link ChannelHandler}.
   */
  private String channel;

  /**
   * Specify the action to take. {@link WebSocketRequestDispatcher} will find
   * the corresponding action method by checking the {@link Action} settings
   */
  private String action;

  /**
   * The payload of the message that an action method will receive as its input.
   */
  private String payload;

  public static IncomingMessage create(String channel, String action, String payload) {
    IncomingMessage message = new IncomingMessage();
    message.channel = channel;
    message.action = action;
    message.payload = payload;
    return message;
  }
}
