package com.socialbox.service.impl;

import com.google.common.collect.Lists;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final UserService userService;
  private final InviteLinkService inviteLinkService;

  @Autowired
  public GroupServiceImpl(GroupRepository groupRepository, UserService userService, InviteLinkService inviteLinkService) {
    this.groupRepository = groupRepository;
    this.userService = userService;
    this.inviteLinkService = inviteLinkService;
  }

  @Override
  public List<GroupDTO> getAllGroups(List<String> groupIds) {
    List<Group> groupList = Lists.newArrayList(this.groupRepository.findAllById(groupIds));
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
  public Group createGroup(Group group) {
    Group currentGroup = this.groupRepository.save(group);

    User currentUser = this.userService.getUserById(currentGroup.getAdminId());
    if (currentUser.getGroupsId() == null) {
      log.info("No previous groups!");
      currentUser.setGroupsId(new HashSet<>());
    }

    currentUser.getGroupsId().add(currentGroup.getId());
    log.info("Group with id: {} added!", currentGroup.getId());
    this.userService.saveUser(currentUser);

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

      if (currentGroup.getMovieList() == null) {
        log.info("Empty movie list in group!");
        currentGroup.setMovieList(new ArrayList<>());
      }
      currentGroup.getMovieList().add(movie);
      log.info("Movie added to group: {}", movie);
      saveGroup(currentGroup);
    }

    return groupMovies;
  }

  @Override
  public InviteDTO sendInvite(String groupId, String userId) {
    Group currGroup = this.groupRepository.findById(groupId).orElse(null);

    if (currGroup == null) {
      log.error("No group found.");
    } else if (!currGroup.getAdminId().equals(userId)) {
      log.error("Given user is not an admin for the group.");
    }

    InviteLink inviteLink = new InviteLink();
    inviteLink.setURL(this.inviteLinkService.createLink(10));

    return InviteDTO.builder()
            .content("Join my group with the following link:")
            .link(inviteLink)
            .build();
  }
}
