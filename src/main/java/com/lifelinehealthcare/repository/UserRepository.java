package com.lifelinehealthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lifelinehealthcare.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByPhoneNumberAndPassword(Long phoneNumber, String password);

	@Query("SELECT u FROM User u WHERE u.name like %:doctorName% AND u.category like %:category%")
	List<User> getUsersBySearchValue(@Param("doctorName") String doctorName, @Param("category") String category);
}
