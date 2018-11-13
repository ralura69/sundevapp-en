package jp.co.suntrust.service;

import jp.co.suntrust.bean.User;

public interface UserService {

	User findUserByID(String userID);
	User findUser(String userID, String password);
	void saveUser(User user);

}
