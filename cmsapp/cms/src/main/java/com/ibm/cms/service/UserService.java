package com.ibm.cms.service;

import com.ibm.cms.dto.UserRequestDto;
import com.ibm.cms.entity.Users;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service

public interface UserService {
//Register User
    public void registerUser(UserRequestDto userRequestDto);

    // Login User
//    public boolean loginUSer(String username, String password);

    // Login User and show which credential is wrong
    public String userLogin(String findByEmail, String password);

    // Forgot Password
    public String passwordReset(String email);

    // Reset Password
   public String passwordResetNew(String email);
    public String resetUserPassword(String token, String newPassword);

}