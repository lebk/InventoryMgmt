package com.leikai.action;

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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.leikai.dto.JobDTO;
import com.leikai.dto.JobprogressDTO;
import com.leikai.dto.UserDTO;
import com.leikai.enumType.ProductEnumType;
import com.leikai.po.Job;
import com.leikai.po.Jobprogress;
import com.leikai.po.Location;
import com.leikai.po.Os;
import com.leikai.po.Product;
import com.leikai.po.User;
import com.leikai.services.JobService;
import com.leikai.services.LocationService;
import com.leikai.services.OsService;
import com.leikai.services.ProductService;
import com.leikai.services.UserService;
import com.leikai.services.impl.JobServiceImpl;
import com.leikai.services.impl.LocationServiceImpl;
import com.leikai.services.impl.OsServiceImpl;
import com.leikai.services.impl.ProductServiceImpl;
import com.leikai.services.impl.UserServiceImpl;
import com.leikai.util.VMFactoryConfigUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TilesAction extends ActionSupport {
  
  static Logger logger = Logger.getLogger(TilesAction.class);
  private static final long serialVersionUID = -2613425890762568273L;
  private JobService js = new JobServiceImpl();
  private UserService us = new UserServiceImpl();
  static final Integer iAdminType = 1;
  static final Integer iRegularType = 2;
  private String addUserName;
  private String addUserPassword;
  private String addUserEmail;
  private boolean addasAdmin;
 
  
  private List<JobDTO> jobDtoList;
  private JobDTO jobDto;
  private List<UserDTO> userDtoList;

  
  private List<Os> osList;
  OsService os = new OsServiceImpl();
  private static String vsPhereFolder = VMFactoryConfigUtil.getFolder();
  private List<String> vsPhereClientList = new ArrayList<String>();
  private List<String> extraOsNameList = new ArrayList<String>();
  private String extraOsName;
  private String clientFolder;
  static String sClient;
  private String osAccount = null;
  private String osPassword = null;
  private String newOsName;
  private String oldOsName;
  
  ProductService ps = new ProductServiceImpl();
  private List<Product> productList;
  private List<String> productName;
  private String yourProductName;
  private String newProductName;
  private String oldProductName;
  
  
  private File fileUpload;
  private String fileUploadContentType;
  private String fileUploadFileName;
  private String fileUploadDir;
  private List<String> productTypeList = new ArrayList<String>();
  private String yourProductType;
  private String productVersion;
 
  private List<Os> supportOsList = new ArrayList<Os>();
  private List<String> supportOsNameList = new ArrayList<String>();
  private String yourSupportOsName;
  private static final int blocksize = 8192;
  
  


  private List<JobprogressDTO> jobprogressDtoList;



  LocationService ls = new LocationServiceImpl();

  // private boolean activateProduct;
  // private String productKey;

  private Integer userID;
  private Integer jobID;

  private List<String> osNameList;
  private List<Os> osNameObjectList;

  private List<Product> productNameObjectList;
 // private List<String> locationNameList;
  private List<Location> locationNameObjectList;
  private List<String> locationNameList;
 
  private String locationName;
  

  
  

  Map<String, List<String>> productIDOsMap = new HashMap();
  
  private String username;
  
  private Integer pageNow = 1;
  private Integer pageSize = 30;
  private Integer pageInTotal;
  
  public Integer getPageNow()
  {
    return pageNow;
  }
  public void setPageNow(Integer pageNow)
  {
    this.pageNow = pageNow;
  }
  
  public Integer getPageSize()
  {
    return pageSize;
  }
  public void setPageSize(Integer pageSize)
  {
    this.pageSize = pageSize;
  }
  
  public Integer getPageInTotal()
  {
     return pageInTotal;
  }
  
  public void setPageInTotal(Integer pageInTotal)
  {
    this.pageInTotal = pageInTotal;
  }
  
  
  public String getUsername()
  {
    return username;
  }
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public List<JobDTO> getJobDtoList()
  {
    return jobDtoList;
  }

  public void setJobDtoList(List<JobDTO> jobDtoList)
  {
    this.jobDtoList = jobDtoList;
  }
  

  private void convertJobListToJobDTOList(List<Job> jl, List<JobDTO> jobDtoList)
  {
    for (Job j : jl)
    {
      jobDtoList.add(new JobDTO(j));
    }
  }
  private void convertJobListToJobDTOListbyStarttime(List<Job> jl, List<JobDTO> jobDtoList)
  {
    for (Job j : jl)
    {
      jobDtoList.add(new JobDTO(j));
    }
    Collections.reverse(jobDtoList);
  }
  
  public Integer countTotalPage(Integer pageSize, Integer allRow)
  {
    Integer totalPage = allRow % pageSize ==0 ? allRow/pageSize : allRow/pageSize+1;
    return totalPage;
  }
  
  public String jobstatus()
  {
    Map session = ActionContext.getContext().getSession();
    username = (String) ActionContext.getContext().getSession().get("username");
    logger.info("the quried user is: " + session.get("username"));
    Integer userId = us.getUserIdByUsername(username);
    
    jobDtoList = new ArrayList<JobDTO>();
    Integer rowInTotal = js.getAllJobs().size();
    pageInTotal = countTotalPage(pageSize,rowInTotal);
    
    logger.info("page in total is: " + pageInTotal);
    logger.info("page size is: " + pageSize);
    logger.info("page now is: " + pageNow);
    
    List<Job> jl = js.getJobsByUser(userId,pageSize,pageNow);

    if (0 == jl.size())
      session.put("NOJOBFOUND", "true");
    //convertJobListToJobDTOListbyStarttime(jl, jobDtoList);
    convertJobListToJobDTOList(jl, jobDtoList);
    for (JobDTO jobDto : jobDtoList)
    {
      logger.info("job id: " + jobDto.getId());
    }
    return "jobstatus";    
  }
  
  public String jobmanage()
  {
    return "jobmanage";
  }
  
  public String showvmlist()
  {
    Map session = ActionContext.getContext().getSession();
    jobDtoList = new ArrayList<JobDTO>();
    List<Job> jl = js.getAllCompleteJobs();
    if (0 == jl.size())
      session.put("NOVMFOUND", "true");
    convertJobListToJobDTOList(jl, jobDtoList);

    return "showvmlist";    
  }

  public String admin()
  {
    return "admin";   
  }

//  @Override
//  public String execute() throws Exception {
//    // TODO Auto-generated method stub
//    return jobstatus();
//  }
  
  /*
  //
  // Job Management
  //
  */
  public Integer getJobID()
  {
    return jobID;
  }

  public void setJobID(Integer jobID)
  {
    this.jobID = jobID;
  }
  
  public Map getProductIDOsMap()
  {
    return productIDOsMap;
  }

  public void setProductIDOsMap(Map productIDOsMap)
  {
    this.productIDOsMap = productIDOsMap;
  }
  
  public void setProductNameObjectList(List<Product> productNameObjectList)
  {
    this.productNameObjectList = productNameObjectList;
  }

  public List<Product> getProductNameObjectList()
  {
    return productNameObjectList;
  }
  
  public void setOsNameObjectList(List<Os> osNameObjectList)
  {
    this.osNameObjectList = osNameObjectList;
  }

  public List<Os> getOsNameObjectList()
  {
    return osNameObjectList;
  }
  
  public List<String> getOsNameList()
  {
    return osNameList;
  }

  public void setOsNameList(List<String> osNameList)
  {
    this.osNameList = osNameList;
  }
  
 
  public String getLocationName()
  {
	  return locationName;
  }
  public void setLocationName()
  {
	  this.locationName = locationName;
  }
  public List<Product> getProductNameList()
  {
    return ps.getProductList();
  }
  
  public List<String> getLocationNameList()
  {
	  return locationNameList;
  }
  
  public void setLocationNameList(List<String> locationNameList)
  {
	  this.locationNameList = locationNameList;
  }
  
  public List<Location> getLocationList()
  {
    return ls.getBaseTargetVMLocationList();
  }
  
 // public void setLocationtion(List<String> locationList)
  //{
//	  this.locationObjectList = locationList;
 // }
  
 
  public List<JobprogressDTO> getJobprogressDtoList()
  {
    return jobprogressDtoList;
  }

  public void setJobprogressDtoList(List<JobprogressDTO> jobprogressDtoList)
  {
    this.jobprogressDtoList = jobprogressDtoList;
  }
  
  
  private void convertJobprogressListToJobprogressDTOList(List<Jobprogress> jpl, List<JobprogressDTO> jobprogressDtoList)
  {
    for (Jobprogress jp : jpl)
    {
      jobprogressDtoList.add(new JobprogressDTO(jp));
    }
  }
  
  public String showJobProgress()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    userID = us.getUserIdByUsername(userName);

    jobprogressDtoList = new ArrayList<JobprogressDTO>();

    logger.info("Job Progresss of Job ID " + jobID);
    session.put("StopJobID", jobID);
    List<Jobprogress> jpl = js.getJobStatusList(jobID);
    logger.info("jobID-" + jobID + ":jobprogresslist size is " + jpl.size());

    String currentstatus = js.getCurrentJobStatus(jobID, userID);
    logger.info("Current Job ID is: " + jobID + " Status is: " + currentstatus + ".");

    boolean bStop = js.isAllowedToStop(jobID, userID);
    if (bStop)
    {
      session.put("ALLOWTOSTOP", "YES");
    }
    if (js.isJobRequestToStop(jobID))
    { // this function is not good-named...
      if (!currentstatus.equals("Stop")) // this is hard-code, will need to be
                                         // changed when the API is available...
        session.put("REQUESTTOSTOP", "YES");
    }
    convertJobprogressListToJobprogressDTOList(jpl, jobprogressDtoList);
    for (JobprogressDTO jpDTO : jobprogressDtoList)
    {
      logger.info("job progress is: " + jpDTO.getJobStatus());
    }

    return "success";
  }
  
  
  public String startJob() throws Exception
  {
    // Show Product Name and Version List
    productNameObjectList = new ArrayList<Product>();
    productNameObjectList = getProductNameList();

    for (Product p : productNameObjectList)
    {
      osNameObjectList = new ArrayList<Os>();
      osNameList = new ArrayList<String>();

      osNameObjectList = os.getOsListFilterByProductId(p.getId());
      for (Os o : osNameObjectList)
      {
        osNameList.add(o.getName());
      }
      productIDOsMap.put(p.getName(), osNameList);
     
    }
    
   locationNameObjectList = new ArrayList<Location>();
   locationNameList = new ArrayList<String>();

    locationNameObjectList = getLocationList();
    for(Location L : locationNameObjectList)
    {
   	  locationNameList.add(L.getUrl());
    }
  

    // Show Location List, we don't need the location list any more since we use a NFS mount
    return "startJob";
  }
  
  public String stopJob()
  {
    Map session = ActionContext.getContext().getSession();
    jobID = (Integer) session.get("StopJobID");
    if (jobID != 0)
    {
      logger.info("stop job id is: " + jobID);
      String userName = (String) session.get("username");
      logger.info("the stop user is: " + session.get("username"));
      userID = us.getUserIdByUsername(userName);
      js.stopJob(jobID, userID);

    }
    else
      return "error";

    session.remove("StopJobID");
    return "success";
  }
  
  /*
  //
  // File Upload Management
  //
  */
  public void setYourSupportOsName(String yourSupportOsName)
  {
      this.yourSupportOsName = yourSupportOsName;
  }
  public String getYourSupportOsName()
  {
    return yourSupportOsName;
  }
  
  public void setSupportOsList(List<Os> supportOsList)
  {
      this.supportOsList = supportOsList;
  }
  public List<Os> getSupportOsList()
  {
    return supportOsList;
  }
  
  public void setSupportOsNameList(List<String> supportOsNameList)
  {
      this.supportOsNameList = supportOsNameList;
  }
  public List<String> getSupportOsNameList()
  {
    return supportOsNameList;
  }
  
  public void setProductTypeList(List<String> productTypeList)
  {
      this.productTypeList = productTypeList;
  }
  
  public List<String> getProductTypeList()
  {
    return productTypeList;
  }
  
  public void setYourProductType(String yourProductType)
  {
      this.yourProductType = yourProductType;
  }
  
  public String getYourProductType()
  {
    return yourProductType;
  }
  
  public void setProductVersion(String productVersion)
  {
      this.productVersion = productVersion;
  }
  public String getProductVersion()
  {
    return productVersion;
  }
  
  public String getFileUploadContentType()
  {
    return fileUploadContentType;
  }

  public void setFileUploadContentType(String fileUploadContentType)
  {
    this.fileUploadContentType = fileUploadContentType;
  }

  public String getFileUploadFileName()
  {
    return fileUploadFileName;
  }

  public void setFileUploadFileName(String fileUploadFileName)
  {
    this.fileUploadFileName = fileUploadFileName;
  }

  public File getFileUpload()
  {
    return fileUpload;
  }
  public void setFileUpload(File fileUpload)
  {
    this.fileUpload = fileUpload;
  }
  public String getFileUploadDir()
  {
    return fileUploadDir;
  }

  public void setFileUploadDir(String fileUploadDir)
  {
    this.fileUploadDir = fileUploadDir;
  }
  
  public String uploadFile() throws Exception
  {   
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    boolean bAllowed = true;
    
    
    logger.info("supported os list: " + yourSupportOsName);
    logger.info("product version you input is: " + productVersion);
    logger.info("product type you select is: " + yourProductType);
    //logger.info("rightSupportedOsList size is: " + rightSupportedOsList.size());
    if (yourSupportOsName == null || yourSupportOsName.equals(""))
      bAllowed = false;
    if (ps.isProductExisted(yourProductType, productVersion))
      bAllowed = false;  
    if (ps.isProductExisted(fileUploadFileName))
      bAllowed = false;
    
    if (!bAllowed){
      session.put("NotAllowed","true");
      return SUCCESS;
    }
    
    String[] osList = yourSupportOsName.split(", ");
    List<String> osStringList = new ArrayList<String>();
    for (String s:osList)
    {
      logger.info("os name list is: " + s);
      osStringList.add(s);
    } 
    
    // the directory to upload to
    VMFactoryConfigUtil.init();
    String uploadDir = VMFactoryConfigUtil.getBaseProductLocation() + yourProductType + "/" + productVersion +"/"; 
    //String uploadDir = ServletActionContext.getServletContext().getRealPath("/resources") + "\\";
    fileUploadDir = uploadDir;
    // write the file to the file specified
    logger.info("upload dir: " + fileUploadDir);
    try{
    File dirPath = new File(uploadDir);

    if (!dirPath.exists())
    {
      dirPath.mkdirs();
    }
    }
    catch (Exception e)
    {
      logger.info("Create Upload Dir Exception: "+e.getMessage());
      session.put("NotAllowed","true");
      return SUCCESS;
    }
    if (!fileUpload.exists()){
      logger.info("File doesn't exist!");
      session.put("NotAllowed","true");
      return SUCCESS;
    }
    try{
    // retrieve the file data
    long filesize = fileUpload.length();
    
    logger.info("upload size: " + filesize);
  
    InputStream stream = new FileInputStream(fileUpload);
   
    // write the file to the file specified
    OutputStream bos = new FileOutputStream(uploadDir + fileUploadFileName);
    int bytesRead;
    byte[] buffer = new byte[blocksize];

    while ((bytesRead = stream.read(buffer, 0, blocksize)) != -1)
    {
      bos.write(buffer, 0, bytesRead);

    }

    bos.close();
    stream.close();
    }
    catch(Exception e){
        logger.info("Unable to create: "+e.getMessage());
        session.put("NotAllowed","true");
        return SUCCESS;
    }
    boolean badd = ps.addProduct(fileUploadFileName, productVersion, null, yourProductType, osStringList, userName);
    if (!badd){ 
      logger.info("Add product falied!");
      bAllowed = false;
    }
    if (!bAllowed){
      session.put("NotAllowed","true");
      return SUCCESS;
    }
    // place the data into the request for retrieval on next page

    logger.info("upload is successful!");
    return SUCCESS;

  }
  
  public String displayUploadFile()
  {  
    productTypeList = ps.getSupportedProductType();
    
    supportOsList = os.getOsListFromDB();
    for (Os o:supportOsList){
      supportOsNameList.add(o.getName());
    }
    
    return "displayUploadFile";
  }
  
  /*
  //
  // Product Management
  //
  */
  public String getOldProductName()
  {
    return oldProductName;
  }

  public void setOldProductName(String oldProductName)
  {
    this.oldProductName = oldProductName;
  }
 
  public String getNewProductName()
  {
    return newProductName;
  }

  public void setNewProductName(String newProductName)
  {
    this.newProductName = newProductName;
  }
  
  public boolean addProduct(String pName, String version, String key, String productType, List<String> supportedOsList, String opUser)
  {
    return ps.addProduct(pName, version, key, productType, supportedOsList, opUser);
  }

  public boolean removeProduct(String pName, String uName)
  {
    return true;
  }

  public boolean updateProductName(String oldName, String newName)
  {
    return true;
  }


  public boolean updateProductVersion(String pName)
  {
    return true;
  }

  public List<Product> getProductListfilterByOs(String osName)
  {
    return null;
  }

  public List<Product> getProductList()
  {
    return productList;
  }

  public void setProductList(List<Product> productList)
  {
    this.productList = productList;
  }

  public List<String> getProductName()
  {
    return productName;
  }

  public void setProductName(List<String> productName)
  {
    this.productName = productName;
  }
  
  public String getYourProductName()
  {
    return yourProductName;
  }

  public void setYourProductName(String yourProductName)
  {
    this.yourProductName = yourProductName;
  }

  public String getDefaultProductName()
  {
    return "Be Confidence in a connected world";
  }
  
  public List<Product> getProductListFromDB()
  {
    return ps.getProductList();
  }
  
  public String displayProduct()
  {
    productList = new ArrayList<Product>();
    productList = getProductListFromDB();
    return "displayProduct";
  }
  public String setProductName()
  {
    return "setProductName";
  }
  
  public String updateProductName()
  {
    if (oldProductName != null && oldProductName.length() > 0)
    {
      if (newProductName != null && newProductName.length() > 0)
      {
        logger.info("old product Name is: " + oldProductName);
        logger.info("updated new product Name is: " + newProductName);
        ps.updateProductName(oldProductName, newProductName);
        return "success";
      }
      else
        return "error";
    } else
      return "error";
  }
  
  /*
  //
  // OS Management
  //
  */
  public List<Os> getOsList()
  {
    return osList;
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
  public void setOsList(List<Os> osList)
  {
    this.osList = osList;
  }
  public List<Os> getOsListFromDB()
  {
    return os.getOsListFromDB();
  }
  public void setVsPhereClientList(List<String> vsPhereClientList)
  {
    this.vsPhereClientList = vsPhereClientList;
  }
  public List<String> getVsPhereClientList()
  {
    return vsPhereClientList;
  }
  public void setExtraOsNameList(List<String> extraOsNameList)
  {
    this.extraOsNameList = extraOsNameList;
  }
  public List<String> getExtraOsNameList()
  {
    return extraOsNameList;
  }
  public void setClientFolder(String clientFolder)
  {
    this.clientFolder = clientFolder;
  }
  public String getClientFolder()
  {
    return clientFolder;
  }
  public void setExtraOsName(String extraOsName)
  {
    this.extraOsName = extraOsName;
  }
  public String getExtraOsName()
  {
    return extraOsName;
  }
  
  public String showOSList()
  {
    osList = new ArrayList<Os>();
    osList = getOsListFromDB();
    return "showOSList";
  }
  
  public String syncOS()
  {
    String[] osList = vsPhereFolder.split(",");
    for (String s:osList)
    {
      logger.info("Client name is: " + s);
      vsPhereClientList.add(s);
    } 
    return "syncOS";
  }
  
  public List<String> getExtraOsList(String folder)
  {
    String userName = (String) ActionContext.getContext().getSession().get("username");
    logger.info("getExtraOsListFromInventory is called...");
    return os.getExtraOsListFromInventory(userName,folder);
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
    return "showExtraClientOS";
  }
  
  public String writeExtraOSinDB()
  {
    logger.info("writeExtraOSinDB: " + sClient);
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    boolean isRBCS = false;
  
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

  public String displayAccountandPasswordSetting()
  {
    return "displayAccountandPasswordSetting";
  }
  
  /*
  //
  // User Management
  //
  */
  public void setAddUserName(String addUserName)
  {
    this.addUserName = addUserName;
  }

  public String getAddUserName()
  {
    return addUserName;
  }

  public void setAddUserPassword(String addUserPassword)
  {
    this.addUserPassword = addUserPassword;
  }

  public String getAddUserPassword()
  {
    return addUserPassword;
  }

  public void setAddUserEmail(String addUserEmail)
  {
    this.addUserEmail = addUserEmail;
  }

  public String getAddUserEmail()
  {
    return addUserEmail;
  }

  public void setAddasAdmin(boolean addasAdmin)
  {
    this.addasAdmin = addasAdmin;
  }

  public boolean isAddasAdmin()
  {
    return addasAdmin;
  }

  
  public String displayUser()
  {
    return "displayUser";
  }
  public List<UserDTO> getUserList()
  {
    return userDtoList;
  }

  public void setUserList(List<UserDTO> userList)
  {
    this.userDtoList = userList;
  }

  public String aboutVMF()
  {
    return "aboutVMF";
  }
  
  public String showUserList()
  {
    userDtoList = new ArrayList<UserDTO>();

    String userName = (String) ActionContext.getContext().getSession().get("username");

    List<User> ul = us.getUserList(userName);

    convertUserListToUserDTOList(ul, userDtoList);

    return "showUserList";
  }
  public boolean addUser(String name, String password, Integer type, String email, String opUser)
  {
    return us.addUser(name, password, type, email, opUser);
  }

  public String addUser()
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");

    if (us.isUserValid(addUserName))
    {
      session.put("ADDUSER", "false");
      return SUCCESS;
    }
    if (us.isEmailValid(addUserEmail))
    {
      session.put("ADDEMAIL", "false");
      return SUCCESS;
    }
    if (true == addasAdmin)
    {
      boolean badd = us.addUser(addUserName, addUserPassword, iAdminType, addUserEmail, opUser);
      if (true == badd)
        return SUCCESS;
      else
        return ERROR;
    } else
    {
      boolean badd = us.addUser(addUserName, addUserPassword, iRegularType, addUserEmail, opUser);
      if (true == badd)
        return SUCCESS;
      else
        return ERROR;
    }

  }

  private boolean deleteUser(String name, String opUser)
  {

    return us.deleteUser(name, opUser);
  }

  public String deleteUser()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    boolean bdelete = deleteUser(username, userName);
    if (true == bdelete)
    {
      return SUCCESS;
    } else
    {
      return ERROR;
    }
  }

  private boolean updateUserType(String name, Integer type, String opUser)
  {
    return us.updateUserType(name, type, opUser);

  }

  public String updateUser()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    boolean bAdmin = us.isUserAdmin(username);
    if (true == bAdmin)
    {
      boolean bdelete = updateUserType(username, iRegularType, userName);
      if (true == bdelete)
      {
        return SUCCESS;
      } else
        return ERROR;
    } else
    {
      boolean bdelete = updateUserType(username, iAdminType, userName);
      if (true == bdelete)
      {
        return SUCCESS;
      } else
        return ERROR;
    }

  }
  private void convertUserListToUserDTOList(List<User> ul, List<UserDTO> userDtoList)
  {
    for (User u : ul)
    {
      userDtoList.add(new UserDTO(u));
    }
  }
}
  