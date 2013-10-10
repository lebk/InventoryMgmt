package com.leikai.util;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.leikai.enumType.ProductEnumType;
import com.leikai.vmguest.GuestFileDirector;
import com.leikai.vmguest.GuestProcessDirector;
import com.vmware.vim25.ArrayOfHostDatastoreBrowserSearchResults;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.FileInfo;
import com.vmware.vim25.GuestFileInfo;
import com.vmware.vim25.GuestFileType;
import com.vmware.vim25.GuestListFileInfo;
import com.vmware.vim25.GuestOperationsFault;
import com.vmware.vim25.GuestProcessInfo;
import com.vmware.vim25.HostDatastoreBrowserSearchResults;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInProgress;
import com.vmware.vim25.TaskInfo;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.*;

public class VMUtil {

	private static ServiceInstance si = null;
	private static String productIntallLogPathName = "Log";
	private static String productActivationReportFile = "ATReport.xml";
	private static Logger logger = Logger.getLogger(VMUtil.class);
	List<String> potenailLogPath;
	private static Integer maxRetryTime = 200;

	public static boolean uploadFile(String src, String dstPath,
			String dstVMName, String adminusername, String adminpassword) {
		boolean status = false;
		try {
			si = VMHelper.getServiceInstance();

			Folder rootFolder = si.getRootFolder();

			ManagedEntity[] mes = new InventoryNavigator(rootFolder)
					.searchManagedEntities("VirtualMachine");
			if (mes == null || mes.length == 0) {
				return false;
			}

			VirtualMachine vm = VMUtil.getVMbyName(dstVMName);

			logger.info("guest tool status:" + vm.getGuest().toolsRunningStatus);
			if (!"guestToolsRunning".equals(vm.getGuest().toolsRunningStatus)) {
				logger.info("The VMware Tools is not running in the Guest OS on VM: "
						+ vm.getName());
				logger.info("Exiting...");
				return false;
			}
			logger.info("the src dir is: " + src);
			logger.info("the dest dir is: " + dstPath);
			logger.info("The admin user name fo the guest VM: " + dstVMName);
			logger.info("The admin user name fo the guest VM: " + adminusername);
			logger.info("The admin user password of the guest VM: "
					+ adminpassword);
			GuestFileDirector fd = new GuestFileDirector(vm, adminusername,
					adminpassword);
			logger.info("begin to upload");
			status = fd.uploadFile(src, dstPath);
			logger.info("upload done");
			return status;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (si != null) {
				si.getServerConnection().logout();
			}
		}
		return false;
	}

