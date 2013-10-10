package com.leikai.upload;

import uk.ltd.getahead.dwr.WebContextFactory;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.leikai.upload.UploadInfo;

public class UploadMonitor
{
  static Logger logger = Logger.getLogger(UploadMonitor.class);

  public UploadInfo getUploadInfo()
  {
    logger.info("hello, calling me?");
    
    HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();

    if ((req.getSession().getAttribute("uploadInfo"))!= null) 
      return (UploadInfo) (req.getSession().getAttribute("uploadInfo"));
    
    else  
      return new UploadInfo();  
  }
}
