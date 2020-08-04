package com.together.smwu.security.controller.v1;

import com.together.smwu.advice.exception.CUserNotFoundException;
import com.together.smwu.config.security.JwtTokenProvider;
import com.together.smwu.security.entity.User;
import com.together.smwu.security.model.request.UserRequest;
import com.together.smwu.security.model.response.CommonResult;
import com.together.smwu.security.model.response.ListResult;
import com.together.smwu.security.model.response.SingleResult;
import com.together.smwu.security.repository.UserJpaRepo;
import com.together.smwu.security.service.ResponseService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ROLE_USER')")
@Api(tags = {"2. User"})
@RestController
@Slf4j
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService; // 결과를 처리할 Service

    @Autowired
    public UserController(UserJpaRepo userJpaRepo, JwtTokenProvider jwtTokenProvider, ResponseService responseService) {
        this.userJpaRepo = userJpaRepo;
        this.responseService = responseService;
    }

    //    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser(
            @CookieValue(name = "X-AUTH-TOKEN") String accessToken) {
        return responseService.getListResult(userJpaRepo.findAll());
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원을 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById(
            @CookieValue(name = "X-AUTH-TOKEN") String accessToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        return responseService.getSingleResult(userJpaRepo.findByUid(id).orElseThrow(CUserNotFoundException::new));
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token",
//                    required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "UserRequest.Modify", required = true)
            @RequestBody UserRequest.Modify modify,
            @CookieValue(name = "X-AUTH-TOKEN") String accessToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        User user = userJpaRepo.findByUid(id).orElseThrow(CUserNotFoundException::new);
        user.setName(modify.getName());
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable long msrl,
            @CookieValue(name = "X-AUTH-TOKEN") String accessToken) {
        userJpaRepo.deleteById(msrl);
        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}
