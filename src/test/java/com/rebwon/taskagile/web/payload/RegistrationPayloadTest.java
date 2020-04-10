package com.rebwon.taskagile.web.payload;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

public class RegistrationPayloadTest {
	private Validator validator;

	@Before
	public void setUp() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void validate_blankPayload_shouldFail() {
		RegistrationPayload payload = new RegistrationPayload();
		Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
		assertEquals(3, violations.size());
	}

	@Test
	public void validate_payloadWithInvalidEmail_shouldFail() {
		RegistrationPayload payload = new RegistrationPayload();
		payload.setEmailAddress("BadEmailAddress");
		payload.setUsername("rebwon");
		payload.setPassword("password");

		Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_payloadWithEmailAddressLongerThan100_shouldFail() {
		int maxLocalPartLength = 64;
		String localPart = RandomStringUtils.random(maxLocalPartLength, true, true);
		int usedLength = maxLocalPartLength + "@".length() + ".com".length();
		String domain = RandomStringUtils.random(101 - usedLength, true, true);

		RegistrationPayload payload = new RegistrationPayload();
		payload.setEmailAddress(localPart + "@" + domain + ".com");
		payload.setUsername("rebwon");
		payload.setPassword("password");

		Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_payloadWithUsernameShorterThan2_shouldFail() {
		RegistrationPayload payload = new RegistrationPayload();
		String usernameTooShort = RandomStringUtils.random(1);
		payload.setUsername(usernameTooShort);
		payload.setPassword("password");
		payload.setEmailAddress("rebwon@gmail.com");

		Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_payloadWithUsernameLongerThan50_shouldFail() {
		RegistrationPayload payload = new RegistrationPayload();
		String usernameTooLong = RandomStringUtils.random(51);
		payload.setUsername(usernameTooLong);
		payload.setPassword("MyPassword");
		payload.setEmailAddress("rebwon@gmail.com");

		Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_payloadWithPasswordShorterThan6_shouldFail() {
		RegistrationPayload payload = new RegistrationPayload();
		String passwordTooShort = RandomStringUtils.random(5);
		payload.setPassword(passwordTooShort);
		payload.setUsername("rebwon");
		payload.setEmailAddress("rebwon@gmail.com");

		Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
		assertEquals(1, violations.size());
	}

	@Test
	public void validate_payloadWithPasswordLongerThan30_shouldFail() {
		RegistrationPayload payload = new RegistrationPayload();
		String passwordTooLong = RandomStringUtils.random(31);
		payload.setPassword(passwordTooLong);
		payload.setUsername("rebwon");
		payload.setEmailAddress("rebwon@gmail.com");

		Set<ConstraintViolation<RegistrationPayload>> violations = validator.validate(payload);
		assertEquals(1, violations.size());
	}
}