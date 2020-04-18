package com.rebwon.taskagile.domain.application.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.rebwon.taskagile.domain.application.UserService;
import com.rebwon.taskagile.domain.application.commands.RegistrationCommand;
import com.rebwon.taskagile.domain.common.event.DomainEventPublisher;
import com.rebwon.taskagile.domain.common.mail.MailManager;
import com.rebwon.taskagile.domain.common.mail.MessageVariable;
import com.rebwon.taskagile.domain.model.user.RegistrationException;
import com.rebwon.taskagile.domain.model.user.RegistrationManagement;
import com.rebwon.taskagile.domain.model.user.SimpleUser;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserId;
import com.rebwon.taskagile.domain.model.user.UserRepository;
import com.rebwon.taskagile.domain.model.user.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final RegistrationManagement registrationManagement;
	private final DomainEventPublisher domainEventPublisher;
	private final MailManager mailManager;
	private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if(StringUtils.isEmpty(username)){
      throw new UsernameNotFoundException("No user found");
    }
    User user;
    if(username.contains("@")) {
      user = userRepository.findByEmailAddress(username);
    } else{
      user = userRepository.findByUsername(username);
    }
    if(user == null) {
      throw new UsernameNotFoundException("No user found by `" + username + "`");
    }
    return new SimpleUser(user);
  }

  @Override
  public User findById(UserId userId) {
    return userRepository.findById(userId);
  }

  @Override
	public void register(RegistrationCommand command) throws RegistrationException {
		Assert.notNull(command, "Parameter `command` must not be null");
		User newUser = registrationManagement.register(
			command.getUsername(),
			command.getEmailAddress(),
			command.getFirstName(),
			command.getLastName(),
			command.getPassword()
		);

		sendWelcomeMessage(newUser);
		domainEventPublisher.publish(new UserRegisteredEvent(newUser, command));
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
