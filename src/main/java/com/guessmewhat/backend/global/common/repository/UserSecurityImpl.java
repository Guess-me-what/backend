package com.guessmewhat.backend.global.common.repository;

import com.guessmewhat.backend.domain.user.dto.User;
import com.guessmewhat.backend.global.security.auth.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityImpl implements UserSecurity{

    @Override
    public User getUser() {
        return ((CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser();
    }

}
