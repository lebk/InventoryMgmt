package com.leikai.enumType;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.po.Pttype;
import com.leikai.util.HibernateUtil;

/**
 * copyright: all right reserved
 * 
 * Author: Lei Bo
 * 
 * 2013-10-9
 * 
 */

public class ProductEnumType
{
  final public static String nis2012 = "NIS2012";
  final public static String nis2013 = "NIS2013";
  final public static String sep_jaguar = "SEP_Amber";
  final public static String sep_amber = "SEP_Jaguar";
  final public static String n360 = "N360";
  final public static String unknown = "unknown";
  static Logger logger = Logger.getLogger(ProductEnumType.class);

  public static String getProductTypeByTypeId(Integer id)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ptl = session.createQuery("from " + Pttype.class.getName() + " where id='" + id + "'").list();

      if (ptl.size() != 1)
      {
        logger.error("There should be just one " + Pttype.class.getName() + " existed with id: " + id + ", but now there are: " + ptl.size());
        return unknown;
      }
      for (Iterator iterator = ptl.iterator(); iterator.hasNext();)
      {
        Pttype pt = (Pttype) iterator.next();
        logger.info("usertype id: " + id + ", the type is: " + pt.getName());

        if (pt.getName().equalsIgnoreCase(nis2012))
        {
          return nis2012;
        } else if (pt.getName().equalsIgnoreCase(nis2013))
        {
          return nis2013;
        } else if (pt.getName().equalsIgnoreCase(sep_jaguar))
        {
          return sep_jaguar;
        } else if (pt.getName().equalsIgnoreCase(sep_amber))
        {
          return sep_amber;
        } else if (pt.getName().equalsIgnoreCase(n360))
        {
          return n360;
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
    logger.error("the queried product type does not existed");
    return unknown;
  }
}
