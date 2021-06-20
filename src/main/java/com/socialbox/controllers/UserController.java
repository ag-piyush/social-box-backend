package com.socialbox.controllers;

import com.socialbox.dto.UserMovieDTO;
import com.socialbox.model.User;
import com.socialbox.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return this.userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") String id) {

    User foundUser = this.userService.getUserById(id);

    if (foundUser == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(foundUser);
  }

  @PostMapping
  public ResponseEntity<User> saveUser(@RequestBody User user) {

    User createdUser = this.userService.saveUser(user);

    if (createdUser == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(createdUser);
  }

  @GetMapping("/{id}/movies")
  public ResponseEntity<List<UserMovieDTO>> getMovies(@PathVariable("id") String id) {

    List<UserMovieDTO> movieDTOS = this.userService.getMovies(id);

    if (movieDTOS == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(movieDTOS);
  }
}
