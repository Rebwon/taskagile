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
  private String password;
  private String emailAddress;

  public RegistrationCommand(String username, String emailAddress, String password) {
		Assert.hasText(username, "Parameter `username` must not be empty");
		Assert.hasText(password, "Parameter `password` must not be empty");
		Assert.hasText(emailAddress, "Parameter `emailAddress` must not be empty");

		this.username = username;
		this.password = password;
		this.emailAddress = emailAddress;
	}
}
