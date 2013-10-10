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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.leikai.po.Os;
import com.leikai.services.OsService;
import com.leikai.services.ProductService;
import com.leikai.services.impl.OsServiceImpl;
import com.leikai.services.impl.ProductServiceImpl;
import com.leikai.util.VMFactoryConfigUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Sample action that shows how to do file upload with Struts 2.
 */
public class FileUploadAction extends ActionSupport
{
  private static final long serialVersionUID = -9208910183310010569L;
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
  

  
//  private List<String> leftOsList = new ArrayList<String>();
//  private List<String> rightSupportedOsList = new ArrayList<String>();
// 
//  private String leftOs;
//  private String rightSupportedOs;
// 
//  private String leftNumber;
//  private String rightNumber;

 
  ProductService ps = new ProductServiceImpl();
  OsService os = new OsServiceImpl();
  
  static Logger logger = Logger.getLogger(FileUploadAction.class);
  

//  public String getLeftNumber() {
//    return leftNumber;
//  }
// 
//  public void setLeftNumber(String leftNumber) {
//    this.leftNumber = leftNumber;
//  }
// 
//  public String getRightNumber() {
//    return rightNumber;
//  }
// 
//  public void setRightNumber(String rightNumber) {
//    this.rightNumber = rightNumber;
//  }
// 
//  public List<String> getLeftOsList() {
//    return leftOsList;
//  }
// 
//  public void setLeftAntivirusList(List<String> leftOsList) {
//    this.leftOsList = leftOsList;
//  }
// 
//  public List<String> getRightSupportedOsList() {
//    return rightSupportedOsList;
//  }
// 
//  public void setRightAntivirusList(List<String> rightSupportedOsList) {
//    this.rightSupportedOsList = rightSupportedOsList;
//  }
// 
//  public String getLeftOs() {
//    return leftOs;
//  }
// 
//  public void setLeftAntivirus(String leftOs) {
//    this.leftOs = leftOs;
//  }
// 
//  public String getRightSupportedOs() {
//    return rightSupportedOs;
//  }
// 
//  public void setRightAntivirus(String rightSupportedOs) {
//    this.rightSupportedOs = rightSupportedOs;
//  }
  
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
  
  

  
  protected HttpServletRequest getRequest()
  {
    return ServletActionContext.getRequest();
  }
  public String execute() throws Exception
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
    String uploadDir = VMFactoryConfigUtil.getBaseProductLocation() + yourProductType + File.separator + productVersion +File.separator; 
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
    InputStream stream = new FileInputStream(fileUpload);

    // write the file to the file specified
    OutputStream bos = new FileOutputStream(uploadDir + fileUploadFileName);
    int bytesRead;
    byte[] buffer = new byte[8192];

    while ((bytesRead = stream.read(buffer, 0, 8192)) != -1)
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

//    getRequest().setAttribute("location", dirPath.getAbsolutePath() + System.getProperty("file.separator") + fileUploadFileName);
//
//    String link = getRequest().getContextPath() + "/resources" + "/";
//
//    getRequest().setAttribute("link", link + fileUploadFileName);
    logger.info("upload is successful!");
    return SUCCESS;

  }

  public String display()
  {  
    productTypeList = ps.getSupportedProductType();
    
    supportOsList = os.getOsListFromDB();
    for (Os o:supportOsList){
      supportOsNameList.add(o.getName());
    }
    
    return NONE;
  }
}
