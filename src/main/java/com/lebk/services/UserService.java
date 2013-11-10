package com.lebk.services;

import java.util.List;

import com.lebk.po.User;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public interface UserService
{
  /**
   * @param name
   * @param password
   * @return
   * 
   *         this method is used to auth the user, if it authed, return true,
   *         otherwise, return false
   */
  public boolean authUser(String name, String password);

  /**
   * @param name
   * @param password
   * @param type
   *          (1 means admin user, 2 means regular user
   * @param email
   * @return
   * 
   *         This method is user to add new user (register a user), admin
   *         privilege required.
   */

  public boolean addUser(String name, String password, Integer type, String email, String opUser);

  /**
   * 
   * @param name
   * @param opUser
   *          (The user who will delete the other user
   * @return
   * 
   *         This method is used to delete the user, only admin user (opUser)
   *         can delete a user.
   */
  public boolean deleteUser(String name, String opUser);

  /**
   * 
   * @param name
   * @param type
   *          (The new level of user( 1 means admin, 2 means regular),
   * @param opUser
   *          (The user will will call updateUserType
   * @return
   * 
   * 
   *         only admin user can call this method
   */
  public boolean updateUserType(String name, Integer type, String opUser);

  /**
   * 
   * @param name
   * @return
   * 
   *         Check whether an user is valid or not, any user can call this
   *         method.
   */
  public boolean isUserValid(String name);

  /**
   * 
   * @param name
   * @return
   * 
   *         Check whether the specified user is admin or not. If yes, return
   *         ture, otherwise, return false
   */

  /**
   * 
   * @param email
   * @return
   * 
   *         check whether a email address is valid (the user is valid and the
   *         email address is existed)
   */
  public boolean isEmailValid(String email);

  /**
   * 
   * 
   * @param name
   * @return
   * 
   *         This method is used to check whether the queried user is admin or
   *         not
   */
  public boolean isUserAdmin(String name);

  /**
   * 
   * @param usreId
   * @return
   * 
   *         check whether the user is admin or not by userId
   */
  public boolean isUserAdmin(Integer userId);

  /**
   * 
   * @param userId
   * @return
   * 
   * 
   *         get the userId by username
   */
  public Integer getUserIdByUsername(String username);

  /**
   * 
   * @param opUser
   * @return
   * 
   *         Only valid user list will be returned
   */
  public List<User> getUserList(String opUser);

  /**
   * 
   * @param userId
   * @return
   * 
   */
  public User getUserByUserId(Integer userId);

}
