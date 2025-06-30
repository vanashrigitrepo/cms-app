package com.ibm.cms.controller;


import com.ibm.cms.dto.ForgotPasswordRequestDto;
import com.ibm.cms.dto.LoginResponseDto;
import com.ibm.cms.dto.ResetPasswordRequestDto;
import com.ibm.cms.dto.UserRequestDto;
import com.ibm.cms.entity.Users;
import com.ibm.cms.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@Data
public class UserController {

    // To Call SErvice Class
    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register/user")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userRequestDto) {
        log.info("Register User {} ", userRequestDto);
        userService.registerUser(userRequestDto);
        return new ResponseEntity<>("✅ Registered Successfully", HttpStatus.OK);

    }

    // Login User
//    @PostMapping("login/user")
//    public ResponseEntity<String> loginUser(@RequestBody UserRequestDto userRequestDto){
//        log.info("Login User with username: {}, password{} ", userRequestDto.getUsername(), userRequestDto.getPassword());
//boolean isPresent=userService.loginUSer(userRequestDto.getUsername(), userRequestDto.getPassword());
//if(isPresent){
//    return new ResponseEntity<>("✅ Login successful", HttpStatus.OK);
//}else{
//    return new ResponseEntity<>("❌ Invalid username or password", HttpStatus.UNAUTHORIZED);
//}
//    }
//}

// Another Way To Show Which credential is Wrong either username or pass
// This is returning plain text so we have to pass response as json
//@PostMapping("user/login")
//public ResponseEntity<String> userLogin(@RequestBody UserRequestDto userRequestDto) {
//    log.info("Login user by checking username: {} and password: {} ", userRequestDto.getUsername(), userRequestDto.getPassword());
//
//    String message = userService.userLogin(userRequestDto.getUsername(), userRequestDto.getPassword());
//
//    if (message.equals("✅ Login successful")) {
//        return new ResponseEntity<>("✅ Login successful", HttpStatus.OK);
//    } else {
//        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
//    }
//}
//}

    // Another Way

    @PostMapping("user/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody UserRequestDto userRequestDto) {
        log.info("Login user by checking email: {} and password: {}", userRequestDto.getEmail(), userRequestDto.getPassword());

        String message = userService.userLogin(userRequestDto.getEmail(), userRequestDto.getPassword());

        boolean success = message.equals("✅ Login successful");

        LoginResponseDto responseDto = new LoginResponseDto(message, success, success ? userRequestDto.getEmail() : null);

        return new ResponseEntity<>(responseDto, success ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

//    Password reset
    @PostMapping("/forgot/password/Response-string")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordRequestDto forgotPassword){
        log.info("Reset Password Using email {} ", forgotPassword.getEmail());
        String message= userService.passwordReset(forgotPassword.getEmail());
    return new ResponseEntity<>(message, message.startsWith("✅")? HttpStatus.OK:HttpStatus.UNAUTHORIZED);
    }

// Reset PAssword with Real Time Link to Email

    // Reset password using token
    @PostMapping("/forgot/password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDto dto) {
        String message = userService.passwordReset(dto.getEmail());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/reset/password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDto resetDto) {
        String message = userService.resetUserPassword(resetDto.getResetToken(), resetDto.getNewPassword());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}



