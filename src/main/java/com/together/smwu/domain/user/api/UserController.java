package com.together.smwu.domain.user.api;

import com.together.smwu.global.advice.exception.CUserNotFoundException;
import com.together.smwu.domain.security.security.CurrentUser;
import com.together.smwu.domain.security.config.jwt.JwtTokenProvider;
import com.together.smwu.domain.user.dto.request.UserRequest;
import com.together.smwu.domain.user.dto.response.CommonResult;
import com.together.smwu.domain.user.dto.response.ListResult;
import com.together.smwu.domain.user.dto.response.SingleResult;
import com.together.smwu.domain.security.service.ResponseService;
import com.together.smwu.domain.user.domain.User;
import com.together.smwu.domain.user.dao.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"2. User"})
@RestController
@Slf4j
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;
    private final ResponseService responseService; // 결과를 처리할 Service

    @Autowired
    public UserController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, ResponseService responseService) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser(@CurrentUser User user) {
        return responseService.getListResult(userRepository.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById(@CurrentUser User user) {

        return responseService.getSingleResult(userRepository.findByUid(user.getUid())
                .orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "UserRequest.Modify", required = true)
            @RequestBody UserRequest.Modify modify,
            @CurrentUser User user) {
        user.setName(modify.getName());
        return responseService.getSingleResult(userRepository.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long msrl,
            @CurrentUser User user) {
        userRepository.deleteById(msrl);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}
