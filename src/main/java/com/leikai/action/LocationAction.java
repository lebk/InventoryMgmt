package com.leikai.action;

import java.util.List;

import com.leikai.po.Location;
import com.leikai.services.LocationService;
import com.leikai.services.impl.LocationServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

public class LocationAction extends ActionSupport
{
  LocationService ls = new LocationServiceImpl();

  /*
   * Only admin user can add base target VM Location (Where the configured VM
   * will be stored.
   */
  public boolean addNewBaseTargetVMLocation(String newBaseVMLocation, String uName)
  {
    return ls.addNewBaseTargetVMLocation(newBaseVMLocation, uName);
  }

  /*
   * Only admin user can update base target VM Location (Where the configured VM
   * will be stored.
   */
  public boolean updateBaseTargetVMLocation(String oldBaseVMLocaiton, String newBaseVMLocation, String uName)
  {

    return ls.updateBaseTargetVMLocation(oldBaseVMLocaiton, newBaseVMLocation, uName);
  }

  /*
   * Only admin user can delete base target VM Location (Where the configured VM
   * will be stored.
   */
  public boolean deleteBaseTargetVMLocation(String BaseVMLocation, String uName)
  {

    return ls.deleteBaseTargetVMLocation(BaseVMLocation, uName);
  }

  /*
   * All user can get the base VM Location list.
   */
  public List<Location> getBaseTargetVMLocationList()
  {
    return ls.getBaseTargetVMLocationList();
  }

}
