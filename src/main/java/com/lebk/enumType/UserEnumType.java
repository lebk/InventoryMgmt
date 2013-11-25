package com.lebk.enumType;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lebk.po.Usertype;
import com.lebk.services.UserService;
import com.lebk.services.impl.UserServiceImpl;
import com.lebk.util.HibernateUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class UserEnumType
{
  final public static String admin = "管理员";
  final public static String regular = "普通用户";
  final public static String unknown = "未知类型";
  static Logger logger = Logger.getLogger(UserEnumType.class);
  private static UserService us = new UserServiceImpl();

  public static Integer getUserTypeId(String userType)
  {
    if (userType.equalsIgnoreCase(admin) || userType.equalsIgnoreCase(regular))
    {

      Session session = HibernateUtil.getSessionFactory().openSession();

      Transaction transaction = null;

      try
      {
        transaction = session.beginTransaction();
        List ul = session.createQuery("from Usertype where name='" + userType + "'").list();

        if (ul.size() != 1)
        {
          logger.error("There should be just one userType existed with name: " + userType + ", but now there are: " + ul.size());
          return null;
        }
        for (Iterator iterator = ul.iterator(); iterator.hasNext();)
        {
          Usertype u = (Usertype) iterator.next();
          logger.info("UserType: " + userType + ", id is: " + u.getId());

          return u.getId();
        }
        transaction.commit();
      } catch (HibernateException e)
      {
        transaction.rollback();
        e.printStackTrace();
      } finally
      {
        session.close();
      }
      return null;
    } else
    {
      logger.error("the queried user type does not existed");
      return null;
    }
  }

  public static String getUsertypeById(Integer id)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from Usertype where id='" + id + "'").list();

      if (ul.size() != 1)
      {
        logger.error("There should be just one userType existed with id: " + id + ", but now there are: " + ul.size());
        return unknown;
      }
      for (Iterator iterator = ul.iterator(); iterator.hasNext();)
      {
        Usertype ut = (Usertype) iterator.next();
        logger.info("usertype id: " + id + ", the type is: " + ut.getName());

        if (ut.getName().equalsIgnoreCase(admin))
        {
          return admin;
        } else if (ut.getName().equalsIgnoreCase(regular))
        {
          return regular;
        } else
        {
          return unknown;
        }
      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    logger.error("the queried user type does not existed");
    return unknown;
  }

}
