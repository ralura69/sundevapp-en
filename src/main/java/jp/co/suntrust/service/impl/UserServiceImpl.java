package jp.co.suntrust.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.suntrust.bean.User;
import jp.co.suntrust.repository.UserRepository;
import jp.co.suntrust.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUserByID(String userID) {
		return userRepository.findUserByID(userID);
	}

	@Override
	public User findUser(String userID, String password) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

}
