package com.lebk.enumType;

import org.apache.log4j.Logger;

import com.lebk.dao.BusinessTypeDao;
import com.lebk.dao.impl.BusinessTypeDaoImpl;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-14
 */
public class BusinessEnumType
{
  final public static String in = "入库";
  final public static String out = "出库";
  final public static String unkown = "未知类型";
  static Logger logger = Logger.getLogger(BusinessEnumType.class);
  static BusinessTypeDao btd = new BusinessTypeDaoImpl();

  public static Integer getIdByBusinessType(String type)
  {
    Integer id = btd.getIdByType(type);
    logger.info("The id is:" + id + " for the business type:" + type);
    return id;
  }

  public static void main(String[] args)
  {
    logger.info("The id for type:" + in + " is: " + getIdByBusinessType(in));
    logger.info("The id for type:" + out + " is: " + getIdByBusinessType(out));
    logger.info("The id for type:" + unkown + " is: " + getIdByBusinessType(unkown));
  }
}
