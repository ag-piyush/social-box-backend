package com.socialbox.service.impl;

import com.google.common.collect.Lists;
import com.socialbox.dto.GroupDTO;
import com.socialbox.model.Group;
import com.socialbox.model.GroupMovie;
import com.socialbox.model.User;
import com.socialbox.repository.GroupRepository;
import com.socialbox.service.GroupService;
import com.socialbox.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final UserService userService;

  @Autowired
  public GroupServiceImpl(GroupRepository groupRepository, UserService userService) {
    this.groupRepository = groupRepository;
    this.userService = userService;
  }

  @Override
  public List<GroupDTO> getAllGroups(List<String> groupIds) {
    List<Group> groupList = Lists.newArrayList(this.groupRepository.findAllById(groupIds));
    List<GroupDTO> groupDTOList = new ArrayList<>();

    for (Group group : groupList) {
      GroupDTO groupDTO =
          GroupDTO.builder()
              .groupId(group.getGroupId())
              .groupName(group.getGroupName())
              .groupPhotoURL(group.getGroupPhotoURL())
              .memberCount(group.getMemberCount())
              .build();
      groupDTOList.add(groupDTO);
    }
    return groupDTOList;
  }

  @Override
  public Group getGroup(String id) {
    Optional<Group> groupOptional = this.groupRepository.findById(id);
    return groupOptional.orElse(null);
  }

  @Override
  public Group saveGroup(Group group) {
    return this.groupRepository.save(group);
  }

  @Override
  public Group createGroup(Group group) {
    Group currentGroup = this.groupRepository.save(group);

    User currentUser = this.userService.getUserById(currentGroup.getGroupAdminId());
    if (currentUser.getGroupsId() == null) {
      log.info("No previous groups!");
      currentUser.setGroupsId(new HashSet<>());
    }

    currentUser.getGroupsId().add(currentGroup.getGroupId());
    log.info("Group added!");
    this.userService.saveUser(currentUser);

    currentGroup.setMemberCount(1);
    this.groupRepository.save(currentGroup);

    return currentGroup;
  }

  @Override
  public List<GroupMovie> saveMovie(List<GroupMovie> groupMovies) {

    for (GroupMovie movie : groupMovies) {
      String groupId = movie.getGroupId();
      Optional<Group> groupOptional = this.groupRepository.findById(groupId);
      Group currentGroup = groupOptional.orElse(null);

      if (currentGroup == null) {
        log.info("Group not found");
        continue;
      }

      if (currentGroup.getGroupMovieList() == null) {
        log.info("Empty movie list in group!");
        currentGroup.setGroupMovieList(new ArrayList<>());
      }
      currentGroup.getGroupMovieList().add(movie);
      log.info("Movie added to group: {}", movie);
      saveGroup(currentGroup);
    }

    return groupMovies;
  }
}
