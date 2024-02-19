package com.localtide.billsync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localtide.billsync.entity.User;
import com.localtide.billsync.entity.User.USER_STATUS;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUserNameIgnoreCase(String userName);

	public User findByUserNameIgnoreCaseAndEmailIgnoreCase(String userName, String email);

	public User findByResetPasswordToken(String token);
//
	public User findByEmailIgnoreCase(String email);
	
	public User findByBillerIdAndStatus(String billerId, USER_STATUS status);
}
