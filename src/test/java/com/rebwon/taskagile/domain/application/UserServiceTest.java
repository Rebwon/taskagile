package com.rebwon.taskagile.domain.application;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rebwon.taskagile.domain.application.commands.RegistrationCommand;
import com.rebwon.taskagile.domain.application.impl.UserServiceImpl;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.common.mail.MailManager;
import com.rebwon.taskagile.domain.model.user.RegistrationException;
import com.rebwon.taskagile.domain.model.user.RegistrationManagement;
import com.rebwon.taskagile.domain.model.user.UsernameExistsException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock private RegistrationManagement registrationManagementMock;
	@Mock private DomainEventPublisher domainEventPublisherMock;
	@Mock private MailManager mailManagerMock;
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		userService = new UserServiceImpl(registrationManagementMock, domainEventPublisherMock, mailManagerMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void register_nullCommand_shouldFail() throws Exception {
		userService.register(null);
	}

	@Test(expected = RegistrationException.class)
	public void register_existingUsername_shouldFail() throws Exception {
		String username = "existing";
		String emailAddress = "rebwon@gmail.com";
		String password = "password!";

		doThrow(UsernameExistsException.class).when(registrationManagementMock)
			.register(username, emailAddress, password);

		RegistrationCommand command = new RegistrationCommand(username, password, emailAddress);
		userService.register(command);
	}
}