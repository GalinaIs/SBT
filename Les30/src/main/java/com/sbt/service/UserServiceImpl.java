package com.sbt.service;

import com.sbt.model.User;
import com.sbt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean checkUser(User user) {
        return userRepository.checkUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }
}
