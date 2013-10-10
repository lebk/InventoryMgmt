package com.leikai.dao;

import java.util.ArrayList;
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

public interface LocationDao
{

  public boolean addNewBaseTargetVMLocation(String newBaseVMLocation);

  public boolean updateBaseTargetVMLocation(String oldBaseVMLocaiton, String newBaseVMLocation);

  public boolean deleteBaseTargetVMLocation(String BaseVMLocation);

  public List<Location> getBaseVMLocationList();

}
