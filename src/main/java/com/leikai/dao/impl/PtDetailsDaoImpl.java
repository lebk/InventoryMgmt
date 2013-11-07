package com.leikai.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.*;
import com.leikai.po.Businesstype;
import com.leikai.po.Ptdetails;
import com.leikai.po.Ptsize;
import com.leikai.util.HibernateUtil;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-7
 */
public class PtDetailsDaoImpl implements PtDetailsDao
{
  static Logger logger = Logger.getLogger(PtDetailsDaoImpl.class);

  public boolean addPtDetail(Integer poId, Integer btId, Integer pNum, Integer opUserId)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      logger.info("begin to add the product detail information for poId:" + poId);

      transaction = session.beginTransaction();
      Ptdetails pd = new Ptdetails();

      pd.setPoId(poId);
      pd.setId(btId);
      pd.setNum(pNum);
      pd.setOpUserId(opUserId);
      pd.setDate(new Date());
      session.save(pd);
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

  public boolean deletePtDetail(Integer id)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Ptdetails ps = (Ptdetails) session.get(Ptdetails.class, id);
      session.delete(ps);
      transaction.commit();
      logger.info("delete product detail id: " + id + " successfully");
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    logger.error("fail to product detail with id: " + id);

    return false;
  }

  public List<Ptdetails> getAllPtDetails()
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List pdl = new ArrayList<Ptdetails>();
    Transaction transaction = null;

    try {
      transaction = session.beginTransaction();
      List pdq = session.createQuery(
          "from " + Ptdetails.class.getName()).list();
      for (Iterator it = pdq.iterator(); it.hasNext();) {
        Ptdetails pd = (Ptdetails) it.next();
        pdl.add(pd);
      }
      transaction.commit();
    } catch (HibernateException e) {
      transaction.rollback();
      e.printStackTrace();

    } finally {
      session.close();
    }
    return pdl;
  }

}
