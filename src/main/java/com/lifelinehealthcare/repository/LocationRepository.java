package com.lifelinehealthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lifelinehealthcare.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
