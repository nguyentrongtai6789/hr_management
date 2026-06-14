package com.hr_management.repository;

import com.hr_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUserNameAndIsActive(String username, int isActive);
}
