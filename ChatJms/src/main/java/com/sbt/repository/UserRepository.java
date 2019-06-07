package com.sbt.repository;

import com.sbt.model.entity.User;

public interface UserRepository {
    User findByUsername(String username);
}
