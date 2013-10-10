package com.leikai.util;

import java.io.File;

import org.apache.log4j.Logger;

import com.leikai.dao.JobDao;
import com.leikai.dao.impl.JobDaoImpl;
import com.leikai.enumType.JobStatusEnumType;
import com.leikai.enumType.ProductEnumType;
import com.leikai.po.Job;
import com.leikai.po.Os;
import com.leikai.po.Product;
import com.leikai.po.User;
import com.leikai.services.JobService;
import com.leikai.services.OsService;
import com.leikai.services.ProductService;
import com.leikai.services.UserService;
import com.leikai.services.impl.JobServiceImpl;
import com.leikai.services.impl.OsServiceImpl;
import com.leikai.services.impl.ProductServiceImpl;
import com.leikai.services.impl.UserServiceImpl;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

public class RunJob implements Runnable
{
  private Integer osId;
  private Integer poId;
  private Integer userId;
  private Integer jobId;
  private static Integer currentRunningJobNum = 0;
  private Boolean isRBCS = false;
  private JobService js = new JobServiceImpl();
  private JobDao jd = new JobDaoImpl();
  private OsService os = new OsServiceImpl();
  private ProductService ps = new ProductServiceImpl();
  private UserService us = new UserServiceImpl();
  private static Integer maxAllowedRunningJobNum = VMFactoryConfigUtil.getMaxAllowedConvertNum();
  static Logger logger = Logger.getLogger(RunJob.class);

  public RunJob(Integer jobId, Integer userId)
  {

    init(jobId, userId);
    this.jobId = jobId;
    this.userId = userId;
  }

  private void init(Integer jobId, Integer userId)
  {

    Job j = js.getJobByJobId(jobId, userId);
    logger.info("init the job ojbect");
    this.osId = j.getOsId();
    this.poId = j.getProdId();
  }

