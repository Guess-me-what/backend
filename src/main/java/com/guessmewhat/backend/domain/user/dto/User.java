package com.guessmewhat.backend.domain.user.dto;

import com.guessmewhat.backend.domain.user.domain.entity.UserEntity;
import com.guessmewhat.backend.domain.user.domain.enums.UserRole;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String email;
    private String nickname;
    private String password;
    private UserRole userRole;

    public User toUser(UserEntity userEntity){
        return User.builder()
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .password(userEntity.getPassword())
                .userRole(userEntity.getUserRole())
                .build();
    }

}
