package com.together.smwu.domain.group.api;

import com.together.smwu.domain.group.application.GroupService;
import com.together.smwu.domain.group.dto.GroupRequest;
import com.together.smwu.domain.group.dto.GroupResponse;
import com.together.smwu.domain.security.security.CurrentUser;
import com.together.smwu.domain.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"3.Group"})
@RestController
public class GroupController {
    public static final String GROUP_URI = "/api/group";

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @ApiOperation(value = "Group 생성", notes = "Group을 생성한다")
    @PostMapping(GROUP_URI)
    public ResponseEntity<Void> createGroup(
            @RequestBody GroupRequest request,
            @CurrentUser User user) {
        Long groupId = groupService.create(request, user);
        return ResponseEntity
                .created(URI.create(GROUP_URI + "/" + groupId))
                .build();
    }

    @ApiOperation(value = "Group 수정", notes = "Group을 수정한다.")
    @PutMapping("/api/group/{groupId}")
    public ResponseEntity<Void> updateGroup(
            @ApiParam(value = "groupId", required = true) @PathVariable Long groupId,
            @RequestBody GroupRequest request,
            @CurrentUser User user) {
        groupService.update(groupId, request, user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Group 찾기", notes = "Group을 groupTitle로 찾는다.")
    @GetMapping("/api/group")
    public ResponseEntity<List<GroupResponse>> findByTitle(
            @ApiParam(value = "title", required = true) @RequestParam String title,
            @CurrentUser User user) {
        List<GroupResponse> responses = groupService.findByTitle(title);
        return ResponseEntity.ok(responses);
    }

    @ApiOperation(value = "Group 찾기", notes = "Group을 groupId로 찾는다.")
    @GetMapping("/api/group/{groupId}")
    public ResponseEntity<GroupResponse> findByTitle(
            @ApiParam(value = "title", required = true) @PathVariable Long groupId,
            @CurrentUser User user) {
        GroupResponse groupResponse = groupService.findByGroupId(groupId);
        return ResponseEntity.ok(groupResponse);
    }

    @ApiOperation(value = "모든 Group 조회", notes = "모든 Group을 조회한다.")
    @GetMapping("/api/group/all")
    public ResponseEntity<List<GroupResponse>> findAllGroups(
            @CurrentUser User user) {
        List<GroupResponse> groupResponses = groupService.findAllGroups();
        return ResponseEntity.ok(groupResponses);
    }

    @ApiOperation(value = "Group 삭제", notes = "Group을 groupId로 삭제한다.")
    @DeleteMapping("/api/group/{groupId}")
    public ResponseEntity<Void> deleteGroupById(
            @ApiParam(value = "groupId", required = true) @PathVariable Long groupId,
            @CurrentUser User user) {
        groupService.deleteGroupById(groupId, user);
        return ResponseEntity.noContent().build();
    }
}
