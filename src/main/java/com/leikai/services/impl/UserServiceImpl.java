package com.leikai.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.leikai.dao.UserDao;
import com.leikai.dao.impl.UserDaoImpl;
import com.leikai.enumType.UserEnumType;
import com.leikai.po.User;
import com.leikai.services.UserService;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 * 
 * 2013-10-9
 * 
 */
public class UserServiceImpl implements UserService {
	static Logger logger = Logger.getLogger(UserServiceImpl.class);

	private UserDao ud = new UserDaoImpl();

	public boolean authUser(String name, String password) {
		// For a user which will be authenticated, we will first check whether
		// it
		// exists in our db, if yes, we
		// will forward to Etrack to auth, if it authenticate by Etrack system,
		// we
		// will allow it to login. Otherwise
		// We will auth it locally, if it authenticated, then we will let it log
		// in.
		if (name == null || name.equals("")) {
			logger.info("the user is null or empty!");
			return false;
		}
		if (this.isUserValid(name) == false) {
			logger.info("the auth user is not valid!");
			return false;
		}

		logger.info("Authenticate by local system....");
		if (ud.authUser(name, password) == true) {
			logger.info("Authenticated by local system!");
			return true;

		} else {
			return false;
		}
	}

	public boolean addUser(String name, String password, Integer type,
			String email, String opUser) {
		if (!this.isUserAdmin(opUser)) {
			logger.error("only admin user can update a user's level");
			return false;
		}
		if (name == null || name.equals("") || email == null
				|| email.equals("")) {
			logger.error("name or email address should not be null or empty!");
			return false;
		}
		// if (password == null || password.equals(""))
		// {
		// logger.error("password should not be null or empty!");
		// return false;
		// }
		String ut = UserEnumType.getUsertypeById(type);
		if (!ut.equals(UserEnumType.admin) && !ut.equals(UserEnumType.regular))

		{
			logger.error("the user type is not recognized");
			return false;
		}

		return ud.addUser(name, password, type, email);
	}

	public boolean deleteUser(String name, String opUser) {
		if (!this.isUserAdmin(opUser)) {
			logger.error("only admin user can delete a user");
			return false;
		}
		return ud.deleteUser(name);
	}

	public boolean updateUserType(String name, Integer type, String opUser) {
		if (!this.isUserAdmin(opUser)) {
			logger.error("only admin user can update a user's level");
			return false;
		}
		return ud.updateUserType(name, type);
	}

	public boolean isUserValid(String name) {
		if (name == null || name.equals("")) {
			logger.error("the queried user is null or empty");
			return false;
		}
		return ud.isUserValid(name);
	}

	public boolean isEmailValid(String uEmail) {
		if (uEmail == null || uEmail.equals("")) {
			logger.error("the queried email should not be null or empty");
			return false;
		}
		return ud.isEmailValid(uEmail);
	}

	public boolean isUserAdmin(String name) {
		return ud.isUserAdmin(name);
	}

	public boolean isUserAdmin(Integer userId) {
		return ud.isUserAdmin(userId);
	}

	public Integer getUserIdByUsername(String user) {
		return ud.getUserIdByUsername(user);
	}

	public List<User> getUserList(String opUser) {
		List<User> retUL = new ArrayList<User>();
		if (!this.isUserAdmin(opUser)) {
			logger.error("only admin user can get the user list");
			return null;
		}
		List<User> ul = ud.getUserList();
		// not allow user to update admin/regular in the web gui,
		for (User u : ul) {
			if (!(u.getName().equals("admin") || u.getName().equals("regular"))) {
				retUL.add(u);
			}
		}

		return retUL;
	}

	public User getUserByUserId(Integer userId) {
		User u = ud.getUserByUserId(userId);
		if (u == null) {
			logger.error("No user found by the userId: " + userId);
			return null;
		}
		return u;
	}
}
