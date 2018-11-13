package jp.co.suntrust.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.suntrust.bean.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {

	@Query("FROM User WHERE USER_ID = :userID")
	User findUserByID(@Param("userID") String userID);

	@Query("FROM User WHERE USER_ID = :userID AND PASSWROD = :password")
	User findUser(@Param("userID") String userID, @Param("password") String password);

}
