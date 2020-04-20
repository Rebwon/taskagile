package com.rebwon.taskagile.domain.common.mail;

public interface Message {
  String getTo();
  String getSubject();
  String getBody();
  String getFrom();
}
