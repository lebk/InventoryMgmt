package com.leikai.dto;

import java.util.Date;

import com.leikai.enumType.JobStatusEnumType;
import com.leikai.po.Jobprogress;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class JobprogressDTO implements java.io.Serializable
{

  private Integer id;
  private int jobId;
  private String jobStatus;
  private Date startTime;
  private Date endTime;

  public JobprogressDTO()
  {
  }

  public JobprogressDTO(Jobprogress jp)
  {
    this.id = jp.getId();
    this.jobId = jp.getJobId();
    this.jobStatus = JobStatusEnumType.getJobStatusbyStatusId(jp.getJobStatusId());
    this.startTime = jp.getStartTime();
    this.endTime = jp.getEndTime();
  }

  public JobprogressDTO(int jobId, String jobStatus, Date createTime)
  {
    this.jobId = jobId;
    this.jobStatus = jobStatus;
    this.startTime = createTime;
  }

  public JobprogressDTO(int jobId, String jobStatus, Date createTime, Date endTime)
  {
    this.jobId = jobId;
    this.jobStatus = jobStatus;
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

  public String getJobStatus()
  {
    return this.jobStatus;
  }

  public void setJobStatus(String jobStatus)
  {
    this.jobStatus = jobStatus;
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
