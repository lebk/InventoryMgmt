package com.leikai.po;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class Producttype implements java.io.Serializable
{

  private Integer id;
  private String name;

  public Producttype()
  {
  }

  public Producttype(String name)
  {
    this.name = name;
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

}