  public void run()
  {
    currentRunningJobNum++;

    boolean status = false;

    logger.info("Hi boy, the current running job is:" + currentRunningJobNum + ", I am running....");

    // To make sure "/var/vmfactory/productlogs/Logs_jobid" is in existence...
    String logpathbyJob = VMUtil.getLocalLogPath(this.jobId.toString());
    File dirFile = new File(logpathbyJob);

    if (!dirFile.exists() || !dirFile.isDirectory())
    {
      logger.info("Create Logs folder by job id...");
      dirFile.mkdirs();
    }

    User u = this.getUser(this.userId);
    Os srcVM = os.getOsbyOsId(this.osId);
    isRBCS = srcVM.getIsRBCS();
    status = js.isVCenterConnectionOk();
    if (status == false)
    {
      logger.error("the vcenter is not connectable:" + VMFactoryConfigUtil.getvSphereHttpSdkUrl());
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      return;
    }
    String srcVMName = srcVM.getName();

    if (srcVMName == null)
    {
      logger.error("failed to retrieve the src OS by osId: " + osId);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      return;
    }
    Product p = ps.getProductByPoId(this.poId);

    String targetVMName = constructTargetVMName(p, srcVMName);

    if (targetVMName == null)
    {
      logger.error("failed to construct the targetVMName:");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      return;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.start));
    status = cloneVM(targetVMName, srcVMName);

    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("cloneVM: Failed to delete Log Folder!");
      return;
    }

    status = startupVM(targetVMName);
    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("startupVM: Failed to delete Log Folder!");
      return;
    }

    String dstProdPath = VMFactoryConfigUtil.getVMBaseProductUploadLocation() + p.getName();
    logger.info("The local product path is: " + p.getLocation());
    logger.info("The  product will be uploaded to: " + dstProdPath);
    String adminUser = srcVM.getAdminname();
    String adminPassword = srcVM.getAdminpassword();
    VirtualMachine vm = VMUtil.getVMbyName(targetVMName);
    status = VMUtil.makeDirectory(vm, adminUser, adminPassword);
    if (status == false)
    {
      logger.error("failed to create directory on the vm: " + targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("makeDir: Failed to delete Log Folder!");
      return;
    }
    status = uploadFile(p.getLocation(), dstProdPath, targetVMName, adminUser, adminPassword);

    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("uploadFile: Failed to delete Log Folder!");
      return;
    }
    String prodType = ps.getProdTypebyProdTypeId(p.getPtId());

    status = installFile(targetVMName, dstProdPath, prodType, adminUser, adminPassword);

    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
      if (!bdeletefile)
        logger.info("installFile: Failed to delete Log Zip File!");
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("installFile: Failed to delete Log Folder!");
      return;
    }

    boolean isConfigProduct = js.isSettingProduct(jobId);
    if (isConfigProduct == true)
    {
      String pt = ProductEnumType.getProductTypeByTypeId(p.getPtId());

      status = settingProduct(pt, targetVMName, adminUser, adminPassword);

      if (status == false)
      {
        rollback(targetVMName);
        if (currentRunningJobNum > 0)
        {
          currentRunningJobNum--;
        }
        EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
        boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
        if (!bdeletefile)
          logger.info("configProduct: Failed to delete Log Zip File!");
        boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
        if (!bdeleteFolder)
          logger.info("configProduct: Failed to delete Log Folder!");
        return;
      }
    }

    boolean isActivatingProduct = js.isActivatingProduct(jobId);
    if (isActivatingProduct == true)
    {
      String pt = ProductEnumType.getProductTypeByTypeId(p.getPtId());

      status = activateProduct(pt, targetVMName, adminUser, adminPassword);

      if (status == false)
      {
        rollback(targetVMName);
        if (currentRunningJobNum > 0)
        {
          currentRunningJobNum--;
        }
        EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
        boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
        if (!bdeletefile)
          logger.info("activateProduct: Failed to delete Log Zip File!");
        boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
        if (!bdeleteFolder)
          logger.info("activateProduct: Failed to delete Log Folder!");
        return;
      }
    }

    status = shutdownVM(targetVMName);
    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
      if (!bdeletefile)
        logger.info("shutdownVM: Failed to delete Log Zip File!");
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("shutdownVM: Failed to delete Log Folder!");
      return;
    }

    String ovfLocation = VMUtil.getOvfExportLocation(targetVMName, isRBCS);
    status = exportToOVF(targetVMName, ovfLocation);
    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
      if (!bdeletefile)
        logger.info("exportToOVF: Failed to delete Log Zip File!");
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("exportToOVF: Failed to delete Log Folder!");
      return;
    }
    String zipOvfFileName = VMUtil.getZipOvfFileName(targetVMName, isRBCS);

    status = zipExportOVFFile(targetVMName, zipOvfFileName, ovfLocation);
    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
      if (!bdeletefile)
        logger.info("zipOVF: Failed to delete Log Zip File!");
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("zipOVF: Failed to delete Log Folder!");
      return;
    }

    status = removeClonedVM(targetVMName);

    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
      if (!bdeletefile)
        logger.info("removeCloneVM: Failed to delete Log Zip File!");
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("removeCloneVM: Failed to delete Log Folder!");
      return;
    }

    // status = moveToFinalFileServer(zipOvfFileName, targetVMName);
    // if (status == false)
    // {
    // if (currentRunningJobNum > 0)
    // {
    // currentRunningJobNum--;
    // }
    // EmailUtil.send(u.getName(), u.getEmail(), null, false);
    // return;
    // }
    status = removeConvertFiles(ovfLocation, targetVMName);
    if (status == false)
    {
      rollback(targetVMName);
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
      if (!bdeletefile)
        logger.info("removeCovertFiles: Failed to delete Log Zip File!");
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("removeCoverFiles: Failed to delete Log Folder!");
      return;
    }
    String finalLocation = getFinalLocation(targetVMName);
    status = complete(finalLocation);
    if (status == false)
    {
      if (currentRunningJobNum > 0)
      {
        currentRunningJobNum--;
      }
      EmailUtil.send(u.getName(), u.getEmail(), null, srcVM, false, jobId);
      boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
      if (!bdeletefile)
        logger.info("Complete: Failed to delete Log Zip File!");
      boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
      if (!bdeleteFolder)
        logger.info("Complete: Failed to delete Log Folder!");
      return;
    }
    if (currentRunningJobNum > 0)
    {
      currentRunningJobNum--;
    }
    EmailUtil.send(u.getName(), u.getEmail(), finalLocation, srcVM, true, jobId);
    logger.info("Hi boy, I am done to create an image!");

    boolean bdeletefile = VMUtil.deleteZip(VMUtil.getLocalZippedFile(this.jobId.toString()));
    if (!bdeletefile)
      logger.info("finalComplete: Failed to delete Log Zip File!");
    boolean bdeleteFolder = VMUtil.deleteLogFolder(logpathbyJob);
    if (!bdeleteFolder)
      logger.info("finalComplete: Failed to delete Log Folder!");

  }

  private boolean cloneVM(String targetVMName, String srcVM)
  {
    logger.info("copyVM start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;

    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.copyVM));

    if (status == false)
    {
      logger.info("update db status failed");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }

    status = VMUtil.cloneVM(targetVMName, srcVM);
    if (status == false)
    {
      logger.info("copy files failed");

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.copyVM));
    logger.info("copyVM done");

    return true;

  }

  private boolean startupVM(String vmName)
  {
    logger.info("startupVM start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(vmName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.startupVM));

    if (status == false)
    {
      logger.error("failed to update the db status, return false");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }

    status = VMPowerUtil.powerOnTillAvail(vmName);
    if (status == false)
    {
      logger.error("failed to poweron the vm: " + vmName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.startupVM));

    logger.info("startupVM done");

    return true;
  }

  private boolean uploadFile(String srcProductPath, String dstProductPath, String targetVMName, String adminusername, String adminpassword)
  {
    logger.info("uploadFile start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.uploadFile));

    if (status == false)
    {
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }

    status = VMUtil.uploadFile(srcProductPath, dstProductPath, targetVMName, adminusername, adminpassword);

    if (status == false)
    {
      logger.error("uploadFile file failed");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;

    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.uploadFile));

    logger.info("uploadFile done!");

    return true;
  }

  private boolean installFile(String targetVMName, String dstProdPath, String prodType, String adminusername, String adminpassword)
  {
    logger.info("installFile start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.installFile));

    if (status == false)
    {
      return false;
    }
    status = VMUtil.installFile(targetVMName, dstProdPath, prodType, adminusername, adminpassword, jobId);
    if (status == false)
    {
      logger.error("failed to install the product: " + dstProdPath);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.installFile));
    logger.info("installFile done!");
    return true;
  }

  private boolean settingProduct(String prodType, String targetVMName, String adminusername, String adminpassword)
  {
    logger.info("settingProduct start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.settingProduct));

    if (status == false)
    {
      return false;
    }
    status = VMUtil.settingProduct(prodType, targetVMName, adminusername, adminpassword);
    if (status == false)
    {
      logger.error("failed to config the product");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.settingProduct));
    logger.info("settingProduct done!");
    return true;
  }

  private boolean activateProduct(String prodType, String targetVMName, String adminusername, String adminpassword)
  {
    logger.info("activateProduct start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.activateProduct));

    if (status == false)
    {
      return false;
    }
    status = VMUtil.activateProduct(prodType, targetVMName, adminusername, adminpassword);
    if (status == false)
    {
      logger.error("failed to activate the product");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.activateProduct));
    logger.info("activateProduct done!");
    return true;
  }

  private boolean shutdownVM(String vmName)
  {
    logger.info("shutdownVM start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(vmName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }

    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.shutdownVM));

    if (status == false)
    {
      logger.error("failed to update the db tables");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    status = VMPowerUtil.shutdown(vmName);

    if (status == false)
    {
      logger.error("failed to shutdown the VM: " + vmName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }

    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.shutdownVM));

    logger.info("shutdownVM done!");

    return true;
  }

  private boolean removeClonedVM(String vmName)
  {
    logger.info("removeClonedVM start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(vmName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.removeClonedVM));

    if (status == false)
    {
      logger.error("failed to update the db tables");

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    status = VMUtil.removeVM(vmName);
    if (status == false)
    {
      logger.error("fail to remove the cloned VM: " + vmName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.removeClonedVM));

    logger.info("removeClonedVM done!");

    return true;
  }

  private boolean exportToOVF(String vmName, String ovfLocation)
  {
    logger.info("exportTOVF start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(vmName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.export2OVF));

    if (status == false)
    {
      logger.error("failed to update the db tables");

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }

    status = OVFOperation.exportToLocal(vmName, ovfLocation);

    if (status == false)
    {
      logger.error("fail to export the file to ovf format:" + vmName);

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.export2OVF));
    logger.info("exportToOVF done");
    return true;
  }

  private boolean zipExportOVFFile(String targetVMName, String zipOvfFileName, String ovfLocation)
  {
    logger.info("compress (zip) the file start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.zipOVF));

    if (status == false)
    {
      logger.error("failed to update the db tables");

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }

    status = VMUtil.zipOVF(zipOvfFileName, ovfLocation);

    if (status == false)
    {
      logger.error("fail to zip the file:" + ovfLocation);

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }

    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.zipOVF));

    logger.info("compress (zip) the file done!");

    return true;
  }

  private boolean moveToFinalFileServer(String zippedOVFFile, String targetVMName)
  {
    logger.info("moveToTarget start");
    boolean status = false;
    // TODO, not allowed to stop in this status
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.moveToFileServer));

    if (status == false)
    {
      logger.error("failed to update the db tables");

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    status = VMUtil.moveToFinalFileServer(zippedOVFFile);
    if (status == false)
    {
      logger.error("fail to move the file to target file server");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.moveToFileServer));
    logger.info("moveToTarget done");
    return true;
  }

  private boolean removeConvertFiles(String ovfLocation, String targetVMName)
  {
    logger.info("removeConvertFile start");
    boolean status = false;
    if (js.isJobRequestToStop(jobId) == true)
    {
      // rollback(targetVMName);
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.stop));
      return false;
    }
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.removeOVFFile));

    if (status == false)
    {
      logger.error("failed to update the db tables");

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }
    status = VMUtil.removeConvertFiles(ovfLocation);
    if (status == false)
    {
      logger.error("fail to remove the converted file");
      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));
      return false;
    }
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.removeOVFFile));

    logger.info("removeConvertFile done");

    return true;
  }

  private boolean complete(String finalLocation)
  {

    boolean status = false;
    status = jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.complete));

    if (status == false)
    {
      logger.error("failed to update the db tables");

      jd.updateJob(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.error));

      return false;
    }
    jd.updateJobTargetVMLocation(jobId, finalLocation);
    jd.updateJobProgressEndTime(jobId, JobStatusEnumType.getJobstatusId(JobStatusEnumType.complete));

    logger.info("complete");
    return true;
  }

  private void rollback(String vmName)
  {

    if (VMUtil.isVMExisted(vmName))
    {
      VMPowerUtil.powerOff(vmName);
      VMUtil.removeVM(vmName);
    }
    VMUtil.removeConvertFiles(VMUtil.getOvfExportLocation(vmName, isRBCS));

  }

  public static Integer get_currentRunningJobNum()
  {
    return currentRunningJobNum;
  }

  // Those two methods is just open for testing purpose (JobServiceTest, when
  // multiple test need to start a job, we need to reset the
  // currentRunningJobNum, or it might cause the later case failed.
  public static void set_currentRunningJobNum(Integer currentRunningJobNum)
  {
    RunJob.currentRunningJobNum = currentRunningJobNum;
  }

  public static boolean isMaxAllowedRunningJobsReached()
  {
    if (currentRunningJobNum >= maxAllowedRunningJobNum)
    {
      logger.error("max allowed running jobs reached, return null");
      return true;
    }
    return false;
  }

  private User getUser(Integer userId)
  {

    User u = us.getUserByUserId(userId);
    return u;
  }

  private String getFinalLocation(String targetVMName)
  {
    if (isRBCS == true)
    {
      return VMFactoryConfigUtil.getBaseLocationOfFinalFileServer() + File.separator + VMFactoryConfigUtil.getBaseDirOfFinalFileServer_RBCS() + File.separator
          + targetVMName + ".zip";
    } else
    {
      return VMFactoryConfigUtil.getBaseLocationOfFinalFileServer() + File.separator + VMFactoryConfigUtil.getBaseDirOfFinalFileServer_nonRBCS()
          + File.separator + targetVMName + ".zip";
    }
  }

  private String constructTargetVMName(Product p, String srcVM)
  {
    String pName = p.getName();

    User u = us.getUserByUserId(this.userId);
    String ret = srcVM + "-" + pName + "-by-" + u.getName();
    ret = ret.replace("_TEMPLATE-", "-");
    ret = ret.replace(".exe-", "-");
    logger.info("The constructed target VM Name is: " + ret);
    if (ret.length() > 80)
      logger.info("The length of the constructed target VM name is: " + ret.length() + ", still longer than 80");
    return ret;
  }

}
