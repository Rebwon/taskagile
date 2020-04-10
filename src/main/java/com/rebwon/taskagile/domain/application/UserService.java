package com.rebwon.taskagile.domain.application;

import com.rebwon.taskagile.domain.application.commands.RegistrationCommand;
import com.rebwon.taskagile.domain.model.user.RegistrationException;

public interface UserService {
	void register(RegistrationCommand command) throws RegistrationException;
}
