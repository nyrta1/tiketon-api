package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
//    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
//    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
