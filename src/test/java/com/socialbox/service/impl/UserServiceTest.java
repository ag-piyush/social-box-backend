package com.socialbox.service.impl;

import com.socialbox.repository.MovieRepository;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.GroupService;
import com.socialbox.service.MovieService;
import com.socialbox.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository userRepository;
  MovieRepository movieRepository;
  MovieService movieService;
  UserService userService;
  GroupService groupService;

  @BeforeEach
  void setUp() {
    userService = new UserServiceImpl(userRepository, movieService, groupService, movieRepository);
  }
}
