package com.socialbox.controllers;

import com.socialbox.dto.GroupDTO;
import com.socialbox.model.Group;
import com.socialbox.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public Group getGroup(@PathVariable("id") String id) {
    return this.groupService.getGroup(id);
  }

  @PostMapping
  public Group saveGroup(@RequestBody Group group) {
    return this.groupService.setAdmin(group);
  }
}
