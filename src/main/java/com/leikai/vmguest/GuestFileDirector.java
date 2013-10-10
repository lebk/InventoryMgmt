package com.leikai.vmguest;

import com.leikai.util.VMFactoryConfigUtil;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class GuestFileDirector
{

  protected VirtualMachine vm;
  protected GuestFileManager gfm;
  protected NamePasswordAuthentication auth;
  static Logger logger = Logger.getLogger(VMFactoryConfigUtil.class);

  public GuestFileDirector(VirtualMachine vm, String username, String password)
  {
    auth = new NamePasswordAuthentication();
    this.vm = vm;
    gfm = vm.getServerConnection().getServiceInstance().getGuestOperationsManager().getFileManager(vm);
    auth.username = username;
    auth.password = password;
  }

  public void changeFileAttributes(String guestFilePath, Calendar accessTime, Calendar modificationTime, String symlinkTarget) throws GuestOperationsFault,
      InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
  {
    GuestFileAttributes fileAttr = new GuestFileAttributes();
    fileAttr.accessTime = accessTime;
    fileAttr.modificationTime = modificationTime;
    fileAttr.symlinkTarget = symlinkTarget;
    gfm.changeFileAttributesInGuest(auth, guestFilePath, fileAttr);
  }

  public String createTemporaryDirectory(String prefix, String suffix, String directoryPath) throws GuestOperationsFault, InvalidState, TaskInProgress,
      FileFault, RuntimeFault, RemoteException
  {
    return gfm.createTemporaryDirectoryInGuest(auth, prefix, suffix, directoryPath);
  }

  public String createTemporaryFile(String prefix, String suffix, String directoryPath) throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault,
      RuntimeFault, RemoteException
  {
    return gfm.createTemporaryFileInGuest(auth, prefix, suffix, directoryPath);
  }

  public void deleteDirectory(String directoryPath) throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
  {
    gfm.deleteDirectoryInGuest(auth, directoryPath, true);
  }

  public void deleteFile(String filePath) throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
  {
    gfm.deleteFileInGuest(auth, filePath);
  }

  public GuestListFileInfo listFiles(String pathInGuest, String matchPattern) throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault,
      RuntimeFault, RemoteException
  {
    return gfm.listFilesInGuest(auth, pathInGuest, 0, 2147483647, matchPattern);
  }

  public GuestListFileInfo listFiles(String pathInGuest) throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
  {
    return listFiles(pathInGuest, ".*");
  }

  public void makeDirectory(String dirPathInGuest) throws IOException
  {
    gfm.makeDirectoryInGuest(auth, dirPathInGuest, true);
  }

  public void moveDirectory(String srcDirectoryPath, String dstDirectoryPath) throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault,
      RuntimeFault, RemoteException
  {
    gfm.moveDirectoryInGuest(auth, srcDirectoryPath, dstDirectoryPath);
  }

  public void moveFile(String srcFilePath, String dstFilePath, boolean overwrite) throws GuestOperationsFault, InvalidState, TaskInProgress, FileFault,
      RuntimeFault, RemoteException
  {
    gfm.moveFileInGuest(auth, srcFilePath, dstFilePath, overwrite);
  }

  public void downloadDirectory(String dirPathInGuest, String dirPathLocal) throws IOException, GuestOperationsFault, InvalidState, TaskInProgress, FileFault,
      RuntimeFault, RemoteException
  {
    File localDir = new File(dirPathLocal);
    localDir.mkdirs();
    GuestListFileInfo listInfo = listFiles(dirPathInGuest);
    GuestFileInfo aguestfileinfo[];
    int j = (aguestfileinfo = listInfo.files).length;
    for (int i = 0; i < j; i++)
    {
      GuestFileInfo fileInfo = aguestfileinfo[i];
      String newPathInGuest = (new File(dirPathInGuest, fileInfo.path)).getPath();
      String newPathLocal = (new File(dirPathLocal, fileInfo.path)).getPath();

      
      logger.info("newPathLocal:" + newPathLocal);
      logger.info("fileInfo:" + fileInfo.getPath());
      if (newPathInGuest.contains("c:\\"))
      {
        newPathInGuest = newPathInGuest.substring(newPathInGuest.lastIndexOf("c:\\"));
        newPathInGuest = newPathInGuest.replace("/", "\\");
      }
      logger.info("newPathInGuest:" + newPathInGuest);
      
      if ("directory".equals(fileInfo.type))
      {
        logger.info("direcory");
        if (!".".equals(fileInfo.path) && !"..".equals(fileInfo.path))
          downloadDirectory(newPathInGuest, newPathLocal);
      } else
      {
        logger.info("file");

        downloadFile(newPathInGuest, newPathLocal);
      }
    }

  }

  public void downloadFile(String filePathInGuest, String filePathLocal) throws IOException
  {
    FileOutputStream out = new FileOutputStream(filePathLocal);
    logger.info("file downloaded to: " + filePathLocal);
    logger.info("file downloaded from: " + filePathInGuest);
    GuestFileAttributes fileAttr = downloadToStream(filePathInGuest, out);
    out.close();
    (new File(filePathLocal)).setLastModified(fileAttr.modificationTime.getTimeInMillis());
  }

  public GuestFileAttributes downloadToStream(String filePathInGuest, OutputStream out) throws IOException
  {
    FileTransferInformation fileTranInfo = gfm.initiateFileTransferFromGuest(auth, filePathInGuest);

    URL fileURL = new URL(updateUrl(fileTranInfo.getUrl()));
    InputStream is = fileURL.openStream();
    readStream2Stream(is, out);
    is.close();
    return fileTranInfo.getAttributes();
  }

  public void uploadDirectory(String dirPathLocal, String dirPathInGuest) throws IOException
  {
    File dir = new File(dirPathLocal);
    if (!dir.isDirectory())
      throw new IllegalArgumentException((new StringBuilder("Local directory path points to a file: ")).append(dirPathLocal).toString());
    makeDirectory(dirPathInGuest);
    File kids[] = dir.listFiles();
    File afile[];
    int j = (afile = kids).length;
    for (int i = 0; i < j; i++)
    {
      File kid = afile[i];
      String kidName = kid.getName();
      String pathInGuest = (new File(dirPathInGuest, kidName)).getCanonicalPath();
      logger.info("The path in guest is:" + pathInGuest);
      if (pathInGuest.contains("c:\\"))
      {
        pathInGuest = pathInGuest.substring(pathInGuest.lastIndexOf("c:\\"));
        pathInGuest = pathInGuest.replace("/", "\\");
      }
      logger.info("The path in guest is:" + pathInGuest);

      if (kid.isDirectory())
      {

        String pathofkid = kid.getCanonicalPath();
        logger.info("directory,before, kid path:" + pathofkid);
        if (pathofkid.contains("c:\\"))
        {
          pathofkid = pathofkid.substring(pathInGuest.lastIndexOf("c:\\"));
        }
        logger.info("directory,after kid path:" + pathofkid);

        uploadDirectory(pathofkid, pathInGuest);
      } else
      {
        String pathofkid = kid.getCanonicalPath();
        logger.info("file,before, kid path:" + pathofkid);

        if (pathofkid.contains("c:\\"))
        {
          pathofkid = pathofkid.substring(pathInGuest.lastIndexOf("c:\\"));
        }
        logger.info("file,after, kid path:" + pathofkid);

        uploadFile(pathofkid, pathInGuest);
      }
    }

  }

  public boolean uploadFile(String filePathLocal, String filePathInGuest)
  {
    boolean status = false;
    File file = new File(filePathLocal);
    if (file.isDirectory())
    {
      logger.error("Local file path points to a directory:");
      return false;
    } else
    {
      long fileSize = file.length();
      FileInputStream in;
      try
      {
        in = new FileInputStream(filePathLocal);

        status = uploadFromStream(in, fileSize, filePathInGuest, file.lastModified(), true);
        in.close();
      } catch (Exception e)
      {
        e.printStackTrace();
        return false;
      }
      return status;
    }
  }

  public boolean uploadFromStream(InputStream in, long size, String filePathInGuest, long modifyTime, boolean overwrite)
  {
    GuestFileAttributes guestFileAttr = null;
    // Temp solution, as we just support the windows
    // platform,(vm.getGuest().getGUestId(),
    GuestWindowsFileAttributes winFileAttr = new GuestWindowsFileAttributes();
    guestFileAttr = winFileAttr;

    // if (vm.getGuest().getGuestId().startsWith("win"))
    // {
    // GuestWindowsFileAttributes winFileAttr = new
    // GuestWindowsFileAttributes();
    // guestFileAttr = winFileAttr;
    // } else
    // {
    // GuestPosixFileAttributes posixFileAttributes = new
    // GuestPosixFileAttributes();
    // posixFileAttributes.setPermissions(Long.valueOf(420L));
    // guestFileAttr = posixFileAttributes;
    // }
    guestFileAttr.setAccessTime(Calendar.getInstance());
    Calendar modCal = Calendar.getInstance();
    modCal.setTimeInMillis(modifyTime);
    guestFileAttr.setModificationTime(modCal);
    String upUrlStr;
    try
    {
      upUrlStr = gfm.initiateFileTransferToGuest(auth, filePathInGuest, guestFileAttr, size, overwrite);
    } catch (Exception e)
    {
      e.printStackTrace();
      logger.error("fail to initiate the file transfer to guest, return false");
      return false;
    }
    String newUrlStr = updateUrl(upUrlStr);
    return uploadData(newUrlStr, in, size);

  }

  private String updateUrl(String upUrlStr)
  {

    if (upUrlStr == null)
    {
      logger.error("upUrlStr should not be null");
      return null;
    }
    String newUpUrlStr = upUrlStr;
    logger.info("Before update Url:" + upUrlStr);
    if (upUrlStr.contains("https://*:443"))
    {
      logger.info(upUrlStr);
      newUpUrlStr = "https://" + VMFactoryConfigUtil.getvSphereUrl() + ":443" + upUrlStr.substring(13);
      logger.info(newUpUrlStr);
    }
    logger.info("after update Url:" + newUpUrlStr);
    return newUpUrlStr;
  }

  private boolean uploadData(String urlString, InputStream in, long size)
  {
    boolean status = false;
    HttpURLConnection conn = null;
    try
    {
      conn = (HttpURLConnection) (new URL(urlString)).openConnection();
      conn.setFixedLengthStreamingMode(size);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setRequestProperty("Content-Type", "application/octet-stream");
      conn.setRequestMethod("PUT");
      conn.setRequestProperty("Content-Length", Long.toString(size));
      OutputStream out = conn.getOutputStream();
      readStream2Stream(in, out);
      out.close();
      if (200 != conn.getResponseCode())
      {
        logger.error("File upload is not successful");
        status = false;
      } else
      {
        conn.disconnect();
        status = true;
      }
    } catch (Exception e)
    {
      e.printStackTrace();
      logger.error("get exception while update the data:" + e.getMessage());
    } finally
    {
      if (conn != null)
      {
        conn.disconnect();
      }
    }
    return status;
  }

  private static void readStream2Stream(InputStream from, OutputStream to) throws IOException
  {
    byte buf[] = new byte[4096];
    for (int len = 0; (len = from.read(buf)) > 0;)
      to.write(buf, 0, len);

  }

}
