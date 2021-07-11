package com.socialbox.service.impl;

import com.socialbox.dto.GroupDTO;
import com.socialbox.dto.InviteDTO;
import com.socialbox.model.Group;
import com.socialbox.model.GroupMovie;
import com.socialbox.model.InviteLink;
import com.socialbox.model.User;
import com.socialbox.repository.GroupRepository;
import com.socialbox.service.GroupService;
import com.socialbox.service.InviteLinkService;
import com.socialbox.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final UserService userService;
  private final InviteLinkService inviteLinkService;

  @Autowired
  public GroupServiceImpl(GroupRepository groupRepository,
      UserService userService, InviteLinkService inviteLinkService) {
    this.groupRepository = groupRepository;
    this.userService = userService;
    this.inviteLinkService = inviteLinkService;
  }

  @Override
  public List<GroupDTO> getAllGroups(List<String> groupIds) {
    List<Group> groupList = new ArrayList<>(this.groupRepository.findAllById(groupIds));
    List<GroupDTO> groupDTOList = new ArrayList<>();

    for (Group group : groupList) {
      GroupDTO groupDTO =
          GroupDTO.builder()
              .id(group.getId())
              .name(group.getName())
              .photoURL(group.getPhotoURL())
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
  public Group createGroup(GroupDTO group) {
    return this.groupRepository.save(
        Group.builder()
            .id(group.getId())
            .name(group.getName())
            .photoURL(group.getPhotoURL())
            .memberCount(1)
            .movieList(new ArrayList<>())
            .users(new ArrayList<>())
            .admin(group.getAdmin())
            .build());
  }

  @Override
  public List<GroupMovie> saveMovie(List<GroupMovie> groupMovies) {
    if (groupMovies.isEmpty()) {
      log.error("Empty list to be saved");
      return new ArrayList<>();
    }

    Optional<Group> groupOptional =
        this.groupRepository.findById(groupMovies.get(0).getGroup().getId());
    Group currentGroup = groupOptional.orElse(null);

    if (currentGroup == null) {
      log.error("Group not found");
      return new ArrayList<>();
    }

    if (currentGroup.getMovieList() == null) {
      log.info("Empty movie list in group!");
      currentGroup.setMovieList(new ArrayList<>());
    }

    currentGroup.getMovieList().addAll(groupMovies);
    log.info("Movie added to group: {}", groupMovies);
    saveGroup(currentGroup);

    return groupMovies;
  }

  @Override
  public InviteDTO sendInvite(String groupId, String userId) {
    Group currGroup = this.groupRepository.findById(groupId).orElse(null);

    if (currGroup == null) {
      log.error("No group found.");
      return null;
    } else if (!currGroup.getAdmin().getUserId().equals(userId)) {
      log.error("Given user is not an admin for the group.");
      return null;
    }

    InviteLink inviteLink = new InviteLink();
    inviteLink.setUrl(this.inviteLinkService.createLink(10));

    return InviteDTO.builder()
        .content("Join my group with the following link:")
        .link(inviteLink)
        .build();
  }

  @Override
  public Group addUserToGroup(String groupId, String userId) {
    User user = this.userService.getUserById(userId);
    Group group = this.getGroup(groupId);

    if (user == null) {
      log.error("Invalid userId: {}", userId);
      return null;
    }
    if (group == null) {
      log.error("Invalid groupId: {}", groupId);
      return null;
    }

    if (user.getGroups() == null) {
      log.info("Creating a new groupList for user: {}", userId);
      user.setGroups(new ArrayList<>());
    }
    user.getGroups().add(group);
    this.userService.saveUser(user);

    if (group.getUsers() == null) {
      log.info("Creating a new userList for group: {}", groupId);
      group.setUsers(new ArrayList<>());
    }
    group.getUsers().add(user);
    return this.saveGroup(group);
  }

  @Override
  public Group removeUserFromGroup(String groupId, String userId) {
    User user = this.userService.getUserById(userId);
    Group group = this.getGroup(groupId);

    if (user == null) {
      log.error("Invalid userId: {}", userId);
      return null;
    }
    if (group == null) {
      log.error("Invalid groupId: {}", groupId);
      return null;
    }

    if (user.getGroups() == null
        || !user.getGroups().contains(group)
        || group.getUsers() == null
        || !group.getUsers().contains(user)) {
      log.error("Invalid userId or groupId");
      return null;
    }

    user.getGroups().remove(group);
    this.userService.saveUser(user);

    group.getUsers().remove(user);
    return this.saveGroup(group);
  }
}
