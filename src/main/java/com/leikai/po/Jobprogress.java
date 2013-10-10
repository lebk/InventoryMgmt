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
public class Jobprogress implements java.io.Serializable
{

  private Integer id;
  private int jobId;
  private int jobStatusId;
  private Date startTime;
  private Date endTime;

  public Jobprogress()
  {
  }

  public Jobprogress(int jobId, int jobStatusId, Date createTime)
  {
    this.jobId = jobId;
    this.jobStatusId = jobStatusId;
    this.startTime = createTime;
  }

  public Jobprogress(int jobId, int jobStatusId, Date createTime, Date endTime)
  {
    this.jobId = jobId;
    this.jobStatusId = jobStatusId;
    this.startTime = createTime;
    this.endTime = endTime;
  }

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public int getJobId()
  {
    return this.jobId;
  }

  public void setJobId(int jobId)
  {
    this.jobId = jobId;
  }

  public int getJobStatusId()
  {
    return this.jobStatusId;
  }

  public void setJobStatusId(int jobStatusId)
  {
    this.jobStatusId = jobStatusId;
  }

  public Date getStartTime()
  {
    return this.startTime;
  }

  public void setStartTime(Date startTime)
  {
    this.startTime = startTime;
  }

  public Date getEndTime()
  {
    return this.endTime;
  }

  public void setEndTime(Date endTime)
  {
    this.endTime = endTime;
  }

}
