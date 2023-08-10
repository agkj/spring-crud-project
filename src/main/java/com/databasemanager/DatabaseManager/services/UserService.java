package com.databasemanager.DatabaseManager.services;


import com.databasemanager.DatabaseManager.dao.UserRepository;
import com.databasemanager.DatabaseManager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> listAllUser(){
        return  (List<User>) userRepository.findAll();
    }


    public void save(User user) { userRepository.save(user);
    }

    public User editUser(Integer id) throws UserNotFoundException {


        Optional<User> userPresent = userRepository.findById(id); //check if user exists
        if(userPresent.isPresent()) {
            return userPresent.get();
        }
        throw new UserNotFoundException("User could not be found");

    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long idCount = userRepository.countById(id);

        if(idCount == null || idCount==0){
            throw new UserNotFoundException("User could not be found");
        }

        userRepository.deleteById(id);

    }




}
