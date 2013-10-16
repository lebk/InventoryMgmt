package com.leikai.dto;

import java.util.Date;

import com.leikai.enumType.UserEnumType;
import com.leikai.po.User;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class UserDTO implements java.io.Serializable
{

  private Integer id;
  private String name;
  private String password;
  private String email;
  private String type;
  private Date createTime;
  private Date lastLoginTime;
  private Boolean isValid;

  public UserDTO()
  {
  }

  public UserDTO(User u)
  {
    this.id = u.getId();
    this.name = u.getName();
    this.password = u.getPassword();
    this.email = u.getEmail();
    this.type = UserEnumType.getUsertypeById(u.getType());
    this.createTime = u.getCreateTime();
    this.lastLoginTime = u.getLastLoginTime();
    this.isValid = u.getIsValid();
  }

  public UserDTO(String name, String email, String type, Date createTime)
  {
    this.name = name;
    this.email = email;
    this.type = type;
    this.createTime = createTime;
  }

  public UserDTO(String name, String password, String email, String type, Date createTime, Date lastLoginTime, Boolean isValid)
  {
    this.name = name;
    this.password = password;
    this.email = email;
    this.type = type;
    this.createTime = createTime;
    this.lastLoginTime = lastLoginTime;
    this.isValid = isValid;
  }

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getLastLoginTime()
  {
    return this.lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime)
  {
    this.lastLoginTime = lastLoginTime;
  }

  public Boolean getIsValid()
  {
    return this.isValid;
  }

  public void setIsValid(Boolean isValid)
  {
    this.isValid = isValid;
  }

}
