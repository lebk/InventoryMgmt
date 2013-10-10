package com.leikai.test;

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class TestUtil
{
  static Logger logger = Logger.getLogger(TestUtil.class);

  public static String getRandomString(int length)
  {

    if (length < 1 || length > 10)
    {
      logger.warn("Valid leng is: 1 ~ 10");
      return null;
    }
    Random rd = new Random();
    String str = String.valueOf(rd.nextInt());
    logger.info("Str: " + str);
    String ret = str.substring(str.length() - length);
    logger.info("ret str: " + ret);

    return ret;

  }
}
