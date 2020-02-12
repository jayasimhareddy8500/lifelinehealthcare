package com.lifelinehealthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifelinehealthcare.entity.User;
import com.lifelinehealthcare.entity.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {

	Optional<UserDetail> findByUser(User user);
}
