package com.socialbox.service.impl;

import com.socialbox.dto.*;
import com.socialbox.model.Group;
import com.socialbox.model.GroupMovie;
import com.socialbox.model.InviteLink;
import com.socialbox.model.User;
import com.socialbox.repository.GroupMovieRepository;
import com.socialbox.repository.GroupRepository;
import com.socialbox.service.GroupService;
import com.socialbox.service.InviteLinkService;
import com.socialbox.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

  private final GroupMovieRepository groupMovieRepository;
  private final GroupRepository groupRepository;
  private final UserService userService;
  private final InviteLinkService inviteLinkService;

  @Autowired
  public GroupServiceImpl(
      GroupMovieRepository groupMovieRepository,
      GroupRepository groupRepository,
      UserService userService,
      InviteLinkService inviteLinkService) {
    this.groupMovieRepository = groupMovieRepository;
    this.groupRepository = groupRepository;
    this.userService = userService;
    this.inviteLinkService = inviteLinkService;
  }

  @Override
  public List<GroupDTO> getAllGroups(List<Integer> groupIds) {
    log.info("Getting groups for: {}", groupIds);
    List<Group> groupList = new ArrayList<>(this.groupRepository.findAllById(groupIds));
    List<GroupDTO> groupDTOList = new ArrayList<>();

    for (Group group : groupList) {
      GroupDTO groupDTO =
          GroupDTO.builder()
              .id(group.getId())
              .name(group.getName())
              .photoURL(group.getPhotoURL())
              .adminId(group.getAdmin().getUserId())
              .memberCount(group.getMemberCount())
              .build();
      groupDTOList.add(groupDTO);
    }
    return groupDTOList;
  }

  @Override
  public GroupDTO getGroupById(Integer id) {
    Optional<Group> groupOptional = this.groupRepository.findById(id);
    if (!groupOptional.isPresent()) {
      log.debug("No group found for id: {}", id);
      return null;
    }

    Group group = groupOptional.get();
    List<GroupMovieDTO> groupMovieDTOS = groupMovieTOGroupMovieDTO(group.getMovieList());
    return GroupDTO.builder()
        .id(group.getId())
        .memberCount(group.getMemberCount())
        .name(group.getName())
        .groupMovieDTOList(groupMovieDTOS)
        .adminId(group.getAdmin().getUserId())
        .build();
  }

  @Override
  public GroupDTO saveGroup(GroupDTO groupDTO) {
    log.info("Saving group for id: {}", groupDTO.getId());
    User admin = this.userService.getUser(groupDTO.getAdminId());
    if (admin == null) {
      log.error("No user found with id: {}", groupDTO.getAdminId());
      return null;
    }

    log.info("Fetched admin: {}", admin);
    Group group =
        this.groupRepository.save(
            Group.builder()
                .memberCount(groupDTO.getMemberCount())
                .name(groupDTO.getName())
                .users(Collections.singleton(admin))
                .admin(admin)
                .build());
    Set<Group> groups = admin.getGroups();
    if (groups == null) {
      admin.setGroups(new HashSet<>());
    }
    groupDTO.setId(group.getId());
    return groupDTO;
  }

  public Group updateGroup(Group group) {
    return this.groupRepository.save(group);
  }

  @Override
  public List<GroupMovieDTO> saveMovie(List<GroupMovieDTO> groupMovieDTOS) {
    if (groupMovieDTOS.isEmpty()) {
      log.error("Empty list to be saved");
      return new ArrayList<>();
    }

    Optional<Group> groupOptional =
        this.groupRepository.findById(groupMovieDTOS.get(0).getGroupId());
    Group currentGroup = groupOptional.orElse(null);

    if (currentGroup == null) {
      log.error("Group not found");
      return new ArrayList<>();
    }

    if (currentGroup.getMovieList() == null) {
      log.info("Empty movie list in group!");
      currentGroup.setMovieList(new HashSet<>());
    }

    List<GroupMovie> groupMovies =
        this.groupMovieRepository.saveAll(
            groupMovieDTOS.stream()
                .map(
                    gM ->
                        GroupMovie.builder()
                            .name(gM.getName())
                            .photoURL(gM.getPhotoURL())
                            .rating(gM.getRating())
                            .votes(gM.getVotes())
                            .group(currentGroup)
                            .build())
                .collect(Collectors.toList()));
    currentGroup.getMovieList().addAll(groupMovies);
    log.info("Movie added to group: {}", groupMovies);
    updateGroup(currentGroup);

    for (int i = 0; i < groupMovies.size(); i++) {
      groupMovieDTOS.get(0).setId(groupMovies.get(0).getId());
    }

    return groupMovieDTOS;
  }

  @Override
  public InviteDTO sendInvite(Integer groupId, Integer userId) {
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
  public GroupDTO addUserToGroup(Integer groupId, Integer userId) {
    User user = this.userService.getUser(userId);
    Optional<Group> groupOptional = this.groupRepository.findById(groupId);

    if (user == null) {
      log.error("Invalid userId: {}", userId);
      return null;
    }
    if (!groupOptional.isPresent()) {
      log.error("Invalid groupId: {}", groupId);
      return null;
    }

    if (user.getGroups() == null) {
      log.info("Creating a new groupList for user: {}", userId);
      user.setGroups(new HashSet<>());
    }
    Group group = groupOptional.get();
    user.getGroups().add(group);
    this.userService.updateUser(user);

    if (group.getUsers() == null) {
      log.info("Creating a new userList for group: {}", groupId);
      group.setUsers(new HashSet<>());
    }
    group.getUsers().add(user);
    group = this.updateGroup(group);

    return GroupDTO.builder()
        .id(group.getId())
        .adminId(group.getAdmin().getUserId())
        .memberCount(group.getMemberCount())
        .photoURL(group.getPhotoURL())
        .groupMovieDTOList(groupMovieTOGroupMovieDTO(group.getMovieList()))
        .build();
  }

  @Override
  public GroupDTO removeUserFromGroup(Integer groupId, Integer userId) {
    User user = this.userService.getUser(userId);
    Optional<Group> groupOptional = this.groupRepository.findById(groupId);

    if (user == null) {
      log.error("Invalid userId: {}", userId);
      return null;
    }
    if (!groupOptional.isPresent()) {
      log.error("Invalid groupId: {}", groupId);
      return null;
    }

    Group group = groupOptional.get();
    if (user.getGroups() == null
        || !user.getGroups().contains(group)
        || group.getUsers() == null
        || !group.getUsers().contains(user)) {
      log.error("Invalid userId or groupId");
      return null;
    }

    user.getGroups().remove(group);
    this.userService.updateUser(user);

    group.getUsers().remove(user);
    group = this.updateGroup(group);

    return GroupDTO.builder()
        .id(groupId)
        .adminId(userId)
        .memberCount(group.getMemberCount())
        .photoURL(group.getPhotoURL())
        .groupMovieDTOList(groupMovieTOGroupMovieDTO(group.getMovieList()))
        .build();
  }

  @Override
  public List<GroupMovieDTO> groupMovieTOGroupMovieDTO(Set<GroupMovie> groupMovies) {
    return groupMovies.stream()
        .map(
            m ->
                GroupMovieDTO.builder()
                    .id(m.getId())
                    .groupId(m.getGroup().getId())
                    .name(m.getName())
                    .photoURL(m.getPhotoURL())
                    .rating(m.getRating())
                    .votes(m.getVotes())
                    .reviews(
                        m.getReviews().stream()
                            .map(
                                r ->
                                    ReviewDTO.builder()
                                        .userReviews(r.getUserReviews())
                                        .groupMovieId(r.getGroupMovie().getId())
                                        .movieId(r.getMovie().getId())
                                        .id(r.getId())
                                        .build())
                            .collect(Collectors.toList()))
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDTO> getAllUsers(Integer id) {
    Optional<Group> groupOptional = this.groupRepository.findById(id);

    if (!groupOptional.isPresent()) {
      log.debug("Group not found id: {}", id);
      return null;
    }

    Group group = groupOptional.get();
    Set<User> users = group.getUsers();

    List<UserDTO> userDTOS = new ArrayList<>();

    for (User user : users) {
      UserDTO userDTO =
          UserDTO.builder()
              .id(user.getUserId())
              .name(user.getName())
              .displayName(user.getDisplayName())
              .email(user.getEmail())
              .photoURL(user.getPhotoURL())
              .personalMovieList(
                  this.userService.userRatingsToUserRatingsDTO(user.getPersonalMovieList()))
              .groups(
                  user.getGroups().stream()
                      .map(
                          m ->
                              GroupDTO.builder()
                                  .id(m.getId())
                                  .name(m.getName())
                                  .photoURL(m.getPhotoURL())
                                  .memberCount(m.getMemberCount())
                                  .adminId(m.getAdmin().getUserId())
                                  .groupMovieDTOList(
                                      this.groupMovieTOGroupMovieDTO(m.getMovieList()))
                                  .build())
                      .collect(Collectors.toList()))
              .build();
      userDTOS.add(userDTO);
    }
    return userDTOS;
  }
}
