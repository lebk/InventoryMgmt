package com.leikai.action;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leikai.po.Os;
import com.leikai.services.OsService;
import com.leikai.services.impl.OsServiceImpl;
import com.leikai.util.VMFactoryConfigUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class OsAction extends ActionSupport
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  static Logger logger = Logger.getLogger(OsAction.class);
  OsService os = new OsServiceImpl();

  private List<Os> osList;
  private String yourOsName;
  //private List<String> osNameExtraList;

  //private List<Os> extraOsList = new ArrayList<Os>();
  private List<String> extraOsNameList = new ArrayList<String>();
  private String extraOsName;
  
  
  private String newOsName;
  private String oldOsName;
  private String osAccount = null;
  private String osPassword = null;
  
  
  private static String vsPhereFolder = VMFactoryConfigUtil.getFolder();
  
  private List<String> vsPhereClientList = new ArrayList<String>();
  private String clientFolder;
  static String sClient;
  
  public void setOsAccount(String osAccount)
  {
    this.osAccount = osAccount;
  }
  public String getOsAccount()
  {
    return osAccount;
  }
  
  public void setOsPassword(String osPassword)
  {
    this.osPassword = osPassword;
  }
  
  public String getOsPassword()
  {
    return osPassword;
  }
  
  
  public void setClientFolder(String clientFolder)
  {
    this.clientFolder = clientFolder;
  }
  public String getClientFolder()
  {
    return clientFolder;
  }
  
  public void setVsPhereClientList(List<String> vsPhereClientList)
  {
    this.vsPhereClientList = vsPhereClientList;
  }
  public List<String> getVsPhereClientList()
  {
    return vsPhereClientList;
  }
  public void setExtraOsName(String extraOsName)
  {
    this.extraOsName = extraOsName;
  }
  public String getExtraOsName()
  {
    return extraOsName;
  }
  
  
  
  public void setExtraOsNameList(List<String> extraOsNameList)
  {
    this.extraOsNameList = extraOsNameList;
  }
  public List<String> getExtraOsNameList()
  {
    return extraOsNameList;
  }

  public String getNewOsName()
  {
    return newOsName;
  }

  public void setNewOsName(String newOsName)
  {
    this.newOsName = newOsName;
  }

  public String getOldOsName()
  {
    return oldOsName;
  }

  public void setOldOsName(String oldOsName)
  {
    this.oldOsName = oldOsName;
  }

  public List<Os> getOsListFromDB()
  {
    return os.getOsListFromDB();

  }

  public List<String> getExtraOsList(String folder)
  {
    String userName = (String) ActionContext.getContext().getSession().get("username");
    logger.info("getExtraOsList is called...");
    return os.getExtraOsListFromInventory(userName,folder);
  }

  public boolean removeOs(String osName, String uName)
  {
    return true;
  }

  public boolean updateOs(Os oldOs, Os newOs)
  {
    return true;
  }

  public String updateOsName() //This is not used, currently, this is done manually...
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    if (oldOsName != null && oldOsName.length() > 0)
    {
      if (newOsName != null && newOsName.length() > 0)
      {
        logger.info("old os Name is: " + oldOsName);
        logger.info("updated new os Name is: " + newOsName);
        //os.updateOsName(oldOsName, newOsName, opUser);
        return "success";
      } else
        return "error";
    } else
      return "error";
  }
  
  
  public String updateOsAccountandPassword()
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    if (oldOsName != null && oldOsName.length() > 0)
    {
        logger.info("old OS name is: " + oldOsName);
        logger.info("new account name is: " + osAccount);
        logger.info("new password is: " + osPassword);
        
        if ((null == osAccount && 0 == osAccount.length())|| osAccount.equals(""))
        {
          return "error";
        }
        if((null == osPassword && 0 == osPassword.length())|| osPassword.equals(""))
        {
          return "error";
        }
        os.updateOs(oldOsName, osAccount, osPassword, opUser);
        return "success";
        
    } else
      return "error";
  }

  public List<Os> getOsList()
  {
    return osList;
  }

  public void setOsList(List<Os> osList)
  {
    this.osList = osList;
  }

//  public List<String> getOsNameExtraList()
//  {
//    return osNameExtraList;
//  }
//
//  public void setOsNameExtraList(List<String> osNameExtraList)
//  {
//    this.osNameExtraList = osNameExtraList;
//  }

  public String getYourOsName()
  {
    return yourOsName;
  }

  public void setYourOsName(String yourOsName)
  {
    this.yourOsName = yourOsName;
  }

  public String getDefaultOsName()
  {
    return "x86 Architecture Platform";
  }

//  public OsAction()
//  {
//    osList = new ArrayList<Os>();
//    osList = getOsListFromDB();
//
//  }

  public String writeExtraOSinDB()
  {
//    extraOsNameList = getExtraOsList(clientFolder);
    logger.info("writeExtraOSinDB: " + sClient);
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    boolean isRBCS = false;
//    logger.info("username is: " + opUser);
//    if (0 == extraOsNameList.size())
//    {
//      logger.info("There is no os list to be synced!");
//    }
//    logger.info("osNameExtraList size is: " + extraOsNameList.size());
    
    String[] extraList = extraOsName.split(", ");
    if (sClient.equals("RBCS"))
      isRBCS= true;
    for (String s:extraList)
    {
      logger.info("extra os name is: " + s);
      
      String adminUser = VMFactoryConfigUtil.getDefaultBaseImageUsername();
      String adminPassword = VMFactoryConfigUtil.getDefaultBaseImagePassword();  
      boolean badd = os.addOs(s, adminUser, adminPassword, isRBCS, opUser);
      if (!badd)
        return ERROR;
    }
    return SUCCESS;
  }

  public String showExtraClientOS()
  {
    Map session = ActionContext.getContext().getSession();
    String clienttmp= null;
    extraOsNameList = new ArrayList<String>();
    clientFolder = clientFolder.trim();
    clienttmp = clientFolder;
    if (clientFolder.equals("Non RBCS")) // hard-coding, will need to be updated if we have new clients...
      clientFolder = "Base OSes";
    logger.info("Your Client is: " + clientFolder);
    sClient = clientFolder;
    extraOsNameList = getExtraOsList(clientFolder);
    if (0 == extraOsNameList.size())
    {
      logger.info("There is no os list to be synced!");
      session.put("NoExtraList", "noextralist");
    }
    clientFolder = clienttmp; // to solve how to map the client
    return SUCCESS;
  }
  
  public String GetDefaultClient()
  {
    return "RBCS";
  }
  
  public String execute()
  {
//    Map session = ActionContext.getContext().getSession();
//    extraOsNameList = new ArrayList<String>();
//    extraOsNameList = getExtraOsList(vsPhereFolder);
//    if (0 == extraOsNameList.size())
//    {
//      logger.info("There is no os list to be synced!");
//      session.put("NoExtraList", "noextralist");
//    }
    
    String[] osList = vsPhereFolder.split(",");
    for (String s:osList)
    {
      logger.info("Client name is: " + s);
      vsPhereClientList.add(s);
    } 
        
    return SUCCESS;
  }

  public String display()
  {
    return NONE;
  }

}
