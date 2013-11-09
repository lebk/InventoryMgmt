package com.leikai.dao.test;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.leikai.dao.ProductDao;
import com.leikai.dao.PtColorDao;
import com.leikai.dao.PtSizeDao;
import com.leikai.dao.PtTypeDao;
import com.leikai.dao.impl.ProductDaoImpl;
import com.leikai.dao.impl.PtColorDaoImpl;
import com.leikai.dao.impl.PtSizeDaoImpl;
import com.leikai.dao.impl.PtTypeDaoImpl;

public class TestUtil
{

  private static String ptTypeName = "测试产品类型";
  private static String ptcolorName = "测试颜色";
  private static String ptSizeName = "测试大小";
  private static Integer pNum = 100;
  static Logger logger = Logger.getLogger(TestUtil.class);

  public static String getPtTypeName()
  {
    return ptTypeName;
  }

  private static Integer getPtTypeId()
  {
    PtTypeDao ptd = new PtTypeDaoImpl();
    return ptd.getIdByPtType(ptTypeName);
  }

  public static String getPtColorName()
  {
    return ptcolorName;
  }

  private static Integer getPtColorId()
  {
    PtColorDao pcd = new PtColorDaoImpl();
    return pcd.getIdByPtColorName(ptcolorName);
  }

  public static String getPtSizeName()
  {
    return ptSizeName;
  }

  private static Integer getPtSizeId()
  {
    PtSizeDao psd = new PtSizeDaoImpl();
    return psd.getIdByPtSizeName(ptSizeName);
  }

  public static Integer getPNum()
  {
    return pNum;
  }

  public static String getPName()
  {
    String pName = ptTypeName + "-" + ptSizeName + "-" + ptcolorName;
    return pName;
  }

  public static Integer getPoId()
  {
    ProductDao pd = new ProductDaoImpl();
    pd.addProduct(getPName(), getPtTypeId(), getPtColorId(), getPtSizeId(), pNum);
    return pd.getIdByProdName(getPName());
  }

  public static String getRandomPName()
  {
    String rStr = RandomStringUtils.randomAscii(8);
    logger.info("The random string is:" + rStr);
    return rStr;
  }

  public static void main(String[] args)
  {
    for (int i = 0; i < 10; i++)
      logger.info("the string is:" + getRandomPName());
  }

}
