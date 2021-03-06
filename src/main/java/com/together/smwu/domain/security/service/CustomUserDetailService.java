package com.together.smwu.domain.security.service;

import com.together.smwu.global.advice.exception.CUserNotFoundException;
import com.together.smwu.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 토큰에 setting된 유저 정보로 회원정보를 조회
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userPk){
        return userRepository.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);

    }
}
