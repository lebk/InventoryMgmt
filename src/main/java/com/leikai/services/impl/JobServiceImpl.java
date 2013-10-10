package com.leikai.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.leikai.dao.JobDao;
import com.leikai.dao.impl.JobDaoImpl;
import com.leikai.enumType.JobStatusEnumType;
import com.leikai.po.Job;
import com.leikai.po.Jobprogress;
import com.leikai.services.JobService;
import com.leikai.services.ProductService;
import com.leikai.services.UserService;
import com.leikai.util.RunJob;
import com.leikai.util.VMFactoryConfigUtil;
import com.leikai.util.VMHelper;
import com.vmware.vim25.mo.ServiceInstance;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class JobServiceImpl implements JobService
{
  static Logger logger = Logger.getLogger(JobServiceImpl.class);
  UserService us = new UserServiceImpl();
  JobDao jd = new JobDaoImpl();
  ProductService ps = new ProductServiceImpl();

  /*
   * All the jobs will be returned
   */
  public List<Job> getAllJobs()
  {

    return jd.getAllJobs();
  }

  /*
   * Jobs will be returned according to the page.
   */
  public List<Job> getJobByPage(Integer pageSize, Integer pageNow)
  {
    return jd.getJobByPage(pageSize, pageNow);
  }
  
  /*
   * All the completed jobs will be return, this method could be used to display
   * all the avaialble VMs.
   */

  public List<Job> getAllCompleteJobs()
  {
    List li = new ArrayList<Job>();

    return jd.getAllCompleteJobs();
  }

  /*
   * Before start a job, need to check whether a completed with the specified
   * product and os.f
   */
  public Integer startJob(Integer poId, Integer osId, Boolean isProdSetting, Boolean isProdActivate, Integer userId)
  {
    if (poId == null || osId == null || userId == null)
    {
      logger.error("the passsed in poId:" + poId + "or osId:" + osId + "OR userId:" + userId + " is null!");
      return null;
    }
    if (!isVCenterConnectionOk())
    {
      logger.error("connection with vCenter is failed");
      return null;
    }
    if (this.isVMCreating(osId, poId) || this.isVMExisted(osId, poId))
    {
      logger.warn("there are VM is in creating status or existed with osId=" + osId + ", poId=" + poId);
      return null;
    }
    if (!ps.isProductSupportedToInstallOnOs(poId, osId))

    {
      logger.error("the product id: " + poId + " is not able to install in os Id:" + osId);
      return null;
    }
    return this.startJob(poId, osId, isProdSetting, isProdActivate, false, userId);
  }

  /*
   * Before start a job, need to check whether a completed VM existed with the
   * specified product and os. If override is set to true (and the user is
   * "Admin"), then it will allow to override the existed VM
   */
  public Integer startJob(Integer poId, Integer osId, Boolean isProdSetting, Boolean isProdActivate, boolean override, Integer userId)
  {
    if (us.isUserAdmin(userId) == true)
    {
      // admin user

      if (override == false)
      {
        if (this.isVMCreating(osId, poId) || this.isVMExisted(osId, poId))
        {
          logger.warn("There are VM with the poId=" + poId + ", osId=" + osId + "existed or creating, return null");
          return null;
        }
        return this.run(osId, poId, isProdSetting, isProdActivate, userId);

      } else
      {
        if (this.isVMCreating(osId, poId))
        {
          logger.warn("should not allow user to override a VM which is in creating status, even administrator!");
          return null;
        }

        if (this.isVMExisted(osId, poId))
        {
          logger.warn("administrator is going to override an existed VM!!!");
          return this.run(osId, poId, isProdSetting, isProdActivate, userId);
        }
        return this.run(osId, poId, isProdSetting, isProdActivate, userId);
      }

    } else
    {
      // normal user
      if (override == true)
      {
        logger.error("only admin user can override!");
        return null;
      } else
      {
        if (this.isVMCreating(osId, poId) || this.isVMExisted(osId, poId))
        {
          logger.warn("some image is already eixsted or creating with osId=" + osId + ", poId=" + poId);
          return null;
        } else
        {
          return run(osId, poId, isProdSetting, false, userId);
        }
      }

    }
  }

  /*
   * Stop a job, only admin user or the user who create the job can stop a job.
   */
  public boolean stopJob(Integer jobId, Integer userId)
  {
    if (this.isAllowedToStop(jobId, userId) == false)
    {
      logger.error("This job is not allowed to stop!, return false");
      return false;
    }
    return this.stop(jobId, userId);
  }

  /*
   * Get the job's status by the specified job id.
   */

  public String getCurrentJobStatus(Integer jobId, Integer userId)
  {
    if (us.isUserAdmin(userId) || this.isJobOwner(jobId, userId))
    {
      return jd.getCurrentJobStatus(jobId);
    }
    logger.error("Only admin user or job owner can call this method, return null");
    return null;
  }

  /*
   * Get all the jobs by the specified user, if the specified user is an admin,
   * then all the jobs will returned, otherwise, only the owned job will be
   * returned.
   */
  public List<Job> getJobsByUser(Integer userId, Integer pageSize, Integer pageNow)
  {

    if (us.isUserAdmin(userId))
    {
      return jd.getJobByPage(pageSize,pageNow);
    }

    return jd.getJobsByUser(userId);
  }

  /*
   * VM is existed.
   */
  public boolean isVMExisted(Integer osId, Integer poId)
  {
    return jd.isVMExisted(osId, poId);
  }

  public boolean isVMCreating(Integer osId, Integer poId)
  {
    return jd.isVMCreating(osId, poId);
  }

  public boolean isJobRequestToStop(Integer jobId)
  {

    return jd.isJobRequestToStop(jobId);

  }

  public boolean isAllowedToStop(Integer jobId, Integer userId)
  {
    // Only admin or job owner is allowed to stop

    if (us.isUserAdmin(userId) || this.isJobOwner(jobId, userId))
    {
      // Only the following status and the job is not request to stop before can
      // allow to stop
      if (JobStatusEnumType.getValidStatusToBeStop().contains(this.getCurrentJobStatus(jobId, userId)) && this.isJobRequestToStop(jobId) == false)
      {
        return true;
      }
    }
    return false;
  }

  private boolean isJobOwner(Integer jobId, Integer userId)
  {
    return jd.isJobOwner(jobId, userId);
  }

  // This is the core method to create the image, delegate to RunJob.run()
  private Integer run(Integer osId, Integer poId, boolean isSettingProduct, Boolean isProdActivate, Integer userId)
  {

    if (isMaxAllowedRunningJobsReached() == true)
    {
      logger.error("max allowed running jobs reached, return null");
      return null;
    }

    JobDao jd = new JobDaoImpl();
    // Insert record in VMFactory.Job and VMFactory.jobProgess table
    // The actually job is done by RunJob
    Integer jobId = jd.startJob(osId, poId, isSettingProduct, isProdActivate, userId);

    RunJob rj = new RunJob(jobId, userId);

    new Thread(rj).start();

    return jobId;

  }

  // This is the core method to stop the image creating
  private boolean stop(Integer jobId, Integer userId)
  {
    // For stop job, we just mark the job's status (isRequestToStop to "Y", when
    // the
    // "running" job find the that status is "Y", it will stop the
    // current job and roll back.

    return jd.stopJob(jobId, userId);
  }

  public boolean isMaxAllowedRunningJobsReached()
  {
    return RunJob.isMaxAllowedRunningJobsReached();
  }

  public Job getJobByJobId(Integer jobId, Integer userId)
  {

    if (us.isUserAdmin(userId) || this.isJobOwner(jobId, userId))
    {
      return jd.getJobByJobId(jobId);
    }
    logger.error("Only admin or job owner can call this method,return null");
    return null;

  }

  public List<Jobprogress> getJobStatusList(Integer jobId)
  {

    List<Jobprogress> jpl = new ArrayList<Jobprogress>();
    if (jobId == null)
    {
      logger.error("the queried jobId is null, return");
      return jpl;
    }

    return jd.getJobStatusList(jobId);

  }

  public Job getJobByOsIdAndPoId(Integer osId, Integer poId)
  {
    if (osId == null | poId == null)
    {
      logger.error("osId or poId could not be null: osId: " + osId + ", poId=" + poId);
    }
    List<Job> jl = jd.getJobByOsIdAndPoId(osId, poId);
    if (jl == null | jl.size() == 0)
    {
      logger.warn("there is no job found by the specific osId or poId");
      return null;
    }
    List<Job> runningOrCompleteJob = new ArrayList<Job>();
    for (Job j : jl)
    {
      String jobStatus = JobStatusEnumType.getJobStatusbyStatusId(j.getJobStatusId());
      logger.info("job, id: " + j.getId() + ", the job status is: " + jobStatus);

      if (JobStatusEnumType.getRunningStatusList().contains(jobStatus) || JobStatusEnumType.complete.equals(jobStatus))
      {
        logger.info("Gotta one running/complete job!job, id: " + j.getId() + ", the job status is: " + jobStatus);
        runningOrCompleteJob.add(j);
      }
    }
    if (runningOrCompleteJob.size() == 0)
    {
      logger.warn("there is no running/complete job found by the specific osId or poId");
      return null;
    }
    if (runningOrCompleteJob.size() > 1)
    {
      logger.error("There are bugs in the codes, as only allowed at most one Job(complete or \"running \")  existed with the same os Id and product Id");
      return null;
    }

    return runningOrCompleteJob.get(0);
  }

  public boolean isSettingProduct(Integer jobId)
  {
    if (jobId == null)
    {
      logger.error("The queried jobId is null");
      return false;
    }
    return jd.isSettingProduct(jobId);
  }

  public boolean isActivatingProduct(Integer jobId)
  {
    if (jobId == null)
    {
      logger.error("The queried jobId is null");
      return false;
    }
    return jd.isActivatingProduct(jobId);
  }

  public boolean isVCenterConnectionOk()
  {
    ServiceInstance si = VMHelper.getServiceInstance();

    if (si == null)
    {

      logger.error("fail to connect to the datacenter:" + VMFactoryConfigUtil.getvSphereHttpSdkUrl());

      return false;
    } else
    {

      si.getServerConnection().logout();
      logger.info("success to connect to the datacenter:" + VMFactoryConfigUtil.getvSphereHttpSdkUrl());

      return true;
    }
  }
}
