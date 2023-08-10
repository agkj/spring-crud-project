package com.databasemanager.DatabaseManager.dao;


import com.databasemanager.DatabaseManager.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

    //id type is Integer as generated value in

    public long countById(Integer id);


}
