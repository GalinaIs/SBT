package com.sbt.dao.impl;

import java.util.List;

import com.sbt.model.UserInfo;

public interface UserInfoDAO {

    UserInfo findUserInfo(String userName);

    // [USER,AMIN,..]
    List<String> getUserRoles(String userName);

}

