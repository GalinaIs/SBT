package com.sbt.service;

import com.sbt.model.User;

public interface UserService {
	boolean checkUser(User user);

	void deleteUser(User user);
}
