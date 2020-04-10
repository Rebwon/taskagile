package com.rebwon.taskagile.web.apis;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rebwon.taskagile.domain.application.UserService;
import com.rebwon.taskagile.domain.model.user.EmailAddressExistsException;
import com.rebwon.taskagile.domain.model.user.RegistrationException;
import com.rebwon.taskagile.domain.model.user.UsernameExistsException;
import com.rebwon.taskagile.web.payload.RegistrationPayload;
import com.rebwon.taskagile.web.results.ApiResult;
import com.rebwon.taskagile.web.results.Result;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistrationApiController {
	private final UserService userService;

	@PostMapping("/api/registrations")
	public ResponseEntity<ApiResult> register(@Valid @RequestBody RegistrationPayload payload) {
		try{
			userService.register(payload.toCommand());
			return Result.created();
		} catch (RegistrationException e){
			String errorMessage = "Registration Failed";
			if(e instanceof UsernameExistsException) {
				errorMessage = "Username already exists.";
			} else if(e instanceof EmailAddressExistsException) {
				errorMessage = "Email address already exists";
			}
			return Result.failure(errorMessage);
		}
	}
}
