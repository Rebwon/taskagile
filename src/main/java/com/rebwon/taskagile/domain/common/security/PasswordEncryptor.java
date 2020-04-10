package com.rebwon.taskagile.domain.common.security;

public interface PasswordEncryptor {
	String encrypt(String rawPassword);
}
