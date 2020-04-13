package com.rebwon.taskagile.domain.model.user;

public interface UserRepository {
	User findByUsername(String username);
	User findByEmailAddress(String emailAddress);
	User findById(UserId userId);
	void save(User user);
}
