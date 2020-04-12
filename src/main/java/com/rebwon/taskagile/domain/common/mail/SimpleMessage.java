package com.rebwon.taskagile.domain.common.mail;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class SimpleMessage implements Message {

  private String to;
  private String subject;
  private String body;
  private String from;

  public SimpleMessage(String to, String subject, String body, String from) {
    this.to = to;
    this.subject = subject;
    this.body = body;
    this.from = from;
  }

  @Override
  public String getTo() {
    return to;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public String getBody() {
    return body;
  }

  @Override
  public String getFrom() {
    return from;
  }
}
