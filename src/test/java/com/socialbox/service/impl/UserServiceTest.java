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

  @Test
  void testGetAllUsers() {
    List<User> users = Collections.singletonList(getUser());

    Mockito.when(userRepository.findAll()).thenReturn(users);

    List<User> allUsers = userService.getAllUsers();

    Assertions.assertNotNull(allUsers);
    Assertions.assertEquals(1, allUsers.size());
    Assertions.assertEquals(users, allUsers);
  }

  @Test
  void testGetUserById_WhenUserPresent() {
    Integer id = 123;
    User expectedUser = getUser();

    Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

    User user = userService.getUserById(id);

    Assertions.assertNotNull(user);
    Assertions.assertEquals(user, expectedUser);
  }

  @Test
  void testGetUserById_WhenUserNotPresent() {
    Integer id = 123;

    Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

    User user = userService.getUserById(id);

    Assertions.assertNull(user);
  }

  @Test
  void testSaveUser_WhenUserFound() {
    User user = getUser();

    Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

    User actualUser = userService.loginUser(user);
    Assertions.assertNotNull(actualUser);
  }

  @Test
  void testSaveUser_WhenUserNotFound() {
    User user = getUser();

    User actualUser = userService.loginUser(user);
    Assertions.assertNull(actualUser);
  }

  private User getUser() {
    return User.builder().userId(1).email("userMail").build();
  }
}
