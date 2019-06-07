package com.sbt.repository;

import com.sbt.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("userRepository")
public class UserRepositoryImpl implements UserRepository {
    private static final List<User> users = Collections.synchronizedList(new ArrayList<User>());

    @Override
    public synchronized boolean checkUser(User user) {
        if (users.contains(user)) return false;

        users.add(user);
        return true;
    }

    @Override
    public synchronized void deleteUser(User user) {
        users.remove(user);
    }
}
