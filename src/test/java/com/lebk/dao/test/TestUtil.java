package com.lebk.dao.test;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.lebk.dao.ProductDao;
import com.lebk.dao.PtColorDao;
import com.lebk.dao.PtSizeDao;
import com.lebk.dao.PtTypeDao;
import com.lebk.dao.impl.ProductDaoImpl;
import com.lebk.dao.impl.PtColorDaoImpl;
import com.lebk.dao.impl.PtSizeDaoImpl;
import com.lebk.dao.impl.PtTypeDaoImpl;

public class TestUtil
{

  private static String ptTypeName = "测试产品类型";
  private static String ptcolorName = "测试颜色";
  private static String ptSizeName = "测试大小";
  private static Integer opUserId = 1;// 管理员
  private static Float pNum = new Float(100.0);
  static Logger logger = Logger.getLogger(TestUtil.class);

  public static String getPtTypeName()
  {
    return ptTypeName;
  }

  public static Integer getOpUserId()
  {
    return opUserId;
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

  public static Float getPNum()
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
    pd.updateProduct(getPName(), getPtTypeId(), getPtColorId(), getPtSizeId(), pNum,1, 1);
    return pd.getIdByProdName(getPName());
  }

  public static String getRandString(int count)
  {
    if (count <= 0)
    {
      logger.error("Expect count greater than 0,return empty");
      return "";
    }
    String rStr = RandomStringUtils.randomAscii(count);
    logger.info("The random string is:" + rStr);
    return rStr;
  }

  public static void main(String[] args)
  {
    for (int i = 0; i < 10; i++)
      logger.info("the string is:" + getRandString(8));
  }

}
