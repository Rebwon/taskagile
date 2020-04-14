package com.rebwon.taskagile.domain.model.user;

import org.springframework.stereotype.Component;

import com.rebwon.taskagile.domain.common.security.PasswordEncryptor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegistrationManagement {
	private final UserRepository userRepository;
	private final PasswordEncryptor passwordEncryptor;

  public User register(String username, String emailAddress, String firstName, String lastName, String password)
    throws RegistrationException {
    User existingUser = userRepository.findByUsername(username);
    if (existingUser != null) {
      throw new UsernameExistsException();
    }

    existingUser = userRepository.findByEmailAddress(emailAddress.toLowerCase());
    if (existingUser != null) {
      throw new EmailAddressExistsException();
    }

    String encryptedPassword = passwordEncryptor.encrypt(password);
    User newUser = User.create(username, emailAddress.toLowerCase(), firstName, lastName, encryptedPassword);
    userRepository.save(newUser);
    return newUser;
	}
}
