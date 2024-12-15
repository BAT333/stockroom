package com.github.bat333.stockroom.repository;

import com.github.bat333.stockroom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    UserDetails findByEmail(String username);
}
