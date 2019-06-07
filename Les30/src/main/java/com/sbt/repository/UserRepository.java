package com.sbt.repository;

import com.sbt.model.User;

public interface UserRepository {

	boolean checkUser(User user);

	void deleteUser(User user);
}