	public static boolean uploadDirectory(String dstDir, String srcDir,
			String vmName, String adminusername, String adminpassword) {
		VirtualMachine vm = VMUtil.getVMbyName(vmName);
		if (vm == null) {
			logger.error("no VM found by the name: " + vmName);
			return false;
		}

		GuestFileDirector fd = new GuestFileDirector(vm, adminusername,
				adminpassword);

		try {
			fd.uploadDirectory(srcDir, dstDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public static boolean makeDirectory(VirtualMachine vm,
			String adminusername, String adminpassword) {

		GuestFileDirector fd = new GuestFileDirector(vm, adminusername,
				adminpassword);
		String basePath = VMFactoryConfigUtil.getVMBaseProductUploadLocation();

		try {// Try to delete the dir first if it exists
			logger.info("try to delete:" + basePath);
			logger.info("the vm name is:" + vm.getName());
			logger.info("the admin user name is:" + adminusername);
			logger.info("the admin password is:" + adminpassword);

			fd.deleteDirectory(basePath);
		} catch (Exception e) {
			logger.warn("delete directory:" + e);
		}
		try {

			fd.makeDirectory(basePath);

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<FileInfo> getFilesInVM(String dsname, String vmName) {
		List<FileInfo> fi = new ArrayList<FileInfo>();

		try {
			si = VMHelper.getServiceInstance();
			Folder rootFolder = si.getRootFolder();

			Datastore ds = (Datastore) new InventoryNavigator(rootFolder)
					.searchManagedEntity("Datastore", dsname);

			logger.info("\nSearching The Datastore " + dsname);

			HostDatastoreBrowser dsBrowser = ds.getBrowser();
			Task task = dsBrowser.searchDatastoreSubFolders_Task("[" + dsname
					+ "]/" + vmName, null);
			task.waitForTask();
			TaskInfo tInfo = task.getTaskInfo();
			ArrayOfHostDatastoreBrowserSearchResults searchResult = (ArrayOfHostDatastoreBrowserSearchResults) tInfo
					.getResult();

			int len = searchResult.getHostDatastoreBrowserSearchResults().length;
			for (int j = 0; j < len; j++) {
				HostDatastoreBrowserSearchResults sres = searchResult.HostDatastoreBrowserSearchResults[j];
				FileInfo[] fileArray = sres.getFile();
				if (fileArray == null)
					continue;
				for (int k = 0; k < fileArray.length; k++) {
					File tempe = new File(fileArray[k].getPath());
					fi.add(fileArray[k]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return fi;
	}

	public static VirtualMachine getVMbyName(String vmName) {

		si = VMHelper.getServiceInstance();

		Folder rootFolder = si.getRootFolder();

		ManagedEntity[] mes = null;
		try {
			mes = new InventoryNavigator(rootFolder)
					.searchManagedEntities("VirtualMachine");
		} catch (InvalidProperty e) {
			e.printStackTrace();
		} catch (RuntimeFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (mes == null || mes.length == 0) {
			logger.error("No VM found by the specified name: " + vmName
					+ ", return null");
			return null;
		}

		for (ManagedEntity me : mes) {

			VirtualMachine vm = (VirtualMachine) me;
			if (vm.getName().equalsIgnoreCase(vmName)) {
				return vm;
			}
		}
		logger.error("Unexpect error");
		return null;
	}

	public static boolean isVMExisted(String vmName) {
		VirtualMachine vm = VMUtil.getVMbyName(vmName);
		if (vm != null) {
			return true;
		}
		return false;
	}

	public static List<VirtualMachine> getVMList() {
		List<VirtualMachine> vml = new ArrayList<VirtualMachine>();

		si = VMHelper.getServiceInstance();

		Folder rootFolder = si.getRootFolder();

		ManagedEntity[] mes = null;
		try {
			mes = new InventoryNavigator(rootFolder)
					.searchManagedEntities("VirtualMachine");
		} catch (InvalidProperty e) {
			e.printStackTrace();
		} catch (RuntimeFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (mes == null || mes.length == 0) {
			return vml;
		}

		for (ManagedEntity me : mes) {

			VirtualMachine vm = (VirtualMachine) me;

			vml.add(vm);

		}
		return vml;
	}

	public static List<VirtualMachine> getVMList(String folder)
			throws InvalidProperty, RuntimeFault, RemoteException {
		List<VirtualMachine> vml = new ArrayList<VirtualMachine>();

		si = VMHelper.getServiceInstance();

		Folder rootFolder = si.getRootFolder();

		ManagedEntity[] mes = null;
		try {
			mes = new InventoryNavigator(rootFolder)
					.searchManagedEntities("Folder");
		} catch (InvalidProperty e) {
			e.printStackTrace();
		} catch (RuntimeFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (mes == null || mes.length == 0) {
			return null;
		}

		for (ManagedEntity me : mes) {
			Folder fd = (Folder) me;

			if (fd.getName().equals(folder)) {
				ManagedEntity[] rbcsvm = new InventoryNavigator(fd)
						.searchManagedEntities("VirtualMachine");
				if (rbcsvm == null || rbcsvm.length == 0) {
					return null;
				}
				for (ManagedEntity rbcs : rbcsvm) {
					VirtualMachine vm = (VirtualMachine) rbcs;
					vml.add(vm);
				}
			}

		}
		return vml;
	}

	public static boolean cloneVM(String targetVMName, String srcVMName) {

		ServiceInstance si = VMHelper.getServiceInstance();

		Folder rootFolder = si.getRootFolder();
		try {
			VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
					rootFolder)
					.searchManagedEntity("VirtualMachine", srcVMName);

			if (vm == null) {
				logger.error("No VM " + srcVMName + " found");
				si.getServerConnection().logout();
				return false;
			}

			VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
			VirtualMachineRelocateSpec vmrc = new VirtualMachineRelocateSpec();
			Datacenter dc = (Datacenter) new InventoryNavigator(
					si.getRootFolder()).searchManagedEntity("Datacenter",
					VMFactoryConfigUtil.getVSphereDatacenterName());
			ResourcePool rp = (ResourcePool) new InventoryNavigator(dc)
					.searchManagedEntities("ResourcePool")[0];

			vmrc.setPool(rp.getMOR());
			cloneSpec.setLocation(vmrc);
			cloneSpec.setPowerOn(false);
			cloneSpec.setTemplate(false);

			logger.info("Launching the VM clone task. " + "Please wait ...");
			logger.info("the source vm name is: " + srcVMName);
			logger.info("the cloned vm name is: " + targetVMName);
			Task task = vm.cloneVM_Task((Folder) vm.getParent(), targetVMName,
					cloneSpec);
			String status = task.waitForTask();
			if (status == Task.SUCCESS) {
				logger.info("VM got cloned successfully.");
				return true;
			} else {
				logger.info(task.getTaskInfo().getDescription());
				logger.info("Failure -: VM cannot be cloned");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		{
			if (si != null) {
				si.getServerConnection().logout();
			}
		}

		return false;
	}

	public static boolean removeVM(String vmName)

	{
		VirtualMachine vm = getVMbyName(vmName);
		if (vm == null) {
			logger.error("No VM found by the name: " + vmName);
			return false;
		}

		try {
			Task task = vm.destroy_Task();
			if (task.waitForTask() == Task.SUCCESS) {
				logger.info("successfully delete the VM: " + vmName);
				return true;
			} else {
				logger.error("fail to delete the VM: " + vmName);
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.error("failed to delete teh vm: " + vmName);
		return false;
	}

	public static boolean moveToFinalFileServer(String targetVMLocation) {
		boolean status = execPerl(targetVMLocation);
		if (status == false) {
			logger.error("fail to call the script to move the configured VM to target final server, return false");
			return false;
		}
		return true;
	}

	public static boolean removeConvertFiles(String ovflocation) {

		logger.info("the locaton of file(s) will be deleted: " + ovflocation);
		boolean status = deleteFolder(ovflocation);
		logger.info("delete status is: " + status);
		if (status == false) {
			return false;
		}
		// status = VMUtil.deleteFile(zipOvfFileName);
		return status;
	}

	public static boolean deleteLogFolder(String logpath) {
		return deleteFolder(logpath);
	}

	private static boolean deleteFolder(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		} else {
			if (file.isFile()) {
				return deleteFile(path);
			} else {
				return deleteDirectory(path);
			}
		}
	}

	public static boolean deleteZip(String zipfilepath) {
		return deleteFile(zipfilepath);
	}

	private static boolean deleteFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	private static boolean deleteDirectory(String path) {

		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File dirFile = new File(path);

		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;

		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {

			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;

		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	// clear off option -j, -j may create error when there are files with the
	// same
	// name exists...
	public static boolean zipInstallLog(String zipLogName, String logLocation) {
		logger.info("the dir:" + logLocation + " will be ziped: ");
		logger.info("the zip file name is:" + zipLogName);
		String para1 = "-r";
		String para2 = "-q";

		String zipFileName = zipLogName;
		String pathLocation = logLocation;
		String[] cmd = { "zip", para1, para2, zipFileName, pathLocation };
		logger.info("the cmd line is:" + java.util.Arrays.toString(cmd));
		StringBuffer resultStringBuffer = new StringBuffer();

		String lineToRead = "";

		int exitValue = 0;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);

			InputStream inputStream = proc.getInputStream();
			BufferedReader bufferedRreader = new BufferedReader(
					new InputStreamReader(inputStream));
			// save first line
			if ((lineToRead = bufferedRreader.readLine()) != null) {
				resultStringBuffer.append(lineToRead);
			}
			// save next lines
			while ((lineToRead = bufferedRreader.readLine()) != null) {
				resultStringBuffer.append("\r\n");
				resultStringBuffer.append(lineToRead);
			}
			// Always reading STDOUT first, then STDERR, exitValue last
			proc.waitFor(); // wait for reading STDOUT and STDERR over
			exitValue = proc.exitValue();
		} catch (Exception ex) {
			resultStringBuffer = new StringBuffer("");
			exitValue = 2;
		}
		logger.info("exit:" + exitValue);
		logger.info(resultStringBuffer.toString());
		if (exitValue == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean zipOVF(String zipOvfFileName, String targetVMLocation) {
		logger.info("the dir:" + targetVMLocation + " will be ziped: ");
		logger.info("the zip file name is:" + zipOvfFileName);
		String para1 = "-r";
		String para2 = "-q";
		String para3 = "-j";
		String zipFileName = zipOvfFileName;
		String pathLocation = targetVMLocation;
		String[] cmd = { "zip", para1, para2, para3, zipFileName, pathLocation };
		logger.info("the cmd line is:" + java.util.Arrays.toString(cmd));
		StringBuffer resultStringBuffer = new StringBuffer();

		String lineToRead = "";

		int exitValue = 0;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);

			InputStream inputStream = proc.getInputStream();
			BufferedReader bufferedRreader = new BufferedReader(
					new InputStreamReader(inputStream));
			// save first line
			if ((lineToRead = bufferedRreader.readLine()) != null) {
				resultStringBuffer.append(lineToRead);
			}
			// save next lines
			while ((lineToRead = bufferedRreader.readLine()) != null) {
				resultStringBuffer.append("\r\n");
				resultStringBuffer.append(lineToRead);
			}
			// Always reading STDOUT first, then STDERR, exitValue last
			proc.waitFor(); // wait for reading STDOUT and STDERR over
			exitValue = proc.exitValue();
		} catch (Exception ex) {
			resultStringBuffer = new StringBuffer("");
			exitValue = 2;
		}
		logger.info("exit:" + exitValue);
		logger.info(resultStringBuffer.toString());
		if (exitValue == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean execPerl(String targetVMLocation) {
		String scriptPath = VMFactoryConfigUtil
				.getMoveConfiguredVMToFinalFileServerScript();
		logger.info("the script is: " + scriptPath);
		String user = VMFactoryConfigUtil.getUsernameOffinalFileServer();
		String baseLocationOfFinalServer = VMFactoryConfigUtil
				.getBaseLocationOfFinalFileServer();
		logger.info("the VM will be copied: " + targetVMLocation);
		logger.info("the user name of the file server: " + user);
		logger.info("the base location where the VM will be copied to: "
				+ baseLocationOfFinalServer);

		String[] cmd = { "perl", scriptPath, targetVMLocation, user,
				baseLocationOfFinalServer };
		StringBuffer resultStringBuffer = new StringBuffer();
		String lineToRead = "";
		// get Process to execute perl, get the output and exitValue
		int exitValue = 0;
		try {
			Process proc = Runtime.getRuntime().exec(cmd);

			InputStream inputStream = proc.getInputStream();
			BufferedReader bufferedRreader = new BufferedReader(
					new InputStreamReader(inputStream));
			// save first line
			if ((lineToRead = bufferedRreader.readLine()) != null) {
				resultStringBuffer.append(lineToRead);
			}
			// save next lines
			while ((lineToRead = bufferedRreader.readLine()) != null) {
				resultStringBuffer.append("\r\n");
				resultStringBuffer.append(lineToRead);
			}
			// Always reading STDOUT first, then STDERR, exitValue last
			proc.waitFor(); // wait for reading STDOUT and STDERR over
			exitValue = proc.exitValue();
		} catch (Exception ex) {
			resultStringBuffer = new StringBuffer("");
			exitValue = 2;
		}
		logger.info("exit:" + exitValue);
		logger.info(resultStringBuffer.toString());
		if (exitValue == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean installFile(String vmName, String prodPath,
			String prodType, String adminusername, String adminpassword,
			Integer jobId) {

		logger.info("job Id: " + jobId + ", The product:" + prodPath
				+ ", prodtype is: " + prodType + " will be install on VM:"
				+ vmName);

		ServiceInstance si = VMHelper.getServiceInstance();
		String param = "/qr /noreboot";
		if (prodType.equals(ProductEnumType.sep_amber)
				|| prodType.equals(ProductEnumType.sep_jaguar)) {
			param = " " + "/s";
		}
		String cmd = prodPath + " " + param;
		try {

			VirtualMachine vm = VMUtil.getVMbyName(vmName);
			if (vm == null) {
				logger.error("no VM found by the name: " + vmName);
				return false;
			}

			logger.info("guest tool status:" + vm.getGuest().toolsRunningStatus);
			if (!"guestToolsRunning".equals(vm.getGuest().toolsRunningStatus)) {
				logger.info("The VMware Tools is not running in the Guest OS on VM: "
						+ vm.getName());
				logger.info("Exiting...");
				return false;
			}
			logger.info("install file, vm name" + vmName);
			logger.info("install file, the user is:" + adminusername);
			logger.info("install file, the user is:" + adminpassword);

			GuestProcessDirector progDir = new GuestProcessDirector(vm,
					adminusername, adminpassword);

			long pid = 0;

			logger.info("The installing command is: " + cmd);
			pid = progDir.run(cmd);
			logger.info("pid: " + pid);

			// progDir.killProcess(pid);
			boolean status = checkProcess(progDir, pid);
			logger.info("install status is:" + status);
			downloadInstallLogs(vm, adminusername, adminpassword, jobId);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("fail to install:" + cmd + " in the guest os");
			return false;
		} finally {
			if (si != null) {
				si.getServerConnection().logout();
			}
		}
	}

	public static boolean downloadInstallLogs(VirtualMachine vm,
			String adminusername, String adminPassword, Integer jobId) {

		ServiceInstance si = VMHelper.getServiceInstance();
		try {
			logger.info("begin to download the installing logs to locally file");

			String remoteLogPath = getInstallLogPath(vm, adminusername,
					adminPassword);
			if (remoteLogPath == null) {
				logger.error("Does not find the path contain the installing logs, return");
				return false;
			}
			String localLogPath = VMUtil.getLocalLogPath(jobId.toString());

			GuestFileDirector fd = new GuestFileDirector(vm, adminusername,
					adminPassword);

			logger.info("the remote log dir is:" + remoteLogPath);
			logger.info("the local log dir is:" + localLogPath);

			fd.downloadDirectory(remoteLogPath, localLogPath);

			VMUtil.zipInstallLog(VMUtil.getLocalZippedFile(jobId.toString()),
					localLogPath);
			logger.info("download logs finished");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get exception:" + e.getMessage());
		} finally {
			if (si != null) {
				si.getServerConnection().logout();
			}
		}
		return false;
	}

	public static String getInstallLogPath(VirtualMachine vm,
			String adminusername, String adminPassword) {

		List<String> pathList = VMFactoryConfigUtil
				.getPoentialProductInstallLogPath();

		for (String path : pathList) {
			String logPath = searchProdInstallLogPath(vm, adminusername,
					adminPassword, path);

			if (logPath != null) {
				logger.info("the path is:" + logPath);
				return logPath;
			}
		}
		logger.info("the install log path cannot be found!");
		return null;
	}

	private static String searchProdInstallLogPath(VirtualMachine vm,
			String adminusername, String adminpassword, String searchedPath) {
		// Search the folder and find the folder will match the serached
		// one(store

		if (searchedPath.substring(searchedPath.lastIndexOf("\\") + 1).equals(
				"NortonInstaller")) {
			GuestFileDirector fd = new GuestFileDirector(vm, adminusername,
					adminpassword);
			try {
				logger.info("Checking:" + searchedPath);
				GuestListFileInfo glfi = fd.listFiles(searchedPath);
				GuestFileInfo[] gfi = glfi.getFiles();
				for (GuestFileInfo fi : gfi) {
					if (fi.getPath().equals("Logs")
							&& fi.getType().equals(
									GuestFileType.directory.toString())) {
						return searchedPath + "\\" + "Logs";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
		return null;
	}

	private static List<String> getPathList() {
		List<String> pathList = new ArrayList<String>();
		return pathList;
	}

	public static boolean settingProduct(String prodType, String vmName,
			String adminusername, String adminpassword) {
		if (!(prodType.equals(ProductEnumType.nis2012)
				|| prodType.equals(ProductEnumType.nis2013) || prodType
					.equals(ProductEnumType.n360))) {
			logger.info("Currently, not support to config the product, type:"
					+ prodType + ", return");

			return false;
		}
		// Update prodsetting.exe to the guest OS
		String srcProdSettingToolPath = VMFactoryConfigUtil
				.getProdSettingToolPath();
		String dstProdSettingToolPath = VMFactoryConfigUtil
				.getVMBaseProductUploadLocation()
				+ VMFactoryConfigUtil.getProdSettingToolName();
		boolean status = VMUtil.uploadFile(srcProdSettingToolPath,
				dstProdSettingToolPath, vmName, adminusername, adminpassword);
		if (status == false) {
			logger.error("Fail to upload the prodSetting tool:"
					+ dstProdSettingToolPath + " to the guest os:");
			return false;

		}
		ServiceInstance si = VMHelper.getServiceInstance();

		try {

			VirtualMachine vm = VMUtil.getVMbyName(vmName);
			if (vm == null) {
				logger.error("no VM found by the name: " + vmName);
				return false;
			}

			logger.info("guest tool status:" + vm.getGuest().toolsRunningStatus);
			if (!"guestToolsRunning".equals(vm.getGuest().toolsRunningStatus)) {
				logger.info("The VMware Tools is not running in the Guest OS on VM: "
						+ vm.getName());
				logger.info("Exiting...");
				return false;
			}
			logger.info("settingProduct, vm name" + vmName);
			logger.info("settingProduct, the user is:" + adminusername);
			logger.info("settingProduct, the user is:" + adminpassword);

			GuestProcessDirector progDir = new GuestProcessDirector(vm,
					adminusername, adminpassword);

			long pid = 0;
			Map<String, Integer> prodSettingMap = ProductConfigUtil
					.getProdSettingMap(prodType);

			logger.info("The prodSetting.exe is at :" + dstProdSettingToolPath);
			Iterator it = prodSettingMap.keySet().iterator();
			while (it.hasNext()) {
				String k = (String) it.next();
				Integer v = prodSettingMap.get(k);
				// String param = "/set EID_Email_AntiSpam 0";
				String param = "/set" + " " + k + " " + v;

				String cmd = dstProdSettingToolPath + " " + param;
				logger.info("The prod setting command is: " + cmd);
				pid = progDir.run(cmd);
				logger.info("pid: " + pid);

				// progDir.killProcess(pid);
				status = checkProcess(progDir, pid);
				logger.info("product setting status is:" + status);
				if (status == false) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("fail to configure the product in the guest os");
			return false;
		} finally {
			if (si != null) {
				si.getServerConnection().logout();
			}
		}

		return true;
	}

	public static boolean activateProduct(String prodType, String vmName,
			String adminusername, String adminpassword) {
		if (!(prodType.equals(ProductEnumType.nis2012)
				|| prodType.equals(ProductEnumType.nis2013) || prodType
					.equals(ProductEnumType.n360))) {
			logger.info("Currently, not support to config the product, type:"
					+ prodType + ", return");
			logger.info("Currently, we only support to activate the following products:");
			logger.info(ProductEnumType.nis2012);
			logger.info(ProductEnumType.nis2013);
			logger.info(ProductEnumType.n360);
			return false;
		}

		String atrunnerDstPath = VMFactoryConfigUtil.getATRunnerDstPath();
		String atrunnerSrcPath = VMFactoryConfigUtil.getATRunnerSrcPath();
		String activationScriptPath = VMFactoryConfigUtil
				.getProdActivationScriptPath(prodType);

		boolean status = VMUtil.uploadDirectory(atrunnerDstPath,
				atrunnerSrcPath, vmName, adminusername, adminpassword);

		if (status == false) {
			logger.error("Fail to upload the ATRunner tool:" + atrunnerDstPath
					+ " to the guest os:");
			return false;

		}
		VirtualMachine vm = VMUtil.getVMbyName(vmName);
		if (vm == null) {
			logger.error("no VM found by the name: " + vmName);
			return false;
		}

		long pid = 0;

		String param = "/script:" + activationScriptPath;
		String cmd = atrunnerDstPath + "ATRunner.exe" + " " + param;
		logger.info("The prod activation command is: " + cmd);
		GuestProcessDirector progDir = new GuestProcessDirector(vm,
				adminusername, adminpassword);
		try {
			pid = progDir.run(cmd);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get exception while activate the products!"
					+ e.getMessage());
		}
		logger.info("pid: " + pid);

		status = checkProductActivationLog(vm, adminusername, adminpassword);
		logger.info("Activate product status is:" + status);
		if (status == false) {
			return false;
		}

		return true;
	}

	private static boolean checkProductActivationLog(VirtualMachine vm,
			String adminusername, String adminpassword) {

		boolean isFileExisted = false;
		Integer count = 0;
		GuestFileDirector fd;
		while (isFileExisted == false && count < VMUtil.maxRetryTime) {
			count++;
			logger.info("wait for:" + count * 10 + "s");

			String logPath = getProdActivationLogPath();

			try {

				Thread.sleep(10 * 1000);

				fd = new GuestFileDirector(vm, adminusername, adminpassword);

				GuestListFileInfo lfi = fd.listFiles(logPath);

				GuestFileInfo[] fil = lfi.getFiles();

				for (GuestFileInfo fi : fil) {
					logger.info("Try to find the result file for product activation: "
							+ productActivationReportFile);
					logger.info("the file is:" + fi.getPath());

					if (fi.getPath().equals(productActivationReportFile)) {
						isFileExisted = true;
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
				logger.error("Get error while list the files under the dir:"
						+ logPath);
				return false;
			}

		}
		if (isFileExisted) {

			String filePathInGuest = getProdActivationLogPath()
					+ productActivationReportFile;
			String filePathLocal = VMFactoryConfigUtil
					.getProductInstallLogLocalPath()
					+ File.separator
					+ productActivationReportFile;

			try {
				fd = new GuestFileDirector(vm, adminusername, adminpassword);

				logger.info("try to download log file (from remote) activating the product by ATRunner:"
						+ filePathInGuest);
				logger.info("try to download log file (to local) activating the product by ATRunner:"
						+ filePathLocal);

				fd.downloadFile(filePathInGuest, filePathLocal);

			} catch (IOException e) {
				logger.error("Fail to download the file of activating the product by calling ATRunner");

				e.printStackTrace();
				return false;
			}

			boolean status = validateProductActivationReport(filePathLocal);
			logger.info("remove the local log file:" + filePathLocal);
			// Keep this file for later reference
			// File file = new File(filePathLocal);
			// file.delete();
			return status;
		}
		return false;
	}

	private static boolean validateProductActivationReport(String reportPath) {
		SAXReader sr = new SAXReader();
		try {
			Document doc = sr.read(reportPath);
			Element root = doc.getRootElement();
			Element key = root.element("GetActivatonKey");
			if (key == null) {
				logger.info("have not found the node GetActivatonKey in the report file(generated by ATRunner");
				return false;
			}
			if (key.getText() == null || key.getText().length() == 0) {
				logger.info("There is no key retrieved by ATRunner");
				return false;
			}
			Element status = root.element("Directactivation");

			if (status == null) {
				logger.info("have not found the node Directactivation in the report file(generated by ATRunner");

				return false;
			}
			if (status.getText() == null || status.getText().length() == 0
					|| !status.getText().equals("S_OK")) {
				logger.info("The activat status is not pass(S_OK), but"
						+ status.getText());

				return false;
			}

		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static String getProdActivationLogPath() {
		String logPath;
		String atrunnerDstPath = VMFactoryConfigUtil.getATRunnerDstPath();

		logPath = atrunnerDstPath + "\\Output\\";

		return logPath;

	}

	private static boolean checkProcess(GuestProcessDirector progDir, long pid) {
		GuestProcessInfo info;
		try {
			GuestProcessInfo[] procInfos = progDir
					.listProcesses(new long[] { pid });

			info = procInfos[0];
			int count = 0;
			while (info.getExitCode() == null && count < maxRetryTime) {
				count++;
				logger.info("wait for:" + count * 10 + "s");
				Thread.sleep(10 * 1000);

				logger.info("===================================");
				logger.info("cmdLine:" + info.cmdLine);
				logger.info("startTime:" + info.startTime.getTime());
				Calendar endTime = info.endTime;
				if (endTime != null)
					logger.info("endTime:" + endTime.getTime());

				logger.info("name:" + info.name);
				logger.info("owner:" + info.owner);
				logger.info("exit code: " + info.getExitCode());

				procInfos = progDir.listProcesses(new long[] { pid });
				info = procInfos[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Integer exitCode = info.getExitCode();
		logger.info("exit code is:" + exitCode);
		if (exitCode == 0) {
			return true;

		} else {
			return false;
		}
	}

	public static String getOvfExportLocation(String vmName, Boolean isRBCS) {

		String exportBaseLoc = VMFactoryConfigUtil.getExportedOVFLocation();
		String targetDir = null;
		if (isRBCS == true) {
			targetDir = exportBaseLoc + File.separator
					+ VMFactoryConfigUtil.getBaseDirOfFinalFileServer_RBCS()
					+ File.separator + vmName + File.separator;
		} else {
			targetDir = exportBaseLoc + File.separator
					+ VMFactoryConfigUtil.getBaseDirOfFinalFileServer_nonRBCS()
					+ File.separator + vmName + File.separator;
		}
		logger.info("the exported OVF will be stored locally at: " + targetDir);
		return targetDir;
	}

	public static String getZipOvfFileName(String vmName, Boolean isRBCS) {
		String zipFileName = null;
		if (isRBCS == true) {
			zipFileName = VMFactoryConfigUtil.getExportedOVFLocation()
					+ File.separator
					+ VMFactoryConfigUtil.getBaseDirOfFinalFileServer_RBCS()
					+ File.separator + vmName + ".zip";
		} else {
			zipFileName = VMFactoryConfigUtil.getExportedOVFLocation()
					+ File.separator
					+ VMFactoryConfigUtil.getBaseDirOfFinalFileServer_nonRBCS()
					+ File.separator + vmName + ".zip";
		}

		logger.info("The zipped OVF file: " + zipFileName);

		return zipFileName;

	}

	public static String getLocalLogPath(String jobId) {
		String localLogPath = VMFactoryConfigUtil
				.getProductInstallLogLocalPath();
		return localLogPath + jobId;
	}

	public static String getLocalZippedFile(String jobId) {
		String localLogPath = VMFactoryConfigUtil
				.getProductInstallLogLocalPath();
		return localLogPath + jobId + ".zip";
	}
}
