package com.databasemanager.DatabaseManager;

import com.databasemanager.DatabaseManager.dao.UserRepository;
import com.databasemanager.DatabaseManager.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired private UserRepository repo;


    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("jake@gmail.com");
        user.setPassword("jakePw");
        user.setFirstName("jake");
        user.setLastName("lucky");

        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();

        Assertions.assertThat(users).hasSizeGreaterThan(0);

        System.out.println(users);


    }

    @Test
    public void testUpdateUser(){
       int userId=1;
       Optional<User> optionalUser = repo.findById(userId);

       User user = optionalUser.get();
       user.setPassword("newPassword");

       repo.save(user);
       User updatedUser = repo.findById(userId).get();
       Assertions.assertThat(updatedUser.getPassword()).isEqualTo("macyPassword");

    }

    @Test
    public void testGetUser(){

        //retrieves user with id 2
        int userId=2;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();

        System.out.println(optionalUser.get());

    }

    @Test
    public void testDeleteUser(){

        int userId = 2;

        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();



    }


}
