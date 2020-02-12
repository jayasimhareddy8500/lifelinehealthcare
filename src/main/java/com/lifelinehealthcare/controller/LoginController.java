package com.lifelinehealthcare.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifelinehealthcare.dto.LoginRequsetDto;
import com.lifelinehealthcare.dto.LoginResponseDto;
import com.lifelinehealthcare.exception.UserNotFoundException;
import com.lifelinehealthcare.service.LoginService;

import lombok.extern.slf4j.Slf4j;
/**
 * This Controller is used to authenticate the user, wheather user is valid or not
 * 
 * 
 * @author Amala
 * @version V1.1
 * @since 12-02-2020
 */

@RestController
@RequestMapping
@CrossOrigin
@Slf4j
public class LoginController {
	@Autowired
	LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> authenticateUser(@Valid @RequestBody LoginRequsetDto loginRequestDto)
			throws UserNotFoundException {
		log.info("Authenticating the User, inside LoginController...");
		return ResponseEntity.ok().body(loginService.authenticateUser(loginRequestDto));
	}


}
