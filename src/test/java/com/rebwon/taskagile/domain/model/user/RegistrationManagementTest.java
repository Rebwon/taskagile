package com.rebwon.taskagile.domain.model.user;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.rebwon.taskagile.domain.common.security.PasswordEncryptor;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationManagementTest {
	@Mock private UserRepository userRepository;
	@Mock private PasswordEncryptor passwordEncryptor;
	private RegistrationManagement registrationManagement;

	@Before
	public void setUp() throws Exception {
		registrationManagement = new RegistrationManagement(userRepository, passwordEncryptor);
	}

	@Test(expected = UsernameExistsException.class)
	public void register_existedUsername_shouldFail() throws RegistrationException {
		String username = "existUsername";
		String emailAddress = "rebwon@gmail.com";
		String password = "password!";

		when(userRepository.findByUsername(username)).thenReturn(new User());
		registrationManagement.register(username, emailAddress, password);
	}

	@Test(expected = EmailAddressExistsException.class)
	public void register_existedEmailAddress_shouldFail() throws RegistrationException {
		String username = "rebwon";
		String emailAddress = "exist@gmail.com";
		String password = "password!";

		when(userRepository.findByEmailAddress(emailAddress)).thenReturn(new User());
		registrationManagement.register(username, emailAddress, password);
	}

	@Test
	public void register_uppercaseEmailAddress_shouldSucceedAndBecomeLowercase() throws RegistrationException {
		String username = "rebwon";
		String emailAddress = "Rebwon@GmaIl.com";
		String password = "password!";

		registrationManagement.register(username, emailAddress, password);
		User user = User.create(username, emailAddress, password);
		verify(userRepository).save(user);
	}

	@Test
	public void register_newUser_shouldSucceed() throws RegistrationException {
		String username = "rebwon";
		String emailAddress = "rebwon@gmail.com";
		String password = "password!";
		String encryptedPassword = "EncryptedPassword";
		User user = User.create(username, emailAddress, encryptedPassword);

		when(userRepository.findByUsername(username)).thenReturn(null);
		when(userRepository.findByEmailAddress(emailAddress)).thenReturn(null);
		doNothing().when(userRepository).save(user);
		when(passwordEncryptor.encrypt(password)).thenReturn("EncryptedPassword");

		User savedUser = registrationManagement.register(username, emailAddress, password);
		InOrder inOrder = inOrder(userRepository);
		inOrder.verify(userRepository).findByUsername(username);
		inOrder.verify(userRepository).findByEmailAddress(emailAddress);
		inOrder.verify(userRepository).save(user);
		verify(passwordEncryptor).encrypt(password);
		assertEquals("Saved user's password should be encrypted", encryptedPassword, savedUser.getPassword());
	}
}