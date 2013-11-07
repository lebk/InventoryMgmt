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

  final public static String unknown = "unknown";
  static Logger logger = Logger.getLogger(ProductEnumType.class);

  public static String getProductTypeByTypeId(Integer id)
  {

    return unknown;
  }
}
