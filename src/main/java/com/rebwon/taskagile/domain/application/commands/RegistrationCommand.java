package com.rebwon.taskagile.domain.application.commands;

import org.springframework.util.Assert;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class RegistrationCommand {
  private String username;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private String password;

  public RegistrationCommand(String username, String emailAddress, String firstName, String lastName, String password) {
    Assert.hasText(username, "Parameter `username` must not be empty");
    Assert.hasText(emailAddress, "Parameter `emailAddress` must not be empty");
    Assert.hasText(firstName, "Parameter `firstName` must not be empty");
    Assert.hasText(lastName, "Parameter `lastName` must not be empty");
    Assert.hasText(password, "Parameter `password` must not be empty");

    this.username = username;
    this.emailAddress = emailAddress;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }
}
