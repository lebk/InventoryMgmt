package com.lebk.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lebk.dao.BusinessTypeDao;
import com.lebk.po.Businesstype;
import com.lebk.po.Ptsize;
import com.lebk.po.User;
import com.lebk.util.HibernateUtil;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class BusinessTypeDaoImpl implements BusinessTypeDao
{
  static Logger logger = Logger.getLogger(BusinessTypeDaoImpl.class);

  public List<Businesstype> getAllBusinessType()
  {

    Session session = HibernateUtil.getSessionFactory().openSession();
    List bl = new ArrayList<Businesstype>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List btq = session.createQuery("from " + Businesstype.class.getName()).list();
      for (Iterator it = btq.iterator(); it.hasNext();)
      {
        Businesstype bt = (Businesstype) it.next();
        bl.add(bt);
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
    return bl;
  }

  public Integer getIdByType(String type)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List psl = session.createQuery("from " + Businesstype.class.getName() + " where type='" + type + "'").list();

      if (psl.size() != 1)
      {
        logger.error("There should be just one type existed with type name: " + type + ", but now there are: " + psl.size());
        return null;
      }
      for (Iterator iterator = psl.iterator(); iterator.hasNext();)
      {
        Businesstype bs = (Businesstype) iterator.next();
        logger.info("type: " + type + ", id is: " + bs.getId());

        return bs.getId();
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
    logger.error("fail to find the record for bussiness with type name: " + type);
    return null;
  }

  public String getTypeById(Integer id)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List psl = session.createQuery("from " + Businesstype.class.getName() + " where id='" + id + "'").list();

      if (psl.size() != 1)
      {
        logger.error("There should be just one type existed with type id: " + id + ", but now there are: " + psl.size());
        return null;
      }
      for (Iterator iterator = psl.iterator(); iterator.hasNext();)
      {
        Businesstype bs = (Businesstype) iterator.next();
        logger.info("id: " + id + ", type is: " + bs.getType());

        return bs.getType();
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
    logger.error("fail to find the record for bussiness with type id: " + id);
    return null;
  }

}
