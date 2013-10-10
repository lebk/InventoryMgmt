package com.leikai.util.test;

import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.leikai.util.VMFactoryConfigUtil;

import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;

import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

public class VMGetVMFromFolderTest
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

    
    ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("Folder");
    if (mes == null || mes.length == 0)
    {
      return;
    }
    
    for (ManagedEntity me : mes)
    {

      Folder fd = (Folder) me;

      if (fd.getName().equals("RBCS"))
      {
        System.out.println("RBCS is Found...");
        ManagedEntity[] rbcsvm = new InventoryNavigator(fd).searchManagedEntities("VirtualMachine");
        if (mes == null || mes.length == 0)
        {
          return;
        }
        for (ManagedEntity m : rbcsvm)
        {
          VirtualMachine vm = (VirtualMachine)m;
          System.out.println(vm.getName());
        }
      }

    }

  }

}
