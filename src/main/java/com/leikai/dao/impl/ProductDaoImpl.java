package com.leikai.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.ProductDao;
import com.leikai.dao.PtColorDao;
import com.leikai.dao.PtDetailsDao;
import com.leikai.dao.PtSizeDao;
import com.leikai.dao.PtTypeDao;
import com.leikai.po.Product;
import com.leikai.po.Ptdetails;
import com.leikai.po.Pttype;
import com.leikai.po.Product;
import com.leikai.po.User;
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

  PtDetailsDao pdd = new PtDetailsDaoImpl();

  public List<Product> getProductList()
  {
    List pl = new ArrayList<Product>();

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from " + Product.class.getName()).list();
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

  private boolean addPtDetails(Integer poId, Integer btId, Integer pNum, Integer opUserId)
  {
    PtDetailsDao pdd = new PtDetailsDaoImpl();
    pdd.addPtDetail(poId, btId, pNum, opUserId);
    return false;
  }

  public boolean addProduct(String pName, Integer ptTypeId, Integer ptColorId, Integer ptSizeId, Integer pNum)
  {

    if (this.isProductExisted(pName))
    {
      logger.info("as the product is already exited, increase the nubmer");
      Integer poId = this.getIdByProdName(pName);
      Integer pn = this.getProductByPoId(poId).getPtNumber();
      Boolean status = updateProductNumber(pName, pn + pNum);
      // status=this.addPtDetails(poId, btId, pNum, opUserId);
      return status;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      logger.info("begin to add product: " + pName);

      transaction = session.beginTransaction();
      Product p = new Product();

      p.setName(pName);
      p.setPtColorId(ptColorId);
      p.setPtTypeId(ptTypeId);
      p.setPtSizeId(ptSizeId);
      p.setPtNumber(pNum);
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

  public boolean removeProduct(Integer poId)
  {
    logger.info("Remove poduduct with id:" + poId);
    Boolean status = pdd.deletePtDetialByPoId(poId);
    if (status == false)
    {
      logger.error("Fail to remove the ptdetails records!");
      return false;
    }
    status = this.deleteProductRecord(poId);
    if (status == false)
    {
      logger.error("Fail to remove the product record!");
      return false;
    }
    return true;
  }

  private boolean deleteProductRecord(Integer id)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();

      Product ps = (Product) session.get(Product.class, id);
      session.delete(ps);

      transaction.commit();
      logger.info("delete product by id: " + id + " successfully");
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    logger.error("fail to product  with id: " + id);

    return false;
  }

  private boolean updateProductNumber(Integer poId, Integer ptNumber)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Product u = (Product) session.get(Product.class, poId);
      u.setPtNumber(ptNumber);
      transaction.commit();
      logger.info("Successfully update the product number for po id:" + poId);
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    logger.info("Fail to update the product number for po id:" + poId);
    return false;
  }

  private boolean updateProductNumber(String pName, Integer ptNumber)
  {
    Integer poId = this.getIdByProdName(pName);
    if (poId == null)
    {
      logger.error("Product does not existed with the name:" + pName);
      return false;
    }

    return this.updateProductNumber(poId, ptNumber);
  }

  public boolean reduceProduct(Integer poId, Integer pNum)
  {
    Integer pn = this.getProductByPoId(poId).getPtNumber();

    return this.updateProductNumber(poId, pn - pNum);
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
      List ptl = session.createQuery("from " + Pttype.class.getName() + " where name='" + prodType + "'").list();
      if (ptl.size() != 1)
      {
        logger.error("No prodtype existed with the name: " + prodType);
        return null;
      }
      for (Iterator it = ptl.iterator(); it.hasNext();)
      {
        Pttype pt = (Pttype) it.next();
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
      List ptl = session.createQuery("from " + Pttype.class.getName() + " where name='" + prodType + "'").list();

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

  public String getProdTypebyProdTypeId(Integer prodTypeId)
  {
    // TODO Auto-generated method stub
    return null;
  }

}
