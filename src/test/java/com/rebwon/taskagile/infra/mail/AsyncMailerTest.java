package com.rebwon.taskagile.infra.mail;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.rebwon.taskagile.domain.common.mail.SimpleMessage;

@RunWith(MockitoJUnitRunner.class)
public class AsyncMailerTest {
  @Mock private JavaMailSender mailSenderMock;
  private AsyncMailer instance;

  @Before
  public void setUp() {
    instance = new AsyncMailer(mailSenderMock);
  }

  @Test(expected = IllegalArgumentException.class)
  public void send_nullMessage_shouldFail() {
    instance.send(null);
  }

  @Test
  public void send_validMessage_shouldSucceed() {
    String from = "system@taskagile.com";
    String to = "console.output@taskagile.com";
    String subject = "A test message";
    String body = "Username: test, Email Address: test@taskagile.com";

    SimpleMessage message = new SimpleMessage(to, subject, body, from);
    instance.send(message);

    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom(from);
    simpleMailMessage.setTo(to);
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText("Username: test, Email Address: test@taskagile.com");
    verify(mailSenderMock).send(simpleMailMessage);
  }
}
