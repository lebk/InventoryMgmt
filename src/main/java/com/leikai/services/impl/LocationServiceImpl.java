package com.leikai.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.leikai.dao.LocationDao;
import com.leikai.dao.impl.LocationDaoImpl;
import com.leikai.dao.impl.UserDaoImpl;
import com.leikai.po.Location;
import com.leikai.services.LocationService;
import com.leikai.services.UserService;
import com.leikai.util.VMFactoryConfigUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class LocationServiceImpl implements LocationService
{

  private UserService us = new UserServiceImpl();
  private LocationDao ld = new LocationDaoImpl();
  static Logger logger = Logger.getLogger(LocationServiceImpl.class);

  /*
   * Only admin user can add base target VM Location (Where the configured VM
   * will be stored.
   */
  public boolean addNewBaseTargetVMLocation(String newBaseVMLocation, String opUser)
  {
    if (!us.isUserAdmin(opUser))
    {
      logger.error("Only admin user can add the base Location");
      return false;
    }

    return ld.addNewBaseTargetVMLocation(newBaseVMLocation);

  }

  /*
   * Only admin user can update base target VM Location (Where the configured VM
   * will be stored.
   */
  public boolean updateBaseTargetVMLocation(String oldBaseVMLocaiton, String newBaseVMLocation, String opUser)
  {
    if (!us.isUserAdmin(opUser))
    {
      logger.error("Only admin user can update the base Location");
      return false;
    }

    return ld.addNewBaseTargetVMLocation(newBaseVMLocation);
  }

  /*
   * Only admin user can delete base target VM Location (Where the configured VM
   * will be stored.
   */
  public boolean deleteBaseTargetVMLocation(String BaseVMLocation, String opUser)
  {
    if (!us.isUserAdmin(opUser))
    {
      logger.error("Only admin user can add the base Location");
      return false;
    }

    return ld.deleteBaseTargetVMLocation(BaseVMLocation);
  }

  public List<Location> getBaseTargetVMLocationList()
  {
    List<Location> retLocList = new ArrayList<Location>();
    String baseTargetVMLocation = VMFactoryConfigUtil.getBaseLocationOfFinalFileServer();
    logger.info("the base target to store the configured VM is at:" + baseTargetVMLocation);
    List<Location> existedBaseVMLoc = ld.getBaseVMLocationList();
    if (!existedBaseVMLoc.contains(baseTargetVMLocation))
    {
      ld.addNewBaseTargetVMLocation(baseTargetVMLocation);
      retLocList = ld.getBaseVMLocationList();

    }
    return retLocList;

  }

  public String getBaseProductLocation()
  {

    return VMFactoryConfigUtil.getBaseProductLocation();
  }

}
