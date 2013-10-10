package com.leikai.po;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class Location implements java.io.Serializable
{

  private Integer id;
  private String type;
  private String url;

  public Location()
  {
  }

  public Location(String type, String url)
  {
    this.type = type;
    this.url = url;
  }

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getUrl()
  {
    return this.url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

}
