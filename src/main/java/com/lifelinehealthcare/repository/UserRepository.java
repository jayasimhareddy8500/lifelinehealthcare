package com.lifelinehealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifelinehealthcare.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
