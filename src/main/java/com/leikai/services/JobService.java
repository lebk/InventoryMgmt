package com.leikai.services;

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
public interface JobService
{
  /**
   * 
   * @return
   * 
   * 
   *         All Jobs will returned by this method, including failed one.
   */
  public List<Job> getAllJobs();
  
  /**
   * 
   * @return
   * 
   * 
   *         Jobs will be returned according to the page.
   */
  public List<Job> getJobByPage(Integer pageSize, Integer pageNow);
  /**
   * @return
   * 
   *         All the completed jobs will be return, this method could be used to
   *         display all the available VMs.
   */

  public List<Job> getAllCompleteJobs();

  /**
   * @param poId
   *          the product id)
   * @param osId
   *          (the os id)
   * @param isProdSetting
   *          TODO
   * @param isProdActivate
   *          TODO
   * @param userId
   *          (user id, who will start the job
   * @return
   * 
   *         Before start a job, need to check whether a completed with the
   *         specified product and os.f
   */
  public Integer startJob(Integer poId, Integer osId, Boolean isProdSetting, Boolean isProdActivate, Integer userId);

  /**
   * @param poId
   *          (prod id)
   * @param osId
   *          (os id)
   * @param isProdSetting
   *          TODO
   * @param isProdActivate
   *          TODO
   * @param override
   *          (only admin can call this method with override set to true.
   * @param userId
   *          (the user who will start a job)
   * @return
   * 
   *         Before start a job, need to check whether a completed VM existed
   *         with the specified product and os. If override is set to true (and
   *         the user is "Admin"), then it will allow to override the existed VM
   */
  public Integer startJob(Integer poId, Integer osId, Boolean isProdSetting, Boolean isProdActivate, boolean override, Integer userId);

  /**
   * 
   * @param jobId
   * @param userId
   *          TODO
   * @return
   * 
   *         Stop a job, only admin user or the user who create the job can stop
   *         a job.
   */

  public boolean stopJob(Integer jobId, Integer userId);

  /**
   * 
   * @param jobId
   * @param userId
   * @return
   * 
   *         * Get the job's status by the specified job id. only admin or the
   *         job's owner can call this method.
   */

  public String getCurrentJobStatus(Integer jobId, Integer userId);

  /**
   * 
   * @param jobId
   * @return
   * 
   *         This method is used to retrieve all the job status this job has
   *         undergoing.
   */
  public List<Jobprogress> getJobStatusList(Integer jobId);

  /**
   * 
   * @param userId
   * @return
   * 
   *         Get all the jobs by the specified user, if the specified user is an
   *         admin, then all the jobs will returned, otherwise, only the owned
   *         job will be returned.
   * 
   */
  public List<Job> getJobsByUser(Integer userId, Integer pageSize, Integer pageNow);

  /**
   * 
   * @param osId
   * @param poId
   * @return
   * 
   *         Check whether an VM is existed or not (job with "complete" and
   *         "running" status) how about, there already one job with the OS and
   *         Product is in "running" status?
   */

  public boolean isVMExisted(Integer osId, Integer poId);

  /**
   * 
   * @param osId
   * @param poId
   * @return
   * 
   *         This VM is created, it is in "running" status, not finished yet.
   */

  public boolean isVMCreating(Integer osId, Integer poId);

  /**
   * 
   * @param osId
   * @param poId
   * @return
   * 
   *         Get the job by poId and osId, all user can call this method
   * 
   */

  public Job getJobByOsIdAndPoId(Integer osId, Integer poId);

  /**
   * 
   * @return Check whether reach the max allowed running jobs
   */
  public boolean isMaxAllowedRunningJobsReached();

  /**
   * 
   * @param jobId
   * @return
   */
  public boolean isJobRequestToStop(Integer jobId);

  /**
   * 
   * @param jobId
   * @return
   * 
   *         This method is used to check, whether the user will config the
   *         product or not after install
   */
  public boolean isSettingProduct(Integer jobId);

  /**
   * 
   * @param jobId
   * @return
   * 
   *         This method is used to check whether activate the product or not;
   */
  public boolean isActivatingProduct(Integer jobId);

  /**
   * 
   * @param jobId
   * @return the job object by Job ID
   * 
   * 
   */
  public Job getJobByJobId(Integer jobId, Integer userId);

  /**
   * 
   * @param jobId
   * @param userId
   * @return
   * 
   *         This method is used to check whether allow to stop the job.
   */
  public boolean isAllowedToStop(Integer jobId, Integer userId);

  /**
   * 
   * @return
   * 
   *         This method is use to test whether the connection to datacenter is
   *         ok or not.
   */
  public boolean isVCenterConnectionOk();
}
