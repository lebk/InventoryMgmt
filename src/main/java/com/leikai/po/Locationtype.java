package com.leikai.po;


/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class Locationtype implements java.io.Serializable
{

  private Integer id;
  private String name;
  private String description;

  public Locationtype()
  {
  }

  public Locationtype(String name, String description)
  {
    this.name = name;
    this.description = description;
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

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

}
