package com.leikai.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.leikai.enumType.ProductEnumType;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class ProductConfigUtil
{
  private static Logger logger = Logger.getLogger(ProductConfigUtil.class);
  private static Properties properties = new Properties();

  private static String getProductConfigFilePath(String prodType)
  {

    String propertiesName = null;
    if (prodType.equals(ProductEnumType.nis2012))
    {
      propertiesName = "NIS2012_Settings.properties";
    } else if (prodType.equals(ProductEnumType.nis2013))
    {
      propertiesName = "NIS2013_Settings.properties";
    } else if (prodType.equals(ProductEnumType.n360))
    {
      propertiesName = "N360_Settings.properties";
    } else
    {
      logger.info("not supported product type, " + prodType);
      return null;
    }
    String baseDir = System.getProperty("user.dir");

    logger.info("basedir: " + baseDir);
    // Temp fix about,Deploy the product env
    if (baseDir.equalsIgnoreCase("/"))
    {
      return "/opt/vmfactory/webapps/VMFactory/WEB-INF/classes/ProductsConfig/" + propertiesName;
    }
    String prodSettings = (baseDir + "/src/main/resources/ProductsConfig/" + propertiesName).replaceAll("%20", " ");


    if (prodSettings.contains("tomcat"))

    {
      logger.info("deploy to tomcat, redrirect to the property file's location");
      if (prodSettings.contains("bin"))
      {
        // If deploy to Tomcat server (the tomcat starts from %TOMCAT_HOME%,
        // use following file path.

        prodSettings = (baseDir + "\\..\\webapps\\VMFactory\\WEB-INF\\classes\\ProductsConfig\\" + propertiesName).replaceAll("%20", " ");
      } else
      {
        // The tomcat is started by Eclipse Tomcat plugin,%TOMCAT_HOME%
        prodSettings = (baseDir + "\\webapps\\VMFactory\\WEB-INF\\classes\\ProductsConfig\\" + propertiesName).replaceAll("%20", " ");

      }
    }

    logger.info("after prodSettings: " + prodSettings);

    return prodSettings;

  }

  private static void load(String prodType)
  {
    String prodConfig = null;
    FileInputStream is = null;

    try
    {

      prodConfig = getProductConfigFilePath(prodType);

      logger.info("product config path: " + prodConfig);

      is = new FileInputStream(prodConfig);

      properties.load(is);
    } catch (IOException e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
    } catch (Exception e)
    {
      logger.error("Read product config file: " + prodConfig + " error!");

      logger.error(e.getMessage());

    } finally
    {
      try
      {
        if (is != null)
        {
          is.close();
        }
      } catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  public static Map<String, Integer> getProdSettingMap(String prodType)
  {
    load(prodType);

    Map<String, Integer> psm = new HashMap<String, Integer>();

    Properties p = properties;

    Iterator it = p.keySet().iterator();
    while (it.hasNext())
    {
      String key = (String) it.next();
      psm.put(key, Integer.valueOf((String) p.get(key)));
    }

    return psm;
  }

  public static void main(String[] args)
  {
    Map m = getProdSettingMap("N360");

    Iterator it = m.keySet().iterator();
    while (it.hasNext())
    {
      String key = (String) it.next();
      logger.info(key + ":" + m.get(key));
    }
  }

}
