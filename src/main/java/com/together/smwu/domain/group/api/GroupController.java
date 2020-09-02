package com.together.smwu.domain.group.api;

import com.together.smwu.domain.group.application.GroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping
    public GroupResponse
}
