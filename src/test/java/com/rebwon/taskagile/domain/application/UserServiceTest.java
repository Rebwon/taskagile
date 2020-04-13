package com.rebwon.taskagile.domain.application;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rebwon.taskagile.domain.application.commands.RegistrationCommand;
import com.rebwon.taskagile.domain.application.impl.UserServiceImpl;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.common.mail.MailManager;
import com.rebwon.taskagile.domain.common.mail.MessageVariable;
import com.rebwon.taskagile.domain.model.user.EmailAddressExistsException;
import com.rebwon.taskagile.domain.model.user.RegistrationException;
import com.rebwon.taskagile.domain.model.user.RegistrationManagement;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserRepository;
import com.rebwon.taskagile.domain.model.user.UsernameExistsException;
import com.rebwon.taskagile.domain.model.user.events.UserRegisteredEvent;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock private RegistrationManagement registrationManagementMock;
  @Mock private DomainEventPublisher domainEventPublisherMock;
  @Mock private MailManager mailManagerMock;
  @Mock private UserRepository repositoryMock;
	private UserService userServiceMock;

	@Before
	public void setUp() {
		userServiceMock = new UserServiceImpl(registrationManagementMock, domainEventPublisherMock, mailManagerMock, repositoryMock);
	}

  @Test
  public void loadUserByUsername_emptyUsername_shouldFail() {
    Exception exception = null;
    try {
      userServiceMock.loadUserByUsername("");
    } catch (Exception e) {
      exception = e;
    }
    assertNotNull(exception);
    assertTrue(exception instanceof UsernameNotFoundException);
    verify(repositoryMock, never()).findByUsername("");
    verify(repositoryMock, never()).findByEmailAddress("");
  }

  @Test
  public void loadUserByUsername_notExistUsername_shouldFail() {
    String notExistUsername = "NotExistUsername";
    when(repositoryMock.findByUsername(notExistUsername)).thenReturn(null);
    Exception exception = null;
    try {
      userServiceMock.loadUserByUsername(notExistUsername);
    } catch (Exception e) {
      exception = e;
    }
    assertNotNull(exception);
    assertTrue(exception instanceof UsernameNotFoundException);
    verify(repositoryMock).findByUsername(notExistUsername);
    verify(repositoryMock, never()).findByEmailAddress(notExistUsername);
  }

  @Test
  public void loadUserByUsername_existUsername_shouldSucceed() throws IllegalAccessException {
    String existUsername = "ExistUsername";
    User foundUser = User.create(existUsername, "user@taskagile.com", "EncryptedPassword!");
    foundUser.updateName("Test", "User");
    // Found user from the database should have id. And since no setter of
    // id is available in User, we have to write the value to it using reflection
    //
    // Besides creating an actual instance of User, we can also create a user
    // mock, like the following.
    // User mockUser = Mockito.mock(User.class);
    // when(mockUser.getUsername()).thenReturn(existUsername);
    // when(mockUser.getPassword()).thenReturn("EncryptedPassword!");
    // when(mockUser.getId()).thenReturn(1L);
    FieldUtils.writeField(foundUser, "id", 1L, true);
    when(repositoryMock.findByUsername(existUsername)).thenReturn(foundUser);
    Exception exception = null;
    UserDetails userDetails = null;
    try {
      userDetails = userServiceMock.loadUserByUsername(existUsername);
    } catch (Exception e) {
      exception = e;
    }
    assertNull(exception);
    verify(repositoryMock).findByUsername(existUsername);
    verify(repositoryMock, never()).findByEmailAddress(existUsername);
    assertNotNull(userDetails);
    assertEquals(existUsername, userDetails.getUsername());
    assertTrue(userDetails instanceof SimpleUser);
  }

  @Test(expected = IllegalArgumentException.class)
  public void register_nullCommand_shouldFail() throws RegistrationException {
    userServiceMock.register(null);
  }

  @Test(expected = RegistrationException.class)
  public void register_existingUsername_shouldFail() throws RegistrationException {
    String username = "existing";
    String emailAddress = "sunny@taskagile.com";
    String password = "MyPassword!";

    doThrow(UsernameExistsException.class).when(registrationManagementMock)
      .register(username, emailAddress, password);

    RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);
    userServiceMock.register(command);
  }

  @Test(expected = RegistrationException.class)
  public void register_existingEmailAddress_shouldFail() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "existing@taskagile.com";
    String password = "MyPassword!";

    doThrow(EmailAddressExistsException.class).when(registrationManagementMock)
      .register(username, emailAddress, password);

    RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);
    userServiceMock.register(command);
	}

  @Test
  public void register_validCommand_shouldSucceed() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "sunny@taskagile.com";
    String password = "MyPassword!";
    User newUser = User.create(username, emailAddress, password);

    when(registrationManagementMock.register(username, emailAddress, password))
      .thenReturn(newUser);

    RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);

    userServiceMock.register(command);

    verify(mailManagerMock).send(
      emailAddress,
      "Welcome to TaskAgile",
      "welcome.ftl",
      MessageVariable.from("user", newUser)
    );
    verify(domainEventPublisherMock).publish(new UserRegisteredEvent(newUser));
  }
}
