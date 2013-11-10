package com.lebk.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

public class HibernateUtil
{

  private static final SessionFactory sessionFactory;
  private static Logger logger = Logger.getLogger(HibernateUtil.class);

  static
  {

    try
    {

      sessionFactory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex)
    {

      logger.error("Initial SessionFactory creation failed." + ex);

      throw new ExceptionInInitializerError(ex);

    }

  }

  public static SessionFactory getSessionFactory()
  {

    return sessionFactory;

  }

}