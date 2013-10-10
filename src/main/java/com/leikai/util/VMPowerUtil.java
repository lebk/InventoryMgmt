package com.leikai.util;

import org.apache.log4j.Logger;

import com.vmware.vim25.TaskInfoState;
import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class VMPowerUtil
{
  private static Logger logger = Logger.getLogger(VMPowerUtil.class);
  private static int maxCount = 200;
  private static int sleepTimeInMills = 5 * 1000;

  /*
   * Power on the VM and wait for the server is ready to login.
   */
  private static String powerOn(String vmName)
  {
    ServiceInstance si = VMHelper.getServiceInstance();

    Folder rootFolder = si.getRootFolder();
    try
    {
      VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
      Task task = vm.powerOnVM_Task(null);

      String status = task.waitForTask();
      logger.info("Power status is: " + status);
      return status;
    } catch (Exception e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
    } finally
    {
      if (si != null)
      {
        si.getServerConnection().logout();
      }
    }
    logger.error("FALURE to power on the vm: " + vmName);
    return TaskInfoState.error.toString();

  }

  /*
   * Power on the VM and wait for the server is ready to login.
   */
  public static boolean powerOnTillAvail(String vmName)
  {
    ServiceInstance si = VMHelper.getServiceInstance();

    Folder rootFolder = si.getRootFolder();
    try
    {
      VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
      logger.info("Begin to power on VM:" + vmName);

      if (vm == null)
      {
        logger.error("No vm found by the name: " + vmName);
        return false;
      }

      Task task = vm.powerOnVM_Task(null);

      boolean status = waitTillVMAvail(vm);
      if (status == false)
      {
        logger.info("fail to power on the VM:" + vmName);
        return false;
      }
      String taskStatus = task.waitForTask();
      logger.info("Power on status is: " + taskStatus);
      if (taskStatus.equalsIgnoreCase(TaskInfoState.success.toString()))
      {
        return true;
      }
      // return status;
    } catch (Exception e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
    } finally
    {
      if (si != null)
      {
        si.getServerConnection().logout();
      }
    }
    logger.error("FALURE to power on the vm: " + vmName);
    return false;

  }

  /*
   * Power on the VM and wait for the server is ready to login.
   */
  public static String powerOff(String vmName)
  {
    ServiceInstance si = VMHelper.getServiceInstance();

    Folder rootFolder = si.getRootFolder();
    try
    {
      VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
      Task task = vm.powerOffVM_Task();

      String status = task.waitForTask();
      logger.info("Power off status is: " + status);
      return status;
    } catch (Exception e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
    } finally
    {
      if (si != null)
      {
        si.getServerConnection().logout();
      }
    }
    logger.error("FALURE to power off the vm: " + vmName);
    return TaskInfoState.error.toString();

  }

  /*
   * Stop the service and shutdown the OS.
   */
  public static boolean shutdown(String vmName)
  {
    ServiceInstance si = VMHelper.getServiceInstance();

    Folder rootFolder = si.getRootFolder();
    int count = 0;
    try
    {
      VirtualMachine vm = (VirtualMachine) new InventoryNavigator(rootFolder).searchManagedEntity("VirtualMachine", vmName);
      logger.info("Begin to shutdown VM:" + vmName);

      vm.shutdownGuest();
      while (!vm.getRuntime().getPowerState().toString().equals(VirtualMachinePowerState.poweredOff.toString()) && count < maxCount)
      {

        Thread.sleep(sleepTimeInMills);
        count++;
        logger.info("wait: " + count * sleepTimeInMills / 1000 + "s");

      }
      // If not shutdown in the specific time, we will call powerOfF to power it
      // down directly
      if (count >= maxCount)
      {
        logger.warn("VM: " + vm.getName() + " is failure to shutdown in + " + 5 * maxCount);
        VMPowerUtil.powerOff(vmName);
        return true;
      } else
      {
        logger.info("VM:" + vmName + " is shutdown successfully");
        return true;
      }

    } catch (Exception e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
    } finally
    {
      if (si != null)
      {
        si.getServerConnection().logout();
      }
    }
    return false;
  }

  private static boolean waitTillVMAvail(VirtualMachine vm)
  {
    boolean status = waitIPAddressAvail(vm);
    // Should have a better solution about the init is ready.
    try
    {
      Thread.sleep(10000);
    } catch (InterruptedException e)
    {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
    return status;
  }

  private static boolean waitIPAddressAvail(VirtualMachine vm)

  {
    int count = 0;

    while (vm.getGuest().getIpAddress() == null && count < maxCount)
    {
      try
      {
        Thread.sleep(sleepTimeInMills);
        count++;
        logger.info("wait: " + count * sleepTimeInMills / 1000 + "s");

      } catch (InterruptedException e)
      {
        e.printStackTrace();
        return false;
      }
    }

    if (count >= maxCount)
    {
      logger.error("VM: " + vm.getName() + " is failure to bootup in + " + 5 * maxCount);
      return false;
    }
    String ip = vm.getGuest().getIpAddress();
    logger.info("ip address is:" + ip);
    if (ip != null)
    {
      return true;
    } else
    {
      return false;
    }
  }
}
