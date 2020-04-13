package com.rebwon.taskagile.domain.model.user;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserFinder {
  private final UserRepository userRepository;

  public User find(String usernameOrEmailAddress) throws UserNotFoundException {
    User user;
    if (usernameOrEmailAddress.contains("@")) {
      user = userRepository.findByEmailAddress(usernameOrEmailAddress);
    } else {
      user = userRepository.findByUsername(usernameOrEmailAddress);
    }
    if (user == null) {
      throw new UserNotFoundException();
    }
    return user;
  }
}
