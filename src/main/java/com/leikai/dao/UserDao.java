package com.leikai.dao;

import java.util.List;

import com.leikai.po.User;

public interface UserDao
{

  public boolean addUser(String name, String password, Integer type, String email);

  public boolean deleteUser(String name);

  public boolean updateUserType(String name, Integer type);

  public boolean authUser(String name, String password);

  public boolean isUserValid(String name);

  public boolean isEmailValid(String uEmail);

  public boolean isUserAdmin(String name);

  public boolean isUserAdmin(Integer userId);

  public Integer getUserIdByUsername(String user);

  public String getUsernamebyUserid(Integer userId);

  public List<User> getUserList();

  public User getUserByUserId(Integer userId);
}
