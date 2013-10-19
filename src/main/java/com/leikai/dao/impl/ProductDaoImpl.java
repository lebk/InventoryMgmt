package com.leikai.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.ProductDao;
import com.leikai.po.Product;
import com.leikai.po.Producttype;
import com.leikai.util.HibernateUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

public class ProductDaoImpl implements ProductDao
{
  static Logger logger = Logger.getLogger(ProductDaoImpl.class);

  public List<Product> getProductList()
  {
    List pl = new ArrayList<Product>();

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Product").list();
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Product p = (Product) it.next();
        logger.info("Product:" + p.getName());
        pl.add(p);
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
    return pl;

  }

  public boolean addProduct(String pName, String version, String key, String baseLocation, String prodType, List<String> supportedOsList, String uploadUser)
  {

    Integer ptId = this.getprodTypeIdByType(prodType);
    List<String> osIdList = null;
    if (ptId == null)
    {
      logger.error("The product type: " + prodType + " is not existed");
      return false;
    }



    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      logger.info("begin to add product: " + pName);

      transaction = session.beginTransaction();
      Product p = new Product();

      p.setName(pName);

      // Remove the FileSeprator as, the baesLocation already contain a "/"
      session.save(p);
      transaction.commit();
      logger.info("add product successfully");
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




  public Integer getIdByProdName(String pName)
  {
    {
      if (pName == null)
      {
        logger.error("The pName should not be null");
        return null;
      }

      Session session = HibernateUtil.getSessionFactory().openSession();

      Transaction transaction = null;

      try
      {
        transaction = session.beginTransaction();
        List pl = session.createQuery("from Product where name='" + pName + "'").list();
        if (pl.size() == 0)
        {
          logger.warn("no product found by the name: " + pName);
          return null;
        }
        if (pl.size() > 1)
        {
          logger.error("more than one product found by the name: " + pName);
          return null;
        }
        for (Iterator it = pl.iterator(); it.hasNext();)
        {
          Product p = (Product) it.next();
          return p.getId();
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
    }
  }





  private String convertStringListToString(List<String> osList)
  {
    String osString = "";
    if (osList == null || osList.size() == 0)
    {
      return osString;
    }

    for (String os : osList)
    {

      logger.info("os:" + os);
      osString += ", " + os;
    }
    logger.info("osString:" + osString);
    return osString.substring(2);
  }

  private boolean isContianOsId(String supportedOslist, Integer osId)
  {

    if (supportedOslist == null || supportedOslist == "")
    {
      return false;
    } else
    {
      String[] osIds = supportedOslist.split(", ");
      for (int i = 0; i < osIds.length; i++)
      {
        logger.info("osId is: " + osIds[i]);
        if (osIds[i].equals(String.valueOf(osId)))
        {
          return true;
        }
      }
    }
    return false;
  }

  private Integer getprodTypeIdByType(String prodType)
  {

    if (!this.isProdTypeExisted(prodType))
    {
      logger.error("The name should be existed");
      return null;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ptl = session.createQuery("from Producttype where name='" + prodType + "'").list();
      if (ptl.size() != 1)
      {
        logger.error("No prodtype existed with the name: " + prodType);
        return null;
      }
      for (Iterator it = ptl.iterator(); it.hasNext();)
      {
        Producttype pt = (Producttype) it.next();
        logger.info("ProductType: " + prodType + ", id is: " + pt.getId());

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
    return null;
  }

  public String getProdTypebyProdTypeId(Integer prodTypeId)
  {

    if (prodTypeId == null)
    {
      return null;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ptl = session.createQuery("from Producttype where id='" + prodTypeId + "'").list();
      if (ptl.size() != 1)
      {
        logger.error("No prodtype existed with the id: " + prodTypeId);
        return null;
      }
      for (Iterator it = ptl.iterator(); it.hasNext();)
      {
        Producttype pt = (Producttype) it.next();
        logger.info("ProductType: " + prodTypeId + ", name is: " + pt.getName());

        return pt.getName();
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
  }

  private boolean isProdTypeExisted(String prodType)
  {
    if (prodType == null)
    {
      logger.error("The prodtype should not be null");
      return false;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ptl = session.createQuery("from Producttype where name='" + prodType + "'").list();

      if (ptl.size() == 1)
      {
        logger.info("The prodtype " + prodType + "exist");
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

    return false;
  }




  public String getNameByProductId(Integer poId)
  {
    {
      if (poId == null)
      {
        logger.error("The poId should not be null");
        return null;
      }

      Session session = HibernateUtil.getSessionFactory().openSession();

      Transaction transaction = null;

      try
      {
        transaction = session.beginTransaction();
        List pl = session.createQuery("from Product where id='" + poId + "'").list();

        if (pl.size() > 1)
        {
          logger.error("There should be just one product existed with product id: " + poId + ", but now there is: " + pl.size());

          return null;
        }
        for (Iterator it = pl.iterator(); it.hasNext();)
        {
          Product p = (Product) it.next();
          return p.getName();
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
    }
  }

  public Product getProductByPoId(Integer poId)
  {
    {
      if (poId == null)
      {
        logger.error("The poId should not be null");
        return null;
      }

      Session session = HibernateUtil.getSessionFactory().openSession();

      Transaction transaction = null;

      try
      {
        transaction = session.beginTransaction();
        List pl = session.createQuery("from Product where id='" + poId + "'").list();

        if (pl.size() > 1)
        {
          logger.error("There should be just one product existed with product id: " + poId + ", but now there is: " + pl.size());

          return null;
        }
        for (Iterator it = pl.iterator(); it.hasNext();)
        {
          Product p = (Product) it.next();
          return p;
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
    }
  }

  public boolean updateProductName(String oldName, String newName)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List pl = session.createQuery("from Product where name='" + oldName + "'").list();
      if (pl.size() > 1)
      {
        logger.error("There should be just one product existed with product name: " + oldName + ", but now there is: " + pl.size());

        return false;
      }
      for (Iterator iterator = pl.iterator(); iterator.hasNext();)
      {

        Product pro = (Product) iterator.next();

        pro.setName(newName);
        String hql = "update Product set name='" + newName + "'where name='" + oldName + "'";
        session.createQuery(hql);
      }
      transaction.commit();
      return true;
    } catch (Exception e)
    {
      System.out.println("can not update the name of the product");
      e.printStackTrace();
      return false;
    } finally
    {
      session.close();
    }

  }

  public Product getProductByProdTypeAndVersion(String ptType, String version)
  {
    // TODO Auto-generated method stub
    return null;
  }

  public List<Producttype> getSupportedProductType()
  {
    List ptl = new ArrayList<Product>();

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Producttype").list();
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Producttype pt = (Producttype) it.next();
        logger.info("Producttye:" + pt.getName());
        ptl.add(pt);
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



  public boolean isProductExisted(String pName)
  {
    {
      if (pName == null)
      {
        logger.error("The pName should not be null");
        return true;
      }

      Session session = HibernateUtil.getSessionFactory().openSession();

      Transaction transaction = null;

      try
      {
        transaction = session.beginTransaction();
        List pl = session.createQuery("from Product where name='" + pName + "'").list();

        if (pl.size() == 1)
        {
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

      return false;
    }
  }

  public boolean isProductExisted(String prodType, String version)
  {
    {
      if (prodType == null || version == null)
      {
        logger.error("The prodType:" + prodType + " or version:" + version + " is null, return false");

        return true;
      }
      Integer ptId = getprodTypeIdByType(prodType);
      Session session = HibernateUtil.getSessionFactory().openSession();

      Transaction transaction = null;

      try
      {
        transaction = session.beginTransaction();
        List pl = session.createQuery("from Product where ptId='" + ptId + "' and pVersion='" + version + "'").list();

        if (pl.size() == 1)
        {
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

      return false;
    }
  }
}
