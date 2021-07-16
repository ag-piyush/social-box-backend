package com.socialbox.service.impl;

import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.MovieService;
import com.socialbox.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock UserRepository userRepository;
  MovieService movieService;
  UserService userService;

  @BeforeEach
  void setUp() {
    userService = new UserServiceImpl(userRepository, movieService);
  }
}
