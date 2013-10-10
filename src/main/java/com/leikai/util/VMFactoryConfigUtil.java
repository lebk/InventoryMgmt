package com.leikai.util;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.leikai.enumType.ProductEnumType;

public class VMFactoryConfigUtil
{
  private static Properties properties = new Properties();
  static Logger logger = Logger.getLogger(VMFactoryConfigUtil.class);

  static
  {
    logger.info("-------- Begin to init the vmf config from VMconfig.peroperites --------");
    init();
    logger.info("-------- End to init the vmf config from VMconfig.peroperites --------");
  }

  private static String getVMFConfigDir()
  {
    String baseDir = System.getProperty("user.dir");

    logger.info("basedir: " + baseDir);
    // Temp fix about,Deploy the product env
    if (baseDir.equalsIgnoreCase("/"))
    {
      return "/opt/InventoryMgmt/webapps/InventoryMgmt/WEB-INF/classes/vmfactoryConfig.properties";
    }
    String vmfconfig = (baseDir + "/src/main/resources/vmfactoryConfig.properties").replaceAll("%20", " ");

    VMFactoryConfigUtil object = new VMFactoryConfigUtil();

    logger.info("before vmfconfig : " + vmfconfig);

    if (vmfconfig.contains("tomcat"))

    {
      logger.info("deploy to tomcat, redrirect to the property file's location");
      if (vmfconfig.contains("bin"))
      {
        // If deploy to Tomcat server (the tomcat starts from %TOMCAT_HOME%,
        // use following file path.

        vmfconfig = (baseDir + "\\..\\webapps\\InventoryMgmt\\WEB-INF\\classes\\vmfactoryConfig.properties").replaceAll("%20", " ");
      } else
      {
        // The tomcat is started by Eclipse Tomcat plugin,%TOMCAT_HOME%
        vmfconfig = (baseDir + "\\webapps\\InventoryMgmt\\WEB-INF\\classes\\vmfactoryConfig.properties").replaceAll("%20", " ");

      }
    }
    logger.info("after vmfconfig : " + vmfconfig);

    logger.info("after vmfconfig : " + vmfconfig);

    return vmfconfig;

  }

  private static String getProductConfigFile(String prodType)
  {
    String baseDir = System.getProperty("user.dir");

    logger.info("basedir: " + baseDir);
    // Temp fix about,Deploy the product env
    if (baseDir.equalsIgnoreCase("/"))
    {
      return "/opt/tomcat/webapps/InventoryMgmt/WEB-INF/classes/ProductsConfig/NIS2012_Settings.txt";
    }
    String prodSettings = (baseDir + "/src/main/resources/ProductsConfig/NIS2012_Settings.txt").replaceAll("%20", " ");

    VMFactoryConfigUtil object = new VMFactoryConfigUtil();

    if (prodSettings.contains("tomcat"))

    {
      logger.info("deploy to tomcat, redrirect to the property file's location");
      if (prodSettings.contains("bin"))
      {
        // If deploy to Tomcat server (the tomcat starts from %TOMCAT_HOME%,
        // use following file path.

        prodSettings = (baseDir + "\\..\\webapps\\InventoryMgmt\\WEB-INF\\classes\\ProductsConfig\\NIS2012_Settings.txt").replaceAll("%20", " ");
      } else
      {
        // The tomcat is started by Eclipse Tomcat plugin,%TOMCAT_HOME%
        prodSettings = (baseDir + "\\webapps\\InventoryMgmt\\WEB-INF\\classes\\ProductsConfig\\NIS2012_Settings.txt").replaceAll("%20", " ");

      }
    }

    logger.info("after prodSettings: " + prodSettings);

    return prodSettings;

  }

