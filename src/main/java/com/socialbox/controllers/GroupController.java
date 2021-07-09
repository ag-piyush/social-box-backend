package com.socialbox.controllers;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.InviteDTO;
import com.socialbox.model.Group;
import com.socialbox.model.GroupMovie;
import com.socialbox.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/group")
@RestController
public class GroupController {

  private final GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping
  public List<GroupDTO> getGroups(@RequestParam("ids") List<String> ids) {
    return this.groupService.getAllGroups(ids);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Group> getGroup(@PathVariable("id") String id) {

    Group foundGroup = this.groupService.getGroup(id);

    if (foundGroup == null) return ResponseEntity.status(401).build();

    return ResponseEntity.ok(foundGroup);
  }

  @PostMapping
  public Group saveGroup(@RequestBody Group group) {
    return this.groupService.createGroup(group);
  }

  @PostMapping("/movie")
  public List<GroupMovie> addGroupMovie(@RequestBody List<GroupMovie> groupMovies) {
    return this.groupService.saveMovie(groupMovies);
  }

  @GetMapping("/invite")
  public InviteDTO sendInvite(@RequestParam String groupId,@RequestParam String userId){
    return this.groupService.sendInvite(groupId, userId);
  }
}
