package com.rebwon.taskagile.infra.repository;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.rebwon.taskagile.domain.model.user.User;
import com.rebwon.taskagile.domain.model.user.UserRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class HibernateUserRepositoryTest {

	@TestConfiguration
	public static class UserRepositoryTestContextConfiguration {
		@Bean
		public UserRepository userRepository(EntityManager entityManager) {
			return new HibernateUserRepository(entityManager);
		}
	}

	@Autowired
	private UserRepository userRepository;

	@Test(expected = PersistenceException.class)
	public void save_nullUsernameUser_shouldFail() {
		User invalidUser = User.create(null, "rebwon@gmail.com", "password!");
		userRepository.save(invalidUser);
	}
}