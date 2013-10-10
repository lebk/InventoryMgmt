package com.leikai.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.po.Os;
import com.leikai.util.HibernateUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public interface OsDao
{

  public List<Os> getOsList();

  public boolean removeOs(String osName);

  public boolean updateOs(String osName, String adminUser, String adminPassword);

  public boolean addOs(String name, String adminname, String adminpassword, Boolean isRBCS);

  public boolean isOsValid(String osName);

  public Integer getIdByOsName(String osName);

  public String getOsNameById(Integer id);

  public Os getOsbyOsId(Integer osId);

}
