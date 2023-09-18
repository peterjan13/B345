package com.zuitt.wdc044.services;

import com.zuitt.wdc044.models.User;
import com.zuitt.wdc044.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
//to indicate that we are going to implement a service we need to use Service annotation from spring framework.
@Service
public class UserServiceImpl implements UserService{

    //Dependency injection
    //@Autowired means an instance of a class that implements
    @Autowired
    private UserRepository userRepository;

    //We are going to define the business logic behind the createUser method from the UserService Interface.

    public void createUser(User user){
        //Save the user in our table:
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        //Null can be returned as a value if the user record cannot be found
        return Optional.ofNullable(userRepository.findByUsername(username));
    }
}
