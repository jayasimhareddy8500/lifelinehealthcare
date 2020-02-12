package com.lifelinehealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifelinehealthcare.entity.UserSlotBook;

@Repository
public interface UserSlotBookRepository extends JpaRepository<UserSlotBook, Integer> {

}
