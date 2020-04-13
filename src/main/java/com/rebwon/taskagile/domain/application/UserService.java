package com.rebwon.taskagile.domain.application;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.rebwon.taskagile.domain.application.commands.RegistrationCommand;
import com.rebwon.taskagile.domain.model.user.RegistrationException;
import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserId;

public interface UserService extends UserDetailsService {
  User findById(UserId userId);
	void register(RegistrationCommand command) throws RegistrationException;
}
