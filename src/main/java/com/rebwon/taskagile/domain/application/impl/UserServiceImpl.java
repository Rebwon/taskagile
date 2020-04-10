package com.rebwon.taskagile.domain.application.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rebwon.taskagile.domain.application.UserService;
import com.rebwon.taskagile.domain.application.commands.RegistrationCommand;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.common.mail.MailManager;
import com.rebwon.taskagile.domain.common.mail.MessageVariable;
import com.rebwon.taskagile.domain.model.user.RegistrationException;
import com.rebwon.taskagile.domain.model.user.RegistrationManagement;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.events.UserRegisteredEvent;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private RegistrationManagement registrationManagement;
	private DomainEventPublisher domainEventPublisher;
	private MailManager mailManager;

	public UserServiceImpl(RegistrationManagement registrationManagement,
		DomainEventPublisher domainEventPublisher, MailManager mailManager) {
		this.registrationManagement = registrationManagement;
		this.domainEventPublisher = domainEventPublisher;
		this.mailManager = mailManager;
	}

	@Override
	public void register(RegistrationCommand command) throws RegistrationException {
		Assert.notNull(command, "Parameter `command` must not be null");
		User newUser = registrationManagement.register(
			command.getUsername(),
			command.getEmailAddress(),
			command.getPassword()
		);

		sendWelcomeMessage(newUser);
		domainEventPublisher.publish(new UserRegisteredEvent(newUser));
	}

	private void sendWelcomeMessage(User user) {
		mailManager.send(
			user.getEmailAddress(),
			"Welcome to TaskAgile",
			"welcome.ftl",
			MessageVariable.from("user", user)
		);
	}
}
