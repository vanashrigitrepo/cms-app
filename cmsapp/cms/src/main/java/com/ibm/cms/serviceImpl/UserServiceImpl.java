package com.ibm.cms.serviceImpl;

import com.ibm.cms.dao.UserRepository;
import com.ibm.cms.dto.UserRequestDto;
import com.ibm.cms.entity.Users;
import com.ibm.cms.mapper.UserMapper;
import com.ibm.cms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository; // to use repo methods

    /**
     * @param users
     */
    @Override
    public void registerUser(UserRequestDto userRequestDto) {
        log.info("Registering User {} ", userRequestDto);
        Users users = UserMapper.mapJsonToEntity(userRequestDto);
userRepository.save(users);
    }



//    /**
//     * @param username
//     * @param password
//     * @return
//     */
//    @Override
//    public boolean loginUSer(String username, String password) {
//log.info("Login User with user name and password {} {} ", username, password);
//        Optional<Users> checkUserCrediantials = userRepository.findByUsername(username);
//if(checkUserCrediantials.isPresent()){
//Users users = checkUserCrediantials.get();
//return users.getPassword().equals(password);
//}
//return false;
//    }


    // method with proper credential message

    /**
     * @param email
     * @param password
     * @return
     */
    @Override
    public String userLogin(String email, String password) {
        Optional<Users> checkCredentials= userRepository.findByEmail(email);

        if(checkCredentials.isEmpty()){
            return "❌ Username not found";
        }

        Users users1 = checkCredentials.get();

        if(!users1.getPassword().equals(password)){
            return "❌ Incorrect password";
        }

        return "✅ Login successful";
    }

    /**
     * @param email
     * @return
     */
    @Override
    public String passwordReset(String email) {
        log.info("Reset Pass of user  {} ", email);
        Optional<Users> users = userRepository.findByEmail(email);
         if(users.isPresent()){
        return "✅ Email sent to reset password to: " + email;
         }else{
             return "❌ No account found with this email";
         }

    }


//    With Real Time Link IN EmailBox
    /**
     * @param email
     * @return
     */
    @Override
    public String passwordResetNew(String email) {
        Optional<Users> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return "❌ No account found with this email";

        Users user = optionalUser.get();
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        String link = "http://localhost:5500/reset-password.html?token=" + token;
        System.out.println("🔗 Reset Link: " + link);
        return "✅ Use this reset link: " + link;
    }

    /**
     * @param token
     * @param newPassword
     * @return
     */
    @Override
    public String resetUserPassword(String token, String newPassword) {
        Optional<Users> optionalUser = userRepository.findByResetToken(token);
        if (optionalUser.isEmpty()) return "❌ Invalid or expired token";

        Users user = optionalUser.get();

        if (user.getTokenExpiry() == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return "❌ Token has expired";
        }

        user.setPassword(newPassword);
        user.setResetToken(null);
        user.setTokenExpiry(null);
        userRepository.save(user);

        return "✅ Password reset successfully";
    }

//





}
