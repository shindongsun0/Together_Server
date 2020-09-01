package com.together.smwu.domain.group.dao;

import com.together.smwu.domain.group.domain.Group;

import java.util.List;

public interface GroupRepositoryCustom {

    List<Group> getAllGroups();

    long updateGroupImageUrl(long groupId, String imageUrl);

    long updateGroupContent(long groupId, String content);

    long updateGroupTitle(long groupId, String title);

}
