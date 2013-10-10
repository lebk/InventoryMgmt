package com.leikai.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vmware.vim25.ArrayOfHostDatastoreBrowserSearchResults;
import com.vmware.vim25.FileInfo;
import com.vmware.vim25.HostDatastoreBrowserSearchResults;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostDatastoreBrowser;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class VMHelper
{
  private static ServiceInstance si = null;
  private static Logger logger = Logger.getLogger(VMHelper.class);

  public static String[] constructArgs(Map<String, String> map)
  {
    String[] newArgs = new String[map.size() * 2];
    Iterator<String> it = map.keySet().iterator();

    int count = 0;
    while (it.hasNext())
    {
      String key = it.next();

      newArgs[count] = "--" + key;
      newArgs[count + 1] = map.get(key);
      count += 2;

    }

    return newArgs;
  }

  public static List<FileInfo> getFilesInVM(String dsname, String vmName)
  {
    List<FileInfo> fi = new ArrayList<FileInfo>();

    try
    {
      si = VMHelper.getServiceInstance();
      Folder rootFolder = si.getRootFolder();

      Datastore ds = (Datastore) new InventoryNavigator(rootFolder).searchManagedEntity("Datastore", dsname);

      System.out.println("\nSearching The Datastore " + dsname);

      HostDatastoreBrowser dsBrowser = ds.getBrowser();
      Task task = dsBrowser.searchDatastoreSubFolders_Task("[" + dsname + "]/" + vmName, null);
      task.waitForTask();
      TaskInfo tInfo = task.getTaskInfo();
      ArrayOfHostDatastoreBrowserSearchResults searchResult = (ArrayOfHostDatastoreBrowserSearchResults) tInfo.getResult();

      int len = searchResult.getHostDatastoreBrowserSearchResults().length;
      for (int j = 0; j < len; j++)
      {
        HostDatastoreBrowserSearchResults sres = searchResult.HostDatastoreBrowserSearchResults[j];
        FileInfo[] fileArray = sres.getFile();
        if (fileArray == null)
          continue;
        for (int k = 0; k < fileArray.length; k++)
        {
          File tempe = new File(fileArray[k].getPath());
          fi.add(fileArray[k]);
        }
      }
    } catch (Exception e)
    {
      e.printStackTrace();

    } finally
    {
      if (si != null)
      {
        si.getServerConnection().logout();
      }
    }

    return fi;
  }

  public static String getVmxName(String dsName, String vmName)
  {
    List<FileInfo> fil = getFilesInVM(dsName, vmName);

    for (FileInfo fi : fil)
    {
      String filename = fi.getPath();
      String filetype = filename.substring(filename.lastIndexOf("."));
      if (filetype.equals(".vmx"))
      {
        return filename;
      }
    }
    logger.error("There is no vmx file in datastore:" + dsName + ", vm: " + vmName);
    return null;
  }

  public static ServiceInstance getServiceInstance()
  {

    String username = VMFactoryConfigUtil.getvSphereUsername();
    String userpassword = VMFactoryConfigUtil.getvSpherePassword();
    String vsphereHttpSdkUrl = VMFactoryConfigUtil.getvSphereHttpSdkUrl();

    try
    {
      return new ServiceInstance(new URL(vsphereHttpSdkUrl), username, userpassword, true);
    } catch (RemoteException e)
    {
      e.printStackTrace();
      VMFactoryConfigUtil.logger.error("create service instance error" + e.getMessage());
    } catch (MalformedURLException e)
    {
      e.printStackTrace();
      VMFactoryConfigUtil.logger.error("create service instance error" + e.getMessage());
    }

    return null;
  }
}
