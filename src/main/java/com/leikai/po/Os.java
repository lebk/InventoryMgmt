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
public class Os implements java.io.Serializable
{

  private Integer id;
  private String name;
  private String adminname;
  private String adminpassword;
  private Date addTime;
  private String location;
  private Boolean isValid;
  private Boolean isRBCS;

  public Os()
  {
  }

  public Os(String name)
  {
    this.name = name;
  }

  public Os(String name, String adminname, String adminpassword, Date addTime, String location, Boolean isValid, Boolean isRBCS)
  {
    this.name = name;
    this.adminname = adminname;
    this.adminpassword = adminpassword;
    this.addTime = addTime;
    this.location = location;
    this.isValid = isValid;
    this.isRBCS = isRBCS;
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

  public String getAdminname()
  {
    return this.adminname;
  }

  public void setAdminname(String adminname)
  {
    this.adminname = adminname;
  }

  public String getAdminpassword()
  {
    return this.adminpassword;
  }

  public void setAdminpassword(String adminpassword)
  {
    this.adminpassword = adminpassword;
  }

  public Date getAddTime()
  {
    return this.addTime;
  }

  public void setAddTime(Date addTime)
  {
    this.addTime = addTime;
  }

  public String getLocation()
  {
    return this.location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public Boolean getIsValid()
  {
    return this.isValid;
  }

  public void setIsValid(Boolean isValid)
  {
    this.isValid = isValid;
  }

  public Boolean getIsRBCS()
  {
    return this.isRBCS;
  }

  public void setIsRBCS(Boolean isRBCS)
  {
    this.isRBCS = isRBCS;
  }
}
