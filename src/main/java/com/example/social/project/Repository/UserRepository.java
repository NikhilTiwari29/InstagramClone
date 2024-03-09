package com.example.social.project.Repository;

import com.example.social.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE %:query% OR LOWER(u.lastName) LIKE %:query% OR LOWER(u.email) LIKE %:query%")
    List<User> searchUser(@Param("query") String query);


}
