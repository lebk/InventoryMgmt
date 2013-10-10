package com.leikai.dao;

import java.util.List;

import com.leikai.po.Job;
import com.leikai.po.Jobprogress;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public interface JobDao
{
  public Integer startJob(Integer osId, Integer poId, Boolean isSettingProduct, Boolean isProdActivate, Integer userId);

  public List<Job> getJobsByUser(Integer userId);

  public List<Job> getAllCompleteJobs();

  public List<Job> getAllJobs();

  public String getCurrentJobStatus(Integer jobId);

  public List<Jobprogress> getJobStatusList(Integer jobId);

  public boolean isJobOwner(Integer jobId, Integer userId);

  public boolean stopJob(Integer jobId, Integer userId);

  public boolean isJobRequestToStop(Integer jobId);

  public boolean isSettingProduct(Integer jobId);

  public boolean isActivatingProduct(Integer jobId);

  public boolean isVMExisted(Integer osId, Integer poId);

  public boolean isVMCreating(Integer osId, Integer poId);

  public boolean updateJob(Integer jobId, Integer jobStatusId);

  public boolean updateJobProgressEndTime(Integer jobId, Integer jobStatusId);

  public Job getJobByJobId(Integer jobId);

  public List<Job> getJobByOsIdAndPoId(Integer osId, Integer poId);

  public boolean updateJobTargetVMLocation(Integer jobId, String targetVMLocation);

  public List<Job> getJobByPage(Integer pageSize, Integer pageNow);
}
