package com.sbt.repository;

import com.sbt.model.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private static final Object lock = new Object();

    @Override
    public User findByUsername(String username) {
        User user = new User(username);
        synchronized (lock) {
            if (users.contains(user)) return null;

            users.add(user);
            return user;
        }
    }

    public static void getUsers() {
        synchronized (lock) {
            System.out.println(users);
        }
    }
}
