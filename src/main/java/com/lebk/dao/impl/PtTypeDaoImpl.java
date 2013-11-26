package com.lebk.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lebk.dao.ProductDao;
import com.lebk.dao.PtTypeDao;
import com.lebk.po.Businesstype;
import com.lebk.po.Product;
import com.lebk.po.Pttype;
import com.lebk.po.Ptcolor;
import com.lebk.util.HibernateUtil;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class PtTypeDaoImpl implements PtTypeDao
{
  static Logger logger = Logger.getLogger(PtTypeDaoImpl.class);

  public boolean addPtType(String ptType, Integer opUserId)
  {
    if (this.isPtTypeExisted(ptType))
    {
      logger.warn("the product type " + ptType + " is already existed");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      logger.info("begin to add product type: " + ptType);

      transaction = session.beginTransaction();
      Pttype pt = new Pttype();

      pt.setType(ptType);
      pt.setOpUserId(opUserId);
      pt.setCreateTime(new Date());
      session.save(pt);
      transaction.commit();
      logger.info("add product type successfully");
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

  public boolean deletePtType(String ptType)
  {
    if (!this.isPtTypeExisted(ptType))
    {
      logger.error("the product type: " + ptType + " should be existed");
      return false;
    }

    Integer id = this.getIdByPtType(ptType);

    if (this.isUsed(id))
    {
      logger.info("The product type is already used, return false");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Pttype pt = (Pttype) session.get(Pttype.class, id);
      session.delete(pt);
      transaction.commit();
      logger.info("delete product type: " + ptType + " successfully");
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    logger.error("fail to delete product type: " + ptType);

    return false;
  }

  public List<Pttype> getAllPtType()
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List ptl = new ArrayList<Pttype>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List btq = session.createQuery("from " + Pttype.class.getName()).list();
      for (Iterator it = btq.iterator(); it.hasNext();)
      {
        Pttype bt = (Pttype) it.next();
        ptl.add(bt);
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
    return ptl;
  }

  public Integer getIdByPtType(String ptTypeName)
  {
    if (!this.isPtTypeExisted(ptTypeName))
    {
      logger.error("The pdType with name: " + ptTypeName + " should be existed");
      return null;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ptl = session.createQuery("from " + Pttype.class.getName() + " where type='" + ptTypeName + "'").list();

      if (ptl.size() != 1)
      {
        logger.error("There should be just one pttypeName existed with type name: " + ptTypeName + ", but now there are: " + ptl.size());
        return null;
      }
      for (Iterator iterator = ptl.iterator(); iterator.hasNext();)
      {
        Pttype pt = (Pttype) iterator.next();
        logger.info("product type name: " + ptTypeName + ", id is: " + pt.getId());

        return pt.getId();
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
    logger.error("fail to find the record for product type name " + ptTypeName);
    return null;
  }

  public String getNameByPtTypeId(Integer ptTypeId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ptl = session.createQuery("from " + Pttype.class.getName() + " where id='" + ptTypeId + "'").list();

      if (ptl.size() != 1)
      {
        logger.error("There should be just one product type Name existed with id: " + ptTypeId + ", but now there are: " + ptl.size());
        return null;
      }
      for (Iterator iterator = ptl.iterator(); iterator.hasNext();)
      {
        Pttype pt = (Pttype) iterator.next();
        logger.info("product type id: " + ptTypeId + ", the type name is: " + pt.getType());
        return pt.getType();
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
    logger.error("fail to find the record for product type id " + ptTypeId);
    return null;
  }

  public Pttype getPtTypeByPtTypeId(Integer ptTypeId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ptl = session.createQuery("from " + Pttype.class.getName() + " where id='" + ptTypeId + "'").list();

      if (ptl.size() != 1)
      {
        logger.error("There should be just one product type existed with id: " + ptTypeId + ", but now there are: " + ptl.size());
        return null;
      }
      for (Iterator iterator = ptl.iterator(); iterator.hasNext();)
      {
        Pttype pt = (Pttype) iterator.next();
        logger.info("product type id: " + ptTypeId + ", the type name is: " + pt.getType());
        return pt;
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
    logger.error("fail to find the record for product type id " + ptTypeId);
    return null;
  }

  public boolean isPtTypeExisted(String ptTypeName)
  {
    if (ptTypeName == null)
    {
      logger.error("The ptTypeName should not be null");
      return true;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pl = session.createQuery("from " + Pttype.class.getName() + " where type='" + ptTypeName + "'").list();

      if (pl.size() >= 1)
      {
        logger.info("The name " + ptTypeName + " is already existed");
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
    logger.info("The product type name " + ptTypeName + " is not existed");
    return false;
  }

  public boolean isUsed(Integer ptTypeId)
  {
    ProductDao pd = new ProductDaoImpl();
    List<Product> pl = pd.getProductList();
    for (Product p : pl)
    {
      if (ptTypeId.equals(p.getPtTypeId()))
      {
        logger.info("This product type id is already used by product:" + p.getName());
        return true;
      }
    }
    return false;
  }
}
