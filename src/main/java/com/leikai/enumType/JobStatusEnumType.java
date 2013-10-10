package com.leikai.enumType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.po.Jobstatus;
import com.leikai.po.Usertype;
import com.leikai.util.HibernateUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class JobStatusEnumType
{
  static Logger logger = Logger.getLogger(JobStatusEnumType.class);

  final public static String start = "Start";
  final public static String copyVM = "CloneVM";
  final public static String startupVM = "StartupVM";
  final public static String uploadFile = "UploadFile";
  final public static String installFile = "InstallFile";
  final public static String settingProduct = "SettingProduct";
  final public static String activateProduct = "activateProduct";
  final public static String shutdownVM = "ShutdownVM";
  final public static String zipOVF = "ZipOVF";
  final public static String removeClonedVM = "RemoveClonedVM";
  final public static String export2OVF = "ExportToOVF";
  final public static String moveToFileServer = "MoveToFileServer";
  final public static String complete = "Complete";
  final public static String removeOVFFile = "RemoveOVF";
  final public static String stop = "Stop";
  final public static String error = "Error";
  final public static String requestToStop = "RequestToStop";

  private static boolean isValidStatus(String jobStatus)
  {
    if (jobStatus.equals(start) || jobStatus.equals(copyVM) || jobStatus.equals(startupVM) || jobStatus.equals(uploadFile) || jobStatus.equals(installFile)
        || jobStatus.equals(settingProduct) || jobStatus.equals(activateProduct) || jobStatus.equals(shutdownVM) || jobStatus.equals(zipOVF)
        || jobStatus.equals(removeClonedVM) || jobStatus.equals(export2OVF) || jobStatus.equals(moveToFileServer) || jobStatus.equals(complete)
        || jobStatus.equals(removeOVFFile) || jobStatus.equals(stop) || jobStatus.equals(error) || jobStatus.equals(requestToStop))
    {
      return true;
    }
    return false;
  }

  public static Integer getJobstatusId(String jobStatus)
  {
    if (isValidStatus(jobStatus))
    {

      Session session = HibernateUtil.getSessionFactory().openSession();

      Transaction transaction = null;

      try
      {
        transaction = session.beginTransaction();
        List ul = session.createQuery("from Jobstatus where name='" + jobStatus + "'").list();

        if (ul.size() != 1)
        {
          logger.error("There should be just one Jobstatus existed with name: " + jobStatus + ", but now there are: " + ul.size());
          return null;
        }
        for (Iterator iterator = ul.iterator(); iterator.hasNext();)
        {
          Jobstatus js = (Jobstatus) iterator.next();
          logger.info("Jobstatus: " + jobStatus + ", id is: " + js.getId());

          return js.getId();
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
    } else
    {
      logger.error("the queried Jobstatus does not existed");
      return null;
    }
  }

  public static Map<String, Integer> getJobStatusMap()
  {
    Map<String, Integer> jsm = new HashMap<String, Integer>();

    jsm.put(JobStatusEnumType.start, JobStatusEnumType.getJobstatusId(start));
    jsm.put(JobStatusEnumType.copyVM, JobStatusEnumType.getJobstatusId(copyVM));
    jsm.put(JobStatusEnumType.startupVM, JobStatusEnumType.getJobstatusId(startupVM));
    jsm.put(JobStatusEnumType.uploadFile, JobStatusEnumType.getJobstatusId(uploadFile));
    jsm.put(JobStatusEnumType.installFile, JobStatusEnumType.getJobstatusId(installFile));
    jsm.put(JobStatusEnumType.settingProduct, JobStatusEnumType.getJobstatusId(settingProduct));
    jsm.put(JobStatusEnumType.activateProduct, JobStatusEnumType.getJobstatusId(activateProduct));
    jsm.put(JobStatusEnumType.shutdownVM, JobStatusEnumType.getJobstatusId(shutdownVM));
    jsm.put(JobStatusEnumType.zipOVF, JobStatusEnumType.getJobstatusId(zipOVF));
    jsm.put(JobStatusEnumType.removeClonedVM, JobStatusEnumType.getJobstatusId(removeClonedVM));
    jsm.put(JobStatusEnumType.export2OVF, JobStatusEnumType.getJobstatusId(export2OVF));
    jsm.put(JobStatusEnumType.moveToFileServer, JobStatusEnumType.getJobstatusId(moveToFileServer));
    jsm.put(JobStatusEnumType.complete, JobStatusEnumType.getJobstatusId(complete));
    jsm.put(JobStatusEnumType.removeOVFFile, JobStatusEnumType.getJobstatusId(removeOVFFile));
    jsm.put(JobStatusEnumType.stop, JobStatusEnumType.getJobstatusId(stop));
    jsm.put(JobStatusEnumType.error, JobStatusEnumType.getJobstatusId(error));
    jsm.put(JobStatusEnumType.requestToStop, JobStatusEnumType.getJobstatusId(requestToStop));

    return jsm;
  }

  public static String getJobStatusbyStatusId(Integer jobStatusId)
  {

    if (jobStatusId == null)
    {
      logger.error("The queried jobStatusId is null");
      return null;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from Jobstatus where id='" + jobStatusId + "'").list();

      if (ul.size() != 1)
      {
        logger.error("There should be just one Jobstatus existed with id: " + jobStatusId + ", but now there are: " + ul.size());
        return null;
      }
      for (Iterator iterator = ul.iterator(); iterator.hasNext();)
      {
        Jobstatus js = (Jobstatus) iterator.next();
        logger.info("Jobstatus: " + jobStatusId + ", name is: " + js.getName());

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

  public static List<String> getRunningStatusList()
  {
    List<String> jobStatusList = new ArrayList<String>();

    jobStatusList.add(JobStatusEnumType.start);
    jobStatusList.add(JobStatusEnumType.copyVM);
    jobStatusList.add(JobStatusEnumType.startupVM);
    jobStatusList.add(JobStatusEnumType.uploadFile);
    jobStatusList.add(JobStatusEnumType.installFile);
    jobStatusList.add(JobStatusEnumType.settingProduct);
    jobStatusList.add(JobStatusEnumType.activateProduct);
    jobStatusList.add(JobStatusEnumType.shutdownVM);
    jobStatusList.add(JobStatusEnumType.zipOVF);
    jobStatusList.add(JobStatusEnumType.removeClonedVM);
    jobStatusList.add(JobStatusEnumType.export2OVF);
    jobStatusList.add(JobStatusEnumType.moveToFileServer);
    jobStatusList.add(JobStatusEnumType.requestToStop);

    return jobStatusList;
  }

  public static List<String> getValidStatusToBeStop()
  {
    List<String> jobStatusList = new ArrayList<String>();

    jobStatusList.add(JobStatusEnumType.start);
    jobStatusList.add(JobStatusEnumType.copyVM);
    jobStatusList.add(JobStatusEnumType.startupVM);
    jobStatusList.add(JobStatusEnumType.uploadFile);
    jobStatusList.add(JobStatusEnumType.installFile);
    jobStatusList.add(JobStatusEnumType.settingProduct);
    jobStatusList.add(JobStatusEnumType.activateProduct);
    jobStatusList.add(JobStatusEnumType.shutdownVM);
    jobStatusList.add(JobStatusEnumType.zipOVF);
    jobStatusList.add(JobStatusEnumType.removeClonedVM);
    jobStatusList.add(JobStatusEnumType.export2OVF);

    return jobStatusList;
  }

  public static void main(String[] args)
  {
    Map<String, Integer> jsm = getJobStatusMap();
    Iterator it = jsm.keySet().iterator();
    while (it.hasNext())
    {
      String status = (String) it.next();
      logger.info("status: " + status + "\t id:" + jsm.get(status));
    }
  }
}
