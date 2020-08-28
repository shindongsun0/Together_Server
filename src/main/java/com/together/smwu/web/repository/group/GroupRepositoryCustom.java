package com.together.smwu.web.repository.group;

import java.util.List;

public interface GroupRepositoryCustom {

    List<Group> getAllGroups();

    long updateGroupImageUrl(long groupId, String imageUrl);

    long updateGroupContent(long groupId, String content);

    long updateGroupTitle(long groupId, String title);

}
