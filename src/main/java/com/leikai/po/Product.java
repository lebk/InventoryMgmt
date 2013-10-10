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
public class Product implements java.io.Serializable
{

  private Integer id;
  private String name;
  private String pversion;
  private String pkey;
  private int ptId;
  private String description;
  private String location;
  private String supportedOslist;
  private Date addTime;
  private String uploadUser;
  private Boolean isValid;

  public Product()
  {
  }

  public Product(String name, int ptId, String location, String uploadUser)
  {
    this.name = name;
    this.ptId = ptId;
    this.location = location;
    this.uploadUser = uploadUser;
  }

  public Product(String name, String pversion, String pkey, int ptId, String description, String location, String supportedOslist, Date addTime,
      String uploadUser, Boolean isValid)
  {
    this.name = name;
    this.pversion = pversion;
    this.pkey = pkey;
    this.ptId = ptId;
    this.description = description;
    this.location = location;
    this.supportedOslist = supportedOslist;
    this.addTime = addTime;
    this.uploadUser = uploadUser;
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

  public String getPversion()
  {
    return this.pversion;
  }

  public void setPversion(String pversion)
  {
    this.pversion = pversion;
  }

  public String getPkey()
  {
    return this.pkey;
  }

  public void setPkey(String pkey)
  {
    this.pkey = pkey;
  }

  public int getPtId()
  {
    return this.ptId;
  }

  public void setPtId(int ptId)
  {
    this.ptId = ptId;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getLocation()
  {
    return this.location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public String getSupportedOslist()
  {
    return this.supportedOslist;
  }

  public void setSupportedOslist(String supportedOslist)
  {
    this.supportedOslist = supportedOslist;
  }

  public Date getAddTime()
  {
    return this.addTime;
  }

  public void setAddTime(Date addTime)
  {
    this.addTime = addTime;
  }

  public String getUploadUser()
  {
    return this.uploadUser;
  }

  public void setUploadUser(String uploadUser)
  {
    this.uploadUser = uploadUser;
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
