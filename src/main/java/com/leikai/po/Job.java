package com.leikai.po;

import java.util.Date;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class Job implements java.io.Serializable
{

  private Integer id;
  private int osId;
  private int prodId;
  private int jobStatusId;
  private int userId;
  private String targetVmlocation;
  private Date startTime;
  private Date completeTime;
  private Date errorTime;
  private boolean isRequestToStop;
  private boolean isProdSetting;
  private boolean isProdActivate;
  private Integer stopBy;
  private Date stopStartTime;
  private Date stopCompleteTime;
  private Date stopErrorTime;

  public Job()
  {
  }

  public Job(int osId, int prodId, int jobStatusId, int userId, boolean isRequestToStop, boolean isProdActivate, boolean isProdSetting)
  {
    this.osId = osId;
    this.prodId = prodId;
    this.jobStatusId = jobStatusId;
    this.userId = userId;
    this.isRequestToStop = isRequestToStop;
    this.isProdActivate = isProdActivate;
    this.isRequestToStop = isProdSetting;
  }

  public Job(int osId, int prodId, int jobStatusId, int userId, String targetVmlocation, Date startTime, Date completeTime, Date errorTime,
      boolean isRequestToStop, boolean isProdSetting, boolean isProdActivate, Integer stopBy, Date stopStartTime, Date stopCompleteTime, Date stopErrorTime)
  {
    this.osId = osId;
    this.prodId = prodId;
    this.jobStatusId = jobStatusId;
    this.userId = userId;
    this.targetVmlocation = targetVmlocation;
    this.startTime = startTime;
    this.completeTime = completeTime;
    this.errorTime = errorTime;
    this.isRequestToStop = isRequestToStop;
    this.isProdSetting = isProdSetting;
    this.isProdActivate = isProdActivate;
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

  public int getOsId()
  {
    return this.osId;
  }

  public void setOsId(int osId)
  {
    this.osId = osId;
  }

  public int getProdId()
  {
    return this.prodId;
  }

  public void setProdId(int prodId)
  {
    this.prodId = prodId;
  }

  public int getJobStatusId()
  {
    return this.jobStatusId;
  }

  public void setJobStatusId(int jobStatusId)
  {
    this.jobStatusId = jobStatusId;
  }

  public int getUserId()
  {
    return this.userId;
  }

  public void setUserId(int userId)
  {
    this.userId = userId;
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

  public boolean isIsProdSetting()
  {
    return this.isProdSetting;
  }

  public void setIsProdSetting(boolean isProdSetting)
  {
    this.isProdSetting = isProdSetting;
  }

  public boolean isIsProdActivate()
  {
    return this.isProdActivate;
  }

  public void setIsProdActivate(boolean isProdActivate)
  {
    this.isProdActivate = isProdActivate;
  }

  public Integer getStopBy()
  {
    return this.stopBy;
  }

  public void setStopBy(Integer stopBy)
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
