package com.socialbox.controllers;

import com.socialbox.dto.UserDTO;
import com.socialbox.dto.UserMovieDTO;
import com.socialbox.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(this.userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id) {

    UserDTO foundUser = this.userService.getUserById(id);

    if (foundUser == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(foundUser);
  }

  @PostMapping
  public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO user) {
    UserDTO createdUser = this.userService.loginUser(user);

    if (createdUser.getDisplayName() == null || createdUser.getDisplayName().isEmpty()) {
      return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    return ResponseEntity.ok(createdUser);
  }

  @GetMapping("/{id}/movies")
  public ResponseEntity<List<UserMovieDTO>> getMovies(@PathVariable("id") Integer id) {

    List<UserMovieDTO> movieDTOS = this.userService.getMovies(id);

    if (movieDTOS == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(movieDTOS);
  }

  @PostMapping("/{id}/settings")
  public ResponseEntity<UserDTO> updateSettingsForUser(@PathVariable("id") Integer id, @RequestBody UserDTO userDTO) {
    UserDTO updatedUserDTO = this.userService.saveSettingsForUser(userDTO, id);
    if (updatedUserDTO == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(updatedUserDTO);
  }
}
