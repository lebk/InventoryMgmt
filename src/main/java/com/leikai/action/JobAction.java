package com.leikai.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class JobAction extends ActionSupport
{
  /**
   * 
   */
  static Logger logger = Logger.getLogger(JobAction.class);
  private JobService js = new JobServiceImpl();
  private UserService us = new UserServiceImpl();

  private List<JobDTO> jobDtoList;
  private JobDTO jobDto;

  private List<JobprogressDTO> jobprogressDtoList;

  private File productUpload;
  private String productUploadContentType;
  private String productUploadName;
  private String productUploadDir;

  OsService os = new OsServiceImpl();
  ProductService ps = new ProductServiceImpl();
  LocationService ls = new LocationServiceImpl();

  private boolean productActivate;
  // private String productKey;
  private boolean productSetting;
  private Integer userID;

  private Integer jobID;

  private String osName;
  private List<String> osNameList;
  private List<Os> osNameObjectList;
  private Integer osID;

  private String productName;
  private List<String> productNameList;
  private Integer productID;
  private List<Product> productNameObjectList;

  private String productVersion;
  private List<String> productVersionList;

  private String locationName;
  private List<String> locationNameList;

  Map<String, List<String>> productIDOsMap = new HashMap();

  public Map getProductIDOsMap()
  {
    return productIDOsMap;
  }

  public void setProductIDOsMap(Map productIDOsMap)
  {
    this.productIDOsMap = productIDOsMap;
  }

  public List<Os> getOsList()
  {
    return os.getOsListFromDB();

  }

  public List<Product> getProductList()
  {
    return ps.getProductList();
  }

  public List<Location> getLocationList()
  {
    return ls.getBaseTargetVMLocationList();
  }

  public boolean isProductSetting()
  {
    return productSetting;
  }

  public void setProductSetting(boolean productSetting)
  {
    this.productSetting = productSetting;
  }

  public boolean isProductActivate()
  {
    return productActivate;
  }

  public void setProductActivate(boolean activateProduct)
  {
    this.productActivate = activateProduct;
  }

  // public String getProductKey()
  // {
  // return productKey;
  // }
  // public void setProductKey(String productKey)
  // {
  // this.productKey=productKey;
  // }

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

  public Integer getJobID()
  {
    return jobID;
  }

  public void setJobID(Integer jobID)
  {
    this.jobID = jobID;
  }

  public String getLocationName()
  {
    return locationName;
  }

  public void setLocationName(String locationName)
  {
    this.locationName = locationName;
  }

  public String getOsName()
  {
    return osName;
  }

  public void setOsName(String osName)
  {
    this.osName = osName;
  }

  public String getProductName()
  {
    return productName;
  }

  public void setProductName(String productName)
  {
    this.productName = productName;
  }

  public void setProductVersion(String productVersion)
  {
    this.productVersion = productVersion;
  }

  public String getProductVersion()
  {
    return productVersion;
  }

  public List<String> getLocationNameList()
  {
    return locationNameList;
  }

  public void setLocationNameList(List<String> locationNameList)
  {
    this.locationNameList = locationNameList;
  }

  public List<String> getOsNameList()
  {
    return osNameList;
  }

  public void setOsNameList(List<String> osNameList)
  {
    this.osNameList = osNameList;
  }

  public List<String> getProductNameList()
  {
    return productNameList;
  }

  public void setProductNameList(List<String> productNameList)
  {
    this.productNameList = productNameList;
  }

  public List<String> getProductVersionList()
  {
    return productVersionList;
  }

  public void setProductVersionList(List<String> productVersionList)
  {
    this.productVersionList = productVersionList;
  }

  public String getFileUploadContentType()
  {
    return productUploadContentType;
  }

  public void setFileUploadContentType(String productUploadContentType)
  {
    this.productUploadContentType = productUploadContentType;
  }

  public String getFileUploadFileName()
  {
    return productUploadName;
  }

  public void setFileUploadFileName(String productUploadName)
  {
    this.productUploadName = productUploadName;
  }

  public File getFileUpload()
  {
    return productUpload;
  }

  public void setFileUpload(File productUpload)
  {
    this.productUpload = productUpload;
  }

  public String getFileUploadDir()
  {
    return productUploadDir;
  }

  public void setFileUploadDir(String productUploadDir)
  {
    this.productUploadDir = productUploadDir;
  }

  protected HttpServletRequest getRequest()
  {
    return ServletActionContext.getRequest();
  }

  public List<JobDTO> getJobDtoList()
  {
    return jobDtoList;
  }

  public void setJobDtoList(List<JobDTO> jobDtoList)
  {
    this.jobDtoList = jobDtoList;
  }

  public List<JobprogressDTO> getJobprogressDtoList()
  {
    return jobprogressDtoList;
  }

  public void setJobprogressDtoList(List<JobprogressDTO> jobprogressDtoList)
  {
    this.jobprogressDtoList = jobprogressDtoList;
  }

  public JobDTO getJobDto()
  {
    return jobDto;
  }

  public void setJobDto(JobDTO jobDto)
  {
    this.jobDto = jobDto;
  }

  public String checkJob()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    logger.info("the start user is: " + session.get("username"));
    userID = us.getUserIdByUsername(userName);
    // if (true == activateProduct)
    // logger.info("keymaster...");
    // else
    // logger.info("Product Key is: " + productKey);

    productID = ps.getIdByProdName(productName);
    logger.info("Product ID is: " + productID);

    osID = os.getIdByOsName(osName);
    logger.info("OS ID is: " + osID);

    if (!js.isVCenterConnectionOk())
      return "connectionoff";

    Job j = js.getJobByOsIdAndPoId(osID, productID);
    if (null != j)
    {
      if (js.isVMCreating(osID, productID))
      {
        return "jobrunning";
      }
      if (js.isVMExisted(osID, productID))
      {

        locationName = j.getTargetVmlocation();
        return "jobexist";
      }
      jobID = j.getId();
      if (js.isJobRequestToStop(jobID))
      { // this function is not good-named...
        String currentstatus = js.getCurrentJobStatus(jobID, userID);
        if (!currentstatus.equals("Stop")) // this is hard-code, will need to be
                                           // changed when the API is
                                           // available...
          return "jobrequesttostop";
      }
    }
    if (js.isMaxAllowedRunningJobsReached())
    {
      return "jobmax";
    }
    logger.info("Product Setting is " + productSetting);
    logger.info("Product Activate: " + productActivate);

    jobID = js.startJob(productID, osID, productSetting, productActivate, userID);
    // logger.info("INFO: Job ID " + jobID + " is returned!");
    if (0 == jobID)
    {
      logger.info("Error: Job ID should NOT be 0!");
      return ERROR;
    }

    return "startjob";
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

    return SUCCESS;
  }

  public String displayJobResult()
  {
    return SUCCESS;
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

    } else
      return "error";
    session.remove("StopJobID");
    return "stopjob";
  }

  private void convertJobListToJobDTOList(List<Job> jl, List<JobDTO> jobDtoList)
  {
    for (Job j : jl)
    {
      jobDtoList.add(new JobDTO(j));
    }
  }

  private void convertJobprogressListToJobprogressDTOList(List<Jobprogress> jpl, List<JobprogressDTO> jobprogressDtoList)
  {
    for (Jobprogress jp : jpl)
    {
      jobprogressDtoList.add(new JobprogressDTO(jp));
    }
  }

  public String showJobResult()
  {
    return SUCCESS;
  }

  public String execute() throws Exception
  {
    //
    // Show Product Name and Version List
    //
    productNameObjectList = new ArrayList<Product>();
    productNameObjectList = getProductList();

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

    //
    // Show Location List
    //
    locationNameList = new ArrayList<String>();
    List<Location> locationList = getLocationList();
    for (Iterator<Location> iterator = locationList.iterator(); iterator.hasNext();)
    {
      Location l = (Location) iterator.next();
      locationNameList.add(l.getUrl());
    }
    return SUCCESS;
  }
}
