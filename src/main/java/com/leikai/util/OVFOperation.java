package com.leikai.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.rmi.RemoteException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;

import com.vmware.vim25.HttpNfcLeaseDeviceUrl;
import com.vmware.vim25.HttpNfcLeaseInfo;
import com.vmware.vim25.HttpNfcLeaseState;
import com.vmware.vim25.OvfCreateDescriptorParams;
import com.vmware.vim25.OvfCreateDescriptorResult;
import com.vmware.vim25.OvfFile;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.HttpNfcLease;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualApp;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class OVFOperation
{
  public static LeaseProgressUpdater leaseProgUpdater;
  static Logger logger = Logger.getLogger(OVFOperation.class);

  public static boolean exportToLocal(String vmName, String ovfLocation)
  {
    ServiceInstance si = VMHelper.getServiceInstance();
    String vAppOrVmName = vmName;
    String hostip = VMFactoryConfigUtil.getvSphereUrl();
    String entityType = "VirtualMachine";

    String targetDir = ovfLocation;

    // Create one directory
    boolean success = (new File(targetDir)).mkdir();
    if (success)
    {
      logger.info("Directory: " + targetDir + " created");
    } else
    {

      logger.error("Directory: " + targetDir + " is failed to created return");
      return false;

    }
    try
    {
      // HostSystem host;
      // host = (HostSystem) si.getSearchIndex().findByIp(null, hostip, false);
      // host = (HostSystem) si.getSearchIndex().findByDnsName(null, hostip,
      // false);

      // logger.info("Host Name : " + host.getName());
      // logger.info("Network : " + host.getNetworks()[0].getName());
      // logger.info("Datastore : " + host.getDatastores()[0].getName());

      InventoryNavigator iv = new InventoryNavigator(si.getRootFolder());

      HttpNfcLease hnLease = null;

      ManagedEntity me = null;
      if (entityType.equals("VirtualApp"))
      {
        me = iv.searchManagedEntity("VirtualApp", vAppOrVmName);
        hnLease = ((VirtualApp) me).exportVApp();
      } else
      {
        me = iv.searchManagedEntity("VirtualMachine", vAppOrVmName);
        hnLease = ((VirtualMachine) me).exportVm();
      }

      // Wait until the HttpNfcLeaseState is ready
      HttpNfcLeaseState hls;
      for (;;)
      {
        hls = hnLease.getState();
        if (hls == HttpNfcLeaseState.ready)
        {
          break;
        }
        if (hls == HttpNfcLeaseState.error)
        {
          si.getServerConnection().logout();
          return false;
        }
      }

      logger.info("HttpNfcLeaseState: ready ");
      HttpNfcLeaseInfo httpNfcLeaseInfo = hnLease.getInfo();
      httpNfcLeaseInfo.setLeaseTimeout(300 * 1000 * 1000);
      printHttpNfcLeaseInfo(httpNfcLeaseInfo);

      // Note: the diskCapacityInByte could be many time bigger than
      // the total size of VMDK files downloaded.
      // As a result, the progress calculated could be much less than reality.
      long diskCapacityInByte = (httpNfcLeaseInfo.getTotalDiskCapacityInKB()) * 1024;

      leaseProgUpdater = new LeaseProgressUpdater(hnLease, 5000);
      leaseProgUpdater.start();

      long alredyWrittenBytes = 0;
      HttpNfcLeaseDeviceUrl[] deviceUrls = httpNfcLeaseInfo.getDeviceUrl();
      if (deviceUrls != null)
      {
        OvfFile[] ovfFiles = new OvfFile[deviceUrls.length];
        logger.info("Downloading Files:");
        for (int i = 0; i < deviceUrls.length; i++)
        {
          String deviceId = deviceUrls[i].getKey();
          String deviceUrlStr = deviceUrls[i].getUrl();
          // String diskFileName =
          // deviceUrlStr.substring(deviceUrlStr.lastIndexOf("/") + 1);
          String diskFileName = vAppOrVmName;

          String diskUrlStr = deviceUrlStr.replace("*", hostip);
          String diskLocalPath = targetDir + diskFileName;
          logger.info("File Name: " + diskFileName);
          logger.info("VMDK URL: " + diskUrlStr);
          String cookie = si.getServerConnection().getVimService().getWsc().getCookie();
          long lengthOfDiskFile = writeVMDKFile(diskLocalPath, diskUrlStr, cookie, alredyWrittenBytes, diskCapacityInByte);
          alredyWrittenBytes += lengthOfDiskFile;
          OvfFile ovfFile = new OvfFile();
          ovfFile.setPath(diskFileName);
          ovfFile.setDeviceId(deviceId);
          ovfFile.setSize(lengthOfDiskFile);
          ovfFiles[i] = ovfFile;
        }

        OvfCreateDescriptorParams ovfDescParams = new OvfCreateDescriptorParams();
        ovfDescParams.setOvfFiles(ovfFiles);
        OvfCreateDescriptorResult ovfCreateDescriptorResult = si.getOvfManager().createDescriptor(me, ovfDescParams);

        String ovfPath = targetDir + vAppOrVmName + ".ovf";
        FileWriter out = new FileWriter(ovfPath);
        out.write(ovfCreateDescriptorResult.getOvfDescriptor());
        out.close();
        logger.info("OVF Desriptor Written to file: " + ovfPath);
      }

      logger.info("Completed Downloading the files");
      leaseProgUpdater.interrupt();
      hnLease.httpNfcLeaseProgress(100);
      hnLease.httpNfcLeaseComplete();

      si.getServerConnection().logout();
      return true;
    } catch (Exception e)
    {
      logger.error("unexpected error happens while export to ovf format, return false");

      e.printStackTrace();

      return false;

    } finally
    {
      if (si != null)
      {
        si.getServerConnection().logout();
      }
    }
  }

  private static void printHttpNfcLeaseInfo(HttpNfcLeaseInfo info)
  {
    logger.info("########################  HttpNfcLeaseInfo  ###########################");
    logger.info("Lease Timeout: " + info.getLeaseTimeout());
    logger.info("Total Disk capacity: " + info.getTotalDiskCapacityInKB());
    HttpNfcLeaseDeviceUrl[] deviceUrlArr = info.getDeviceUrl();
    if (deviceUrlArr != null)
    {
      int deviceUrlCount = 1;
      for (HttpNfcLeaseDeviceUrl durl : deviceUrlArr)
      {
        logger.info("HttpNfcLeaseDeviceUrl : " + deviceUrlCount++);
        logger.info("  Device URL Import Key: " + durl.getImportKey());
        logger.info("  Device URL Key: " + durl.getKey());
        logger.info("  Device URL : " + durl.getUrl());
        logger.info("  SSL Thumbprint : " + durl.getSslThumbprint());
      }
    } else
    {
      logger.info("No Device URLS Found");
    }
  }

  private static long writeVMDKFile(String localFilePath, String diskUrl, String cookie, long bytesAlreadyWritten, long totalBytes) throws IOException
  {
    HttpsURLConnection conn = getHTTPConnection(diskUrl, cookie);
    InputStream in = conn.getInputStream();
    OutputStream out = new FileOutputStream(new File(localFilePath));
    byte[] buf = new byte[102400];
    int len = 0;
    long bytesWritten = 0;
    while ((len = in.read(buf)) > 0)
    {
      out.write(buf, 0, len);
      bytesWritten += len;
      int percent = (int) (((bytesAlreadyWritten + bytesWritten) * 100) / totalBytes);
      leaseProgUpdater.setPercent(percent);

    }
    in.close();
    out.close();
    return bytesWritten;
  }

  private static HttpsURLConnection getHTTPConnection(String urlStr, String cookieStr) throws IOException
  {
    HostnameVerifier hv = new HostnameVerifier()
      {
        public boolean verify(String urlHostName, SSLSession session)
        {
          return true;
        }
      };
    HttpsURLConnection.setDefaultHostnameVerifier(hv);
    URL url = new URL(urlStr);
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

    conn.setDoInput(true);
    conn.setDoOutput(true);
    conn.setAllowUserInteraction(true);
    conn.setRequestProperty("Cookie", cookieStr);
    conn.connect();
    return conn;
  }

}
