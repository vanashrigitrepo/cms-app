package com.ibm.cms.dao;

import com.ibm.cms.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UserRepository extends JpaRepository <Users, Long>{
    Optional<Users> findByEmail(String email);
    Optional<Users> findByResetToken(String resetToken);


}
