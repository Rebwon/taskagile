package com.rebwon.taskagile.web.apis;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rebwon.taskagile.config.SecurityConfiguration;
import com.rebwon.taskagile.domain.application.UserService;
import com.rebwon.taskagile.domain.model.user.EmailAddressExistsException;
import com.rebwon.taskagile.domain.model.user.UsernameExistsException;
import com.rebwon.taskagile.utils.JsonUtils;
import com.rebwon.taskagile.web.payload.RegistrationPayload;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {SecurityConfiguration.class,
  RegistrationApiController.class})
@WebMvcTest
public class RegistrationApiControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	public void register_blankPayload_shouldFailAndReturn400() throws Exception {
		mockMvc.perform(post("/api/registrations"))
			.andExpect(status().is(400));
	}

	@Test
	public void register_existedUsername_shouldFailAndReturn400() throws Exception {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("exist");
		payload.setEmailAddress("rebwon@gmail.com");
		payload.setPassword("password!");

		doThrow(UsernameExistsException.class)
			.when(userService)
			.register(payload.toCommand());

		mockMvc.perform(
			post("/api/registrations")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JsonUtils.toJson(payload)))
			.andExpect(status().is(400))
			.andExpect(jsonPath("$.message").value("Username already exists."));
	}

	@Test
	public void register_existedEmailAddress_shouldFailAndReturn400() throws Exception {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("rebwon");
		payload.setEmailAddress("exist@taskagile.com");
		payload.setPassword("password!");

		doThrow(EmailAddressExistsException.class)
			.when(userService)
			.register(payload.toCommand());

		mockMvc.perform(
			post("/api/registrations")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JsonUtils.toJson(payload)))
			.andExpect(status().is(400))
			.andExpect(jsonPath("$.message").value("Email address already exists"));
	}

	@Test
	public void register_validPayload_shouldSucceedAndReturn201() throws Exception {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setUsername("rebwon");
		payload.setEmailAddress("rebwon@gmail.com");
		payload.setPassword("password!");

		doNothing().when(userService)
			.register(payload.toCommand());

		mockMvc.perform(
			post("/api/registrations")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JsonUtils.toJson(payload)))
			.andExpect(status().is(201));
	}
}
