package com.leikai.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.JobDao;
import com.leikai.enumType.JobStatusEnumType;
import com.leikai.po.Job;
import com.leikai.po.Jobprogress;
import com.leikai.po.Jobstatus;
import com.leikai.po.Location;
import com.leikai.po.Os;
import com.leikai.util.HibernateUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class JobDaoImpl implements JobDao
{
  static Logger logger = Logger.getLogger(JobDaoImpl.class);

  public Integer startJob(Integer osId, Integer poId, Boolean isProductSetting, Boolean isProdActivate, Integer userId)
  {

    Integer jobId = this.addJob(osId, poId, isProductSetting, isProdActivate, userId);
    if (jobId == null)
    {
      logger.error("The job record is  not added correctly return null");
      return null;
    }
    boolean insertStatus = insertAJobProgressRecord(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.start));
    if (insertStatus == false)
    {
      logger.error("The jobpogress record is  not inserted correctly return null");
      return null;
    }
    return jobId;

  }

  private Integer addJob(Integer osId, Integer poId, Boolean isProductSetting, Boolean isProdActivate, Integer userId)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {

      transaction = session.beginTransaction();
      Job j = new Job();
      j.setOsId(osId);
      j.setProdId(poId);
      j.setStartTime(new Date());
      j.setUserId(userId);
      j.setJobStatusId(JobStatusEnumType.getJobstatusId(JobStatusEnumType.start));
      j.setIsProdSetting(isProductSetting);
      j.setIsProdActivate(isProdActivate);
      session.save(j);
      transaction.commit();
      logger.info("the job id is: " + j.getId());
      return j.getId();
    } catch (HibernateException e)
    {

      transaction.rollback();
      logger.error(e.toString());
      e.printStackTrace();

    } finally
    {

      session.close();

    }
    logger.error("The job is not insert correctly");
    return null;
  }

  public List<Job> getJobsByUser(Integer userId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List jl = new ArrayList<Job>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where userId='" + userId + "'").list();
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        logger.info("Job: " + j.getId());

        jl.add(j);

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return jl;
  }

  public List<Job> getAllCompleteJobs()
  {
    List<Job> completedJobList = new ArrayList<Job>();
    List<Job> jl = this.getAllJobs();

    for (Job j : jl)
    {
      logger.info("getAllCompleteJobs, job: " + j.getId());
      if (getJobStatusByStatusId(j.getJobStatusId()).equalsIgnoreCase(JobStatusEnumType.complete))
      {
        logger.info("complete job id: " + j.getId());
        completedJobList.add(j);
      }
    }

    return completedJobList;
  }

  public List<Job> getAllJobs()
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List jl = new ArrayList<Job>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job").list();
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        //logger.info("Job: " + j.getId());

        jl.add(j);

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return jl;
  }

  public List<Job> getJobByPage(Integer pageSize, Integer pageNow)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List jl = new ArrayList<Job>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      String sqlString = "from Job order by id desc";
      final Query query = session.createQuery(sqlString);
      query.setMaxResults(pageSize);
      query.setFirstResult(pageNow*pageSize-pageSize);
      
      List ql = query.list();
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        logger.info("Job id by page: " + j.getId());

        jl.add(j);

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return jl;
  }
  
  public String getCurrentJobStatus(Integer jobId)
  {
    Job j = this.getJobByJobId(jobId);
    return getJobStatusByStatusId(j.getJobStatusId());

  }

  private String getJobStatusByStatusId(Integer statusId)

  {
    if (statusId == null)
    {
      logger.error("The queried status ID is null, return null");
      return null;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Jobstatus where id='" + statusId + "'").list();
      if (ql.size() == 0 || ql.size() > 2)
      {
        logger.error("The quried status id: " + statusId + "does not contain valid data, return null");
        return null;
      }
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Jobstatus js = (Jobstatus) it.next();
        logger.info("Job: " + js.getId());

        return js.getName();

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return null;
  }

  public boolean isJobOwner(Integer jobId, Integer userId)
  {
    List<Job> jl = this.getJobsByUser(userId);
    for (Job j : jl)
    {
      if (j.getId().equals(jobId))
      {
        logger.info("The job, id:" + jobId + ", is owned by user, id:" + userId);
        return true;
      }
    }
    return false;
  }

  public boolean stopJob(Integer jobId, Integer userId)
  {

    if (jobId == null)
    {
      logger.error("no job found by the specifed jobId: " + jobId);
      return false;
    }
    boolean updateFlag = updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.requestToStop));
    if (updateFlag == false)
    {
      logger.error("fail to update the job status, return");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Job j = (Job) session.get(Job.class, jobId);
      j.setIsRequestToStop(true);
      j.setStopBy(userId);
      transaction.commit();
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return false;
  }

  public boolean isJobRequestToStop(Integer jobId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where id='" + jobId + "'").list();
      if (ql.size() == 0 || ql.size() >= 2)
      {
        logger.error("The quried job id: " + jobId + "does not contain valid data, return null");
        return false;
      }
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        logger.info("Job: " + j.getId());

        return j.isIsRequestToStop();

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    logger.error("unexpeted erro happen while quries the isJobRequestStatus");
    return false;

  }

  public boolean isVMExisted(Integer osId, Integer poId)
  {

    Integer completeJsId = JobStatusEnumType.getJobstatusId(JobStatusEnumType.complete);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where osId='" + osId + "' and prodId='" + poId + "' and jobStatusId='" + completeJsId + "'").list();
      transaction.commit();
      if (ql.size() >= 1)
      {
        logger.info("There is one job with osId:" + osId + ", prodId=" + poId + " existed with:" + JobStatusEnumType.complete);
        return true;
      } else
      {
        return false;
      }

    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    logger.error("unexpeted erro happen");
    return false;
  }

  public boolean isVMCreating(Integer osId, Integer poId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where osId='" + osId + "' and prodId='" + poId + "' and isRequestToStop='false'").list();

      if (ql.size() == 0)
      {
        return false;
      } else
      {
        for (Iterator it = ql.iterator(); it.hasNext();)
        {
          Job j = (Job) it.next();
          String jobStatus = getJobStatusByStatusId(j.getJobStatusId());
          logger.info("Job, id=" + j.getId() + ", the jobstatus  is: " + jobStatus);
          if (JobStatusEnumType.getRunningStatusList().contains(jobStatus))
          {
            return true;
          }

        }
      }

      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    logger.error("unexpeted erro happen");
    return false;
  }

  private boolean updateJobStatus(Integer jobId, Integer jobStatusId)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Job j = (Job) session.get(Job.class, jobId);
      j.setJobStatusId(jobStatusId);
      transaction.commit();
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return false;
  }

  public boolean updateJob(Integer jobId, Integer jobStatusId)
  {
    boolean updateStatus = false;
    updateStatus = this.updateJobStatus(jobId, jobStatusId);
    if (updateStatus == false)
    {
      logger.error("udpdate the job table failed return");
      return false;
    }
    updateStatus = this.insertAJobProgressRecord(jobId, jobStatusId);
    if (updateStatus == false)
    {
      logger.error("insert the jobprogress table failed return");
      return false;
    }
    return updateStatus;
  }

  private boolean insertAJobProgressRecord(Integer jobId, Integer jobStatusId)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {

      transaction = session.beginTransaction();
      Jobprogress jp = new Jobprogress();
      jp.setJobId(jobId);
      jp.setJobStatusId(jobStatusId);
      jp.setStartTime(new Date());
      session.save(jp);
      transaction.commit();
      logger.info("the jobprogress is: " + jp.getId());
      return true;
    } catch (HibernateException e)
    {

      transaction.rollback();
      logger.error(e.toString());
      e.printStackTrace();

    } finally
    {

      session.close();

    }
    logger.error("The jobporgress is not insert correctly");
    return false;
  }

  public Job getJobByJobId(Integer jobId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where id='" + jobId + "'").list();
      if (ql.size() == 0 || ql.size() > 2)
      {
        logger.error("The quried job id: " + jobId + "does not contain valid data, return null");
        return null;
      }
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        logger.info("Job: " + j.getId());

        return j;

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return null;
  }

  private Integer getJobProgressIdByJobIdAndJobStatusId(Integer jobId, Integer jobStatusId)
  {

    if (jobId == null || jobStatusId == null)
    {
      logger.error("The queried jobStatusId or jobId is null, return null");
      logger.error("jobId= " + jobId);
      logger.error("jobStatusId= " + jobStatusId);

      return null;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Jobprogress where jobId='" + jobId + "' and jobStatusId='" + jobStatusId + "'").list();
      if (ql.size() == 0 || ql.size() > 1)
      {
        logger.error("The quried ressult does not contain valid data, it should contain just one list, but:" + ql.size() + ", return null,");
        return null;
      }
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Jobprogress jp = (Jobprogress) it.next();
        logger.info("Jobprogress id: " + jp.getId());

        return jp.getId();

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return null;
  }

  public boolean updateJobProgressEndTime(Integer jobId, Integer jobStatusId)
  {
    Integer jobProgressId = this.getJobProgressIdByJobIdAndJobStatusId(jobId, jobStatusId);
    if (jobProgressId == null)
    {
      logger.error("no valid jobgrogress id found return false");
      return false;

    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Jobprogress jp = (Jobprogress) session.get(Jobprogress.class, jobProgressId);
      jp.setEndTime(new Date());
      transaction.commit();
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return false;
  }

  public List<Jobprogress> getJobStatusList(Integer jobId)
  {

    Session session = HibernateUtil.getSessionFactory().openSession();
    List jl = new ArrayList<Jobprogress>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Jobprogress where jobId='" + jobId + "' order by id ASC").list();
      logger.info("the size is: " + ql.size());
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Jobprogress jp = (Jobprogress) it.next();

        logger.info("Jobprogress, id:" + jp.getId() + ", jobId: " + jobId);

        jl.add(jp);

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return jl;

  }

  public List<Job> getJobByOsIdAndPoId(Integer osId, Integer poId)
  {
    List<Job> jl = new ArrayList<Job>();
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where osId='" + osId + "' and prodId='" + poId + "'").list();

      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        logger.info("Job id: " + j.getId());

        jl.add(j);

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return jl;
  }

  public boolean updateJobTargetVMLocation(Integer jobId, String targetVMLocation)
  {
    if (jobId == null || targetVMLocation == null)
    {
      logger.error("Neither JobId:" + jobId + "or targetVMLocation:" + targetVMLocation + " could be null");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Job j = (Job) session.get(Job.class, jobId);
      j.setTargetVmlocation(targetVMLocation);
      transaction.commit();
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return false;
  }

  public boolean isSettingProduct(Integer jobId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where id='" + jobId + "'").list();
      if (ql.size() == 0 || ql.size() >= 2)
      {
        logger.error("The quried job id: " + jobId + "does not contain valid data, return null");
        return false;
      }
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        logger.info("Job: " + j.getId());

        return j.isIsProdSetting();

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    logger.error("unexpeted erro happen while quries the isSettingProduct");
    return false;
  }

  public boolean isActivatingProduct(Integer jobId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Job where id='" + jobId + "'").list();
      if (ql.size() == 0 || ql.size() >= 2)
      {
        logger.error("The quried job id: " + jobId + "does not contain valid data, return null");
        return false;
      }
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Job j = (Job) it.next();
        logger.info("Job: " + j.getId());

        return j.isIsProdActivate();

      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    logger.error("unexpeted erro happen while quries the isActivatingProduct");
    return false;
  }

}
