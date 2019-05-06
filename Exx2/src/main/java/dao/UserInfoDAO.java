package dao;

import java.util.List;

import model.UserInfo;

public interface UserInfoDAO {

    public UserInfo findUserInfo(String userName);

    // [USER,AMIN,..]
    public List<String> getUserRoles(String userName);

}