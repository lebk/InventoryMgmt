package com.lebk.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lebk.dao.PtSizeDao;
import com.lebk.po.Ptcolor;
import com.lebk.po.Ptsize;
import com.lebk.po.Ptsize;
import com.lebk.util.HibernateUtil;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-24
 */

public class PtSizeDaoImpl implements PtSizeDao
{
  static Logger logger = Logger.getLogger(PtSizeDaoImpl.class);

  public boolean addPtSize(String ptSizeName)
  {
    if (this.isPtSizeExisted(ptSizeName))
    {
      logger.warn("the size " + ptSizeName + " is already existed");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      logger.info("begin to add product size: " + ptSizeName);

      transaction = session.beginTransaction();
      Ptsize ps = new Ptsize();

      ps.setSize(ptSizeName);
      session.save(ps);
      transaction.commit();
      logger.info("add product size successfully");
      return true;

    } catch (HibernateException e)
    {

      transaction.rollback();
      logger.error(e.toString());
      e.printStackTrace();

    } finally
    {

      session.close();

    }
    return false;
  }

  public boolean deletePtSize(String ptSizeName)
  {
    if (!this.isPtSizeExisted(ptSizeName))
    {
      logger.error("the size: " + ptSizeName + " should be existed");
      return false;
    }

    Integer id = this.getIdByPtSizeName(ptSizeName);
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Ptsize ps = (Ptsize) session.get(Ptsize.class, id);
      session.delete(ps);
      transaction.commit();
      logger.info("delete size: " + ptSizeName + " successfully");
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    logger.error("fail to delete size: " + ptSizeName);

    return false;
  }

  public Integer getIdByPtSizeName(String ptSizeName)
  {
    if (!this.isPtSizeExisted(ptSizeName))
    {
      logger.error("The Ptsize with size: " + ptSizeName + " should be existed");
      return null;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List psl = session.createQuery("from " + Ptsize.class.getName() + " where size='" + ptSizeName + "'").list();

      if (psl.size() != 1)
      {
        logger.error("There should be just one ptSizeName existed with name: " + ptSizeName + ", but now there are: " + psl.size());
        return null;
      }
      for (Iterator iterator = psl.iterator(); iterator.hasNext();)
      {
        Ptsize ps = (Ptsize) iterator.next();
        logger.info("color: " + ptSizeName + ", id is: " + ps.getId());

        return ps.getId();
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
    logger.error("fail to find the record for size with name: " + ptSizeName);
    return null;
  }

  public String getNameByPtSizeId(Integer ptSizeId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pcl = session.createQuery("from " + Ptsize.class.getName() + " where id='" + ptSizeId + "'").list();

      if (pcl.size() != 1)
      {
        logger.error("There should be just one ptSizeName existed with id: " + ptSizeId + ", but now there are: " + pcl.size());
        return null;
      }
      for (Iterator iterator = pcl.iterator(); iterator.hasNext();)
      {
        Ptsize ps = (Ptsize) iterator.next();
        logger.info("size id: " + ptSizeId + ", the size is: " + ps.getSize());
        return ps.getSize();
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
    logger.error("fail to find the record for size id " + ptSizeId);
    return null;
  }

  public Ptsize getPtSizeByPtsizeId(Integer ptSizeId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pcl = session.createQuery("from " + Ptsize.class.getName() + " where id='" + ptSizeId + "'").list();

      if (pcl.size() != 1)
      {
        logger.error("There should be just one ptSizeName existed with id: " + ptSizeId + ", but now there are: " + pcl.size());
        return null;
      }
      for (Iterator iterator = pcl.iterator(); iterator.hasNext();)
      {
        Ptsize ps = (Ptsize) iterator.next();
        logger.info("size id: " + ptSizeId + ", the size is: " + ps.getSize());
        return ps;
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
    logger.error("fail to find the record for size id " + ptSizeId);
    return null;
  }

  public boolean isPtSizeExisted(String ptSizeName)
  {
    if (ptSizeName == null)
    {
      logger.error("The ptSizeName should not be null");
      return true;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pl = session.createQuery("from " + Ptsize.class.getName() + " where size='" + ptSizeName + "'").list();

      if (pl.size() >= 1)
      {
        logger.info("The size " + ptSizeName + " is already existed");
        return true;
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
    logger.info("The size " + ptSizeName + " is not existed");
    return false;
  }

}
