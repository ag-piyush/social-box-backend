package com.socialbox.controllers;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.GroupMovieDTO;
import com.socialbox.dto.InviteDTO;
import com.socialbox.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/group")
@RestController
public class GroupController {

  private final GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping
  public ResponseEntity<List<GroupDTO>> getGroups(@RequestParam("ids") List<Integer> ids) {
    return ResponseEntity.ok(this.groupService.getAllGroups(ids));
  }

  @GetMapping("/{id}")
  public ResponseEntity<GroupDTO> getGroup(@PathVariable("id") Integer id) {

    GroupDTO foundGroup = this.groupService.getGroupById(id);

    if (foundGroup == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(foundGroup);
  }

  @PostMapping
  public ResponseEntity<GroupDTO> saveGroup(@RequestBody GroupDTO group) {
    GroupDTO groupDTO = this.groupService.saveGroup(group);

    if (groupDTO == null) return ResponseEntity.badRequest().build();

    return ResponseEntity.ok(groupDTO);
  }

  @PostMapping("/movie")
  public ResponseEntity<List<GroupMovieDTO>> addGroupMovie(@RequestBody List<GroupMovieDTO> groupMovies) {
    return ResponseEntity.ok(this.groupService.saveMovie(groupMovies));
  }

  @GetMapping("/invite")
  public ResponseEntity<InviteDTO> sendInvite(@RequestParam("groupId") Integer groupId,
      @RequestParam("userId") Integer userId) {
    InviteDTO inviteDTO = this.groupService.sendInvite(groupId, userId);

    if (inviteDTO == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(inviteDTO);
  }

  @PostMapping("/{id}/user")
  public ResponseEntity<GroupDTO> addUserToGroup(@PathVariable("id") Integer groupId,
      @RequestParam("userId") Integer userId) {
    GroupDTO group = this.groupService.addUserToGroup(groupId, userId);

    if (group == null) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(group);
  }

  @DeleteMapping("/{id}/user")
  public ResponseEntity<GroupDTO> removeUserFromGroup(@PathVariable("id") Integer groupId,
      @RequestParam("userId") Integer userId) {
    GroupDTO group = this.groupService.removeUserFromGroup(groupId, userId);

    if (group == null) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(group);
  }
}
