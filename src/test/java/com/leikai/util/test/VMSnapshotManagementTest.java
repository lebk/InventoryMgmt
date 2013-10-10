package com.leikai.util.test;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.leikai.util.VMFactoryConfigUtil;
import com.leikai.util.VMSnapshotManagement;
import com.leikai.vmguest.GuestFileDirector;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

import com.vmware.vim25.GuestProgramSpec;
import com.vmware.vim25.NamePasswordAuthentication;
import com.vmware.vim25.mo.GuestAuthManager;
import com.vmware.vim25.mo.GuestOperationsManager;
import com.vmware.vim25.mo.GuestProcessManager;


public class VMSnapshotManagementTest
{

  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Ignore
  @Test
  public void testVMSnapshotmanager() throws Exception
  {

    String username = VMFactoryConfigUtil.getvSphereUsername();
    String userpassword = VMFactoryConfigUtil.getvSpherePassword();
    String vsphereHttpSdkUrl = VMFactoryConfigUtil.getvSphereHttpSdkUrl();

    ServiceInstance si = new ServiceInstance(new URL(vsphereHttpSdkUrl), username, userpassword, true);
    Folder rootFolder = si.getRootFolder();

    ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
    if (mes == null || mes.length == 0)
    {
      return;
    }

    VirtualMachine vm = (VirtualMachine) mes[0];

    System.out.println("guest tool status:" + vm.getGuest().toolsRunningStatus);
    if (!"guestToolsRunning".equals(vm.getGuest().toolsRunningStatus))
    {
      System.out.println("The VMware Tools is not running in the Guest OS on VM: " + vm.getName());
      System.out.println("Exiting...");
      return;
    }

    VMSnapshotManagement.createSnapshot(vm, "basic", "this is the basic snapshot");

    GuestFileDirector fd = new GuestFileDirector(vm, "Administrator", "vijava");

    fd.uploadDirectory("C:\\temp\\automation\\AutoTask", "c:\\AutoTask");
    System.out.println("upload ATRunner Kit is done");

    fd.uploadDirectory("C:\\temp\\nis2012", "c:\\nis2012");
    System.out.println("upload nis2012 is done");

    VMSnapshotManagement.listSnapshot(vm);

    GuestOperationsManager gom = si.getGuestOperationsManager();
    GuestAuthManager gam = gom.getAuthManager(vm);
    NamePasswordAuthentication npa = new NamePasswordAuthentication();
    npa.username = "Administrator";
    npa.password = "vijava";
    GuestProgramSpec spec = new GuestProgramSpec();
    spec.programPath = "C:\\automation\\AutoTask\\ATRunner.exe";
    spec.arguments = "/script:Activation.ats";

    GuestProcessManager gpm = gom.getProcessManager(vm);
    long pid = gpm.startProgramInGuest(npa, spec);

    //
    // WORKS TO DO to check if all the configuration is done, i.e. product
    // setting is done, product activation is done, etc...
    //

    //
    // revert back to basic...
    //
    VMSnapshotManagement.revertSnapshot(vm, "basic");
    System.out.println("ATRunner exec done");

  }

}
