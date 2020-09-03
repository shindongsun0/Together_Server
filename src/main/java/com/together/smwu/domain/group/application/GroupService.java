package com.together.smwu.domain.group.application;

import com.together.smwu.domain.group.dto.GroupRequest;
import com.together.smwu.domain.group.dto.GroupResponse;
import com.together.smwu.domain.user.domain.User;

import java.util.List;

public interface GroupService {

    Long create(GroupRequest requestDto, User user);

    void update(Long groupId, GroupRequest request, User user);

    List<GroupResponse> findByTitle(String groupTitle);

    GroupResponse findByGroupId(Long groupId);

    List<GroupResponse> findAllGroups();

    void deleteGroupById(Long groupId, User user);
}
