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
import com.lebk.dao.PtColorDao;
import com.lebk.po.Product;
import com.lebk.po.Ptcolor;
import com.lebk.po.Pttype;
import com.lebk.util.HibernateUtil;

public class PtColorDaoImpl implements PtColorDao
{
  static Logger logger = Logger.getLogger(PtColorDaoImpl.class);

  public boolean addPtColor(String ptColorName, Integer opUserId)
  {
    if (ptColorName == null || ptColorName.length() == 0)
    {
      return false;
    }
    
    if (this.isPtColorExisted(ptColorName))
    {
      logger.warn("the color " + ptColorName + " is already existed");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      logger.info("begin to add product color: " + ptColorName);

      transaction = session.beginTransaction();
      Ptcolor pc = new Ptcolor();

      pc.setColor(ptColorName);
      pc.setOpUserId(opUserId);
      pc.setCreateTime(new Date());
      session.save(pc);
      transaction.commit();
      logger.info("add product color successfully");
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

  public boolean deletePtColor(String ptColor)
  {

    if (!this.isPtColorExisted(ptColor))
    {
      logger.error("the color: " + ptColor + " should be existed");
      return false;
    }

    Integer id = this.getIdByPtColorName(ptColor);
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Ptcolor pc = (Ptcolor) session.get(Ptcolor.class, id);
      session.delete(pc);
      transaction.commit();
      logger.info("delete color: " + ptColor + " successfully");
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    logger.error("fail to delete color: " + ptColor);

    return false;
  }

  public Integer getIdByPtColorName(String ptcolorName)
  {
    if (!this.isPtColorExisted(ptcolorName))
    {
      logger.error("The Ptcolor with name: " + ptcolorName + " should be existed");
      return null;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pcl = session.createQuery("from " + Ptcolor.class.getName() + " where color='" + ptcolorName + "'").list();

      if (pcl.size() != 1)
      {
        logger.error("There should be just one ptSizeName existed with name: " + ptcolorName + ", but now there are: " + pcl.size());
        return null;
      }
      for (Iterator iterator = pcl.iterator(); iterator.hasNext();)
      {
        Ptcolor pc = (Ptcolor) iterator.next();
        logger.info("color: " + ptcolorName + ", id is: " + pc.getId());

        return pc.getId();
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
    logger.error("fail to find the record for color name " + ptcolorName);
    return null;
  }

  public String getColorNameByPtColorId(Integer ptcolorId)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pcl = session.createQuery("from " + Ptcolor.class.getName() + " where id='" + ptcolorId + "'").list();

      if (pcl.size() != 1)
      {
        logger.error("There should be just one ptSizeName existed with id: " + ptcolorId + ", but now there are: " + pcl.size());
        return null;
      }
      for (Iterator iterator = pcl.iterator(); iterator.hasNext();)
      {
        Ptcolor pc = (Ptcolor) iterator.next();
        logger.info("color id: " + ptcolorId + ", the color is: " + pc.getColor());
        return pc.getColor();
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
    logger.error("fail to find the record for color id " + ptcolorId);
    return null;
  }

  public Ptcolor getPtColorByPtcolorId(Integer ptcolorId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pcl = session.createQuery("from " + Ptcolor.class.getName() + " where id='" + ptcolorId + "'").list();

      if (pcl.size() != 1)
      {
        logger.error("There should be just one ptSizeName existed with id: " + ptcolorId + ", but now there are: " + pcl.size());
        return null;
      }
      for (Iterator iterator = pcl.iterator(); iterator.hasNext();)
      {
        Ptcolor pc = (Ptcolor) iterator.next();
        logger.info("color id: " + ptcolorId + ", the color is: " + pc.getColor());
        return pc;
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
    logger.error("fail to find the record for color id " + ptcolorId);
    return null;

  }

  public boolean isPtColorExisted(String ptcolorName)
  {
    if (ptcolorName == null)
    {
      logger.error("The ptSizeName should not be null");
      return true;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pl = session.createQuery("from " + Ptcolor.class.getName() + " where color='" + ptcolorName + "'").list();

      if (pl.size() >= 1)
      {
        logger.info("The color " + ptcolorName + " is already existed");
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
    logger.info("The color " + ptcolorName + " is not existed");
    return false;
  }

  public List<Ptcolor> getAllPtColor()
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List pcl = new ArrayList<Ptcolor>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pcq = session.createQuery("from " + Ptcolor.class.getName()).list();
      for (Iterator it = pcq.iterator(); it.hasNext();)
      {
        Ptcolor bt = (Ptcolor) it.next();
        pcl.add(bt);
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
    return pcl;
  }

  public boolean isUsed(Integer ptColorId)
  {
    ProductDao pd = new ProductDaoImpl();
    List<Product> pl = pd.getProductList();
    for (Product p : pl)
    {
      if (ptColorId.equals(p.getPtColorId()))
      {
        logger.info("This product color id is already used by product:" + p.getName());
        return true;
      }
    }
    return false;
  }

}