  public static void init()
  {
    String vmfconfig = null;
    FileInputStream is = null;

    try
    {

      vmfconfig = getVMFConfigDir();

      logger.info("vmfconfig path: " + vmfconfig);

      is = new FileInputStream(vmfconfig);

      properties.load(is);
    } catch (IOException e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
    } catch (Exception e)
    {
      logger.error("Read vmfconfig file: " + vmfconfig + " error!");

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

  public static String getProperty(String key)
  {
    return properties.getProperty(key).trim();
  }

  public static String getvSphereUrl()
  {
    return properties.getProperty("vsphere_url").trim();
  }

  public static String getvSphereUsername()
  {
    return properties.getProperty("vsphere_username").trim();
  }

  public static String getvSpherePassword()
  {
    return properties.getProperty("vsphere_password").trim();
  }

  public static String getvSphereHttpSdkUrl()
  {
    return "https://" + getvSphereUrl() + "/sdk";
  }

  public static String getBaseProductLocation()
  {
    return properties.getProperty("vsphere_baseProductLocation").trim();
  }

  public static String getVSphereDatacenterName()
  {
    return properties.getProperty("vsphere_datacentername").trim();
  }

  public static String getDatastoreName()
  {
    return properties.getProperty("vshpere_datasorename").trim();
  }

  public static String getVMBaseProductUploadLocation()
  {
    return properties.getProperty("vm_baseProductUploadLocation").trim();
  }

  public static String getExportedOVFLocation()
  {
    return properties.getProperty("vsphere_webportal_exportedOVFLocation").trim();
  }

  public static Integer getMaxAllowedConvertNum()
  {
    return Integer.valueOf(properties.getProperty("maxAllowedConvertNum").trim());
  }

  public static String getMoveConfiguredVMToFinalFileServerScript()
  {
    return properties.getProperty("moveConfiguredVMToFinalFileServerScript").trim();
  }

  public static String getUsernameOffinalFileServer()
  {
    return properties.getProperty("username_finalFileServer").trim();
  }

  public static String getBaseLocationOfFinalFileServer()
  {
    return properties.getProperty("baseLocation_finalFileServer").trim();
  }

  public static String getBaseDirOfFinalFileServer_RBCS()
  {
    return properties.getProperty("baseDir_finalFileServer_RBCS").trim();
  }

  public static String getBaseDirOfFinalFileServer_nonRBCS()
  {
    return properties.getProperty("baseDir_finalFileServer_nonRBCS").trim();
  }

  public static String getDefaultBaseImageUsername()
  {
    return properties.getProperty("defaultBaseImageUserName").trim();
  }

  public static String getDefaultBaseImagePassword()
  {
    return properties.getProperty("defaultBaseImagePassword").trim();
  }

  public static List<String> getExclusiveVMList()
  {
    List<String> exclusiveVMList = new ArrayList<String>();

    String vmListStr = properties.getProperty("exclusiveVMList").trim();
    String[] vmList = vmListStr.split(",");
    for (String vm : vmList)
    {
      logger.info("exclusive vm:" + vm);
      exclusiveVMList.add(vm);
    }
    return exclusiveVMList;
  }

  public static String getEmailServer()
  {
    return properties.getProperty("emailServer").trim();

  }

  public static String getEmailSender()
  {
    return properties.getProperty("emailSender").trim();

  }

  public static String getEtrackServer()
  {
    return properties.getProperty("etrackServer").trim();

  }

  public static String getEtrackOraclePort()
  {
    return properties.getProperty("etrackOraclePort").trim();

  }

  public static String getProdSettingToolPath()
  {
    return properties.getProperty("prodSettingToolPath").trim();

  }

  public static String getATRunnerSrcPath()
  {
    return properties.getProperty("atrunner_src_Path").trim();

  }

  public static String getATRunnerDstPath()
  {
    return properties.getProperty("atrunner_dst_Path").trim();

  }

  public static String getProdActivationScriptPath(String prodType)
  {
    String path;
    String basedir = VMFactoryConfigUtil.getATRunnerDstPath();

    if (prodType.equals(ProductEnumType.nis2012))
    {
      path = basedir + "Activation_nis2012.ats";
    } else if (prodType.equals(ProductEnumType.nis2013))
    {
      path = basedir + "Activation_nis2013.ats";
    } else if (prodType.equals(ProductEnumType.n360))
    {
      path = basedir + "Activation_n360.ats";
    } else
    {
      logger.error("Not supported product type, return null");
      return null;
    }

    return path;
  }

  public static String getFolder()
  {
    return properties.getProperty("vsphere_folder").trim();
  }

  public static String getProdSettingToolName()
  {
    String psToolPath = getProdSettingToolPath();
    String psToolName = null;
    if (File.separator.equals("\\"))
    {
      logger.info("win platfrom!");
      psToolName = psToolPath.substring(psToolPath.lastIndexOf("\\") + 1);
    } else
    {
      logger.info("linux platfrom!");
      psToolName = psToolPath.substring(psToolPath.lastIndexOf("/") + 1);
    }
    return psToolName;
  }

  public static List<String> getPoentialProductInstallLogPath()
  {

    List<String> installLogPath = new ArrayList<String>();

    String logPathStr = properties.getProperty("poentialProductInstallLogPath").trim();
    String[] logPath = logPathStr.split(",");
    for (String path : logPath)
    {
      logger.info("potiential log path is:" + path);
      installLogPath.add(path);
    }
    return installLogPath;
  }

  public static String getProductInstallLogLocalPath()
  {
    return properties.getProperty("productInstallLocalPath").trim();
  }

  public static void main(String[] args)
  {
    logger.info(getvSphereUrl());
    logger.info(getvSphereHttpSdkUrl());
    logger.info(getBaseProductLocation());
    logger.info(getExportedOVFLocation());
    logger.info(getMaxAllowedConvertNum());
    logger.info(getVSphereDatacenterName());
    logger.info(getDatastoreName());
    logger.info(getVMBaseProductUploadLocation());
    logger.info(getMoveConfiguredVMToFinalFileServerScript());
    logger.info(getUsernameOffinalFileServer());
    logger.info(getBaseLocationOfFinalFileServer());
    logger.info(getBaseDirOfFinalFileServer_RBCS());
    logger.info(getBaseDirOfFinalFileServer_nonRBCS());
    logger.info(getFolder());

    logger.info(getDefaultBaseImageUsername());
    logger.info(getDefaultBaseImagePassword());
    logger.info(getExclusiveVMList());
    logger.info(getEmailServer());
    logger.info(getEmailSender());
    logger.info(getEtrackServer());
    logger.info(getEtrackOraclePort());
    logger.info(getProdSettingToolPath());
    logger.info(getProdSettingToolName());

    logger.info(getPoentialProductInstallLogPath());
    logger.info(getProductInstallLogLocalPath());
    logger.info(getATRunnerSrcPath());
    logger.info(getATRunnerDstPath());

    logger.info(getProdActivationScriptPath(ProductEnumType.nis2012));
    logger.info(getProdActivationScriptPath(ProductEnumType.nis2013));
    logger.info(getProdActivationScriptPath(ProductEnumType.n360));
    logger.info(getProdActivationScriptPath(ProductEnumType.sep_amber));

  }
}
