package com.leikai.services;

import java.util.List;

import com.leikai.po.Location;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public interface LocationService
{
  /**
   * 
   * @param newBaseVMLocation
   * @param opUser
   * @return
   * 
   *         Only admin user can add base target VM Location (Where the
   *         configured VM will be stored.
   */
  public boolean addNewBaseTargetVMLocation(String newBaseVMLocation, String opUser);

  /**
   * 
   * @param oldBaseVMLocaiton
   * @param newBaseVMLocation
   * @param opUser
   * @return
   * 
   *         Only admin user can update base target VM Location (Where the
   *         configured VM will be stored.
   */
  public boolean updateBaseTargetVMLocation(String oldBaseVMLocaiton, String newBaseVMLocation, String opUser);

  /**
   * 
   * @param BaseVMLocation
   * @param opUser
   * @return
   * 
   *         Only admin user can delete base target VM Location (Where the
   *         configured VM will be stored.
   */
  public boolean deleteBaseTargetVMLocation(String BaseVMLocation, String opUser);

  /**
   * 
   * @return
   * 
   *         All user can get the base VM Location list.
   */
  public List<Location> getBaseTargetVMLocationList();

  /**
   * 
   * @return
   * 
   *         The base product location will be configured in the properites file
   *         instead of in DB.
   * 
   */

  public String getBaseProductLocation();
}
