package com.guessmewhat.backend.domain.user.domain.repository.jpa;

import com.guessmewhat.backend.domain.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    UserEntity getByEmail(String email);

}
