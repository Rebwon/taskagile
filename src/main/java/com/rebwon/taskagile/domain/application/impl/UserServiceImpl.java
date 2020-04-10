package com.rebwon.taskagile.domain.application.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.rebwon.taskagile.domain.application.UserService;
import com.rebwon.taskagile.domain.application.commands.RegistrationCommand;
import com.rebwon.taskagile.domain.model.user.RegistrationException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Override
	public void register(RegistrationCommand command) throws RegistrationException {
		Assert.notNull(command, "Parameter `command` must not be null");

	}
}
