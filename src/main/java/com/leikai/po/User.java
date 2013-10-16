package com.leikai.po;


import java.util.Date;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class User implements java.io.Serializable
{

  private Integer id;
  private String name;
  private String password;
  private String email;
  private int type;
  private Date createTime;
  private Date lastLoginTime;
  private Boolean isValid;

  public User()
  {
  }

  public User(String name, String email, int type, Date createTime)
  {
    this.name = name;
    this.email = email;
    this.type = type;
    this.createTime = createTime;
  }

  public User(String name, String password, String email, int type, Date createTime, Date lastLoginTime, Boolean isValid)
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

  public int getType()
  {
    return this.type;
  }

  public void setType(int type)
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
