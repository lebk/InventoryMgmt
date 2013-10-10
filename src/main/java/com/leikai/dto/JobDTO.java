package com.leikai.dto;

import java.util.Date;

import com.leikai.dao.JobDao;
import com.leikai.dao.OsDao;
import com.leikai.dao.ProductDao;
import com.leikai.dao.UserDao;
import com.leikai.dao.impl.JobDaoImpl;
import com.leikai.dao.impl.OsDaoImpl;
import com.leikai.dao.impl.ProductDaoImpl;
import com.leikai.dao.impl.UserDaoImpl;
import com.leikai.po.Job;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class JobDTO
{
  private Integer id;
  private String osName;
  private String prodName;
  private String jobStatus;
  private String user;
  private String targetVmlocation;
  private Date startTime;
  private Date completeTime;
  private Date errorTime;
  private boolean isRequestToStop;
  private boolean isProdSetting;
  private String stopBy;
  private Date stopStartTime;
  private Date stopCompleteTime;
  private Date stopErrorTime;
  private OsDao od = new OsDaoImpl();
  private ProductDao pd = new ProductDaoImpl();
  private JobDao jd = new JobDaoImpl();
  private UserDao ud = new UserDaoImpl();

  public JobDTO()
  {
  }

  public JobDTO(Job job)
  {
    id = job.getId();
    osName = od.getOsNameById(job.getOsId());
    prodName = pd.getNameByProductId(job.getProdId());
    jobStatus = jd.getCurrentJobStatus(job.getId());
    user = ud.getUsernamebyUserid(job.getUserId());
    targetVmlocation = job.getTargetVmlocation();
    startTime = job.getStartTime();
    completeTime = job.getCompleteTime();
    errorTime = job.getErrorTime();
    isRequestToStop = job.isIsRequestToStop();
    isProdSetting = job.isIsProdSetting();
    stopBy = ud.getUsernamebyUserid(job.getStopBy());
    stopStartTime = job.getStopStartTime();
    stopCompleteTime = job.getStopCompleteTime();
    stopErrorTime = job.getStopErrorTime();

  }

  public JobDTO(String osName, String prodName, String jobStatus, String user, boolean isRequestToStop, boolean isProdSetting)
  {
    this.osName = osName;
    this.prodName = prodName;
    this.jobStatus = jobStatus;
    this.user = user;
    this.isRequestToStop = isRequestToStop;
    this.isProdSetting = isProdSetting;
  }

  public JobDTO(String osName, String prodName, String jobStatus, String user, String targetVmlocation, Date startTime, Date completeTime, Date errorTime,
      boolean isRequestToStop, boolean isProdSetting, String stopBy, Date stopStartTime, Date stopCompleteTime, Date stopErrorTime)
  {
    this.osName = osName;
    this.prodName = prodName;
    this.jobStatus = jobStatus;
    this.user = user;
    this.targetVmlocation = targetVmlocation;
    this.startTime = startTime;
    this.completeTime = completeTime;
    this.errorTime = errorTime;
    this.isRequestToStop = isRequestToStop;
    this.isProdSetting = isProdSetting;
    this.stopBy = stopBy;
    this.stopStartTime = stopStartTime;
    this.stopCompleteTime = stopCompleteTime;
    this.stopErrorTime = stopErrorTime;
  }

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getOsName()
  {
    return osName;
  }

  public void setOsName(String osName)
  {
    this.osName = osName;
  }

  public String getProdName()
  {

    return prodName;
  }

  public void setProdName(String prodName)
  {
    this.prodName = prodName;
  }

  public String getJobStatus()
  {
    return jobStatus;
  }

  public void setJobStatus(String jobStatus)
  {
    this.jobStatus = jobStatus;
  }

  public String getUser()
  {
    return user;
  }

  public void setUser(String user)
  {
    this.user = user;
  }

  public String getTargetVmlocation()
  {
    return this.targetVmlocation;
  }

  public void setTargetVmlocation(String targetVmlocation)
  {
    this.targetVmlocation = targetVmlocation;
  }

  public Date getStartTime()
  {
    return this.startTime;
  }

  public void setStartTime(Date startTime)
  {
    this.startTime = startTime;
  }

  public Date getCompleteTime()
  {
    return this.completeTime;
  }

  public void setCompleteTime(Date completeTime)
  {
    this.completeTime = completeTime;
  }

  public Date getErrorTime()
  {
    return this.errorTime;
  }

  public void setErrorTime(Date errorTime)
  {
    this.errorTime = errorTime;
  }

  public boolean isIsRequestToStop()
  {
    return this.isRequestToStop;
  }

  public void setIsRequestToStop(boolean isRequestToStop)
  {
    this.isRequestToStop = isRequestToStop;
  }

  public String getStopBy()
  {
    return this.stopBy;
  }

  public boolean isIsProdSetting()
  {
    return this.isProdSetting;
  }

  public void setIsProdSetting(boolean isProdSetting)
  {
    this.isProdSetting = isProdSetting;
  }

  public void setStopBy(String stopBy)
  {
    this.stopBy = stopBy;
  }

  public Date getStopStartTime()
  {
    return this.stopStartTime;
  }

  public void setStopStartTime(Date stopStartTime)
  {
    this.stopStartTime = stopStartTime;
  }

  public Date getStopCompleteTime()
  {
    return this.stopCompleteTime;
  }

  public void setStopCompleteTime(Date stopCompleteTime)
  {
    this.stopCompleteTime = stopCompleteTime;
  }

  public Date getStopErrorTime()
  {
    return this.stopErrorTime;
  }

  public void setStopErrorTime(Date stopErrorTime)
  {
    this.stopErrorTime = stopErrorTime;
  }
}
