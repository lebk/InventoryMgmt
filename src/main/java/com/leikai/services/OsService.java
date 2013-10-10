package com.leikai.services;

import java.util.List;

import com.leikai.po.Os;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

public interface OsService
{
  /**
   * 
   * @return
   * 
   *         Everybody can call this method, it will return the current availabe
   *         in our DB
   */
  public List<Os> getOsListFromDB();

  /**
   * 
   * @param opUser
   * @return
   * 
   *         admin privilege required.
   */
  public List<String> getOsListFromInventory(String opUser);
  /**
   * 
   * @param opUser
   * @param folder
   * @return
   * 
   *         admin privilege required.
   */
  public List<String> getOsListFromInventory(String opUser, String folder);
  /**
   * 
   * @param opUser
   * @return
   * 
   *         admin privilege required.
   * 
   *         This method is used to retrieve the extra os list in the Inventory,
   *         only the OS exists in the inventory other than the db will be
   *         returned
   */
  public List<String> getExtraOsListFromInventory(String opUser);
  /**
   * 
   * @param opUser
   * @parma folder
   * @return
   * 
   *         admin privilege required.
   * 
   *         This method is used to retrieve the extra os list in the Inventory according to the folder,
   *         only the OS exists in the inventory other than the db will be
   *         returned
   */
  public List<String> getExtraOsListFromInventory(String opUser, String folder);
  /**
   * 
   * @param osName
   * @param uName
   * @return
   * 
   *         This method will remove the specfied os, only admin user can do
   *         this.
   * 
   */
  public boolean removeOs(String osName, String opUser);

  /**
   * 
   * @param osName
   *          , the name of the os
   * @param adminUser
   * @param adminPassword
   * @param opUser
   *          , the user who will update the OsName, only admin user is allow to
   * @return
   * 
   *         This method is used to update the os, only admin user can update
   *         that, if update successfully, return true, otherwise, return false.
   */
  public boolean updateOs(String osName, String adminUser, String adminPassword, String opUser);

  /**
   * 
   * @param osName
   * @return
   * 
   *         Retrieve the OS id by the osName, only user can call this operation
   */
  public Integer getIdByOsName(String osName);

  /**
   * 
   * @param name
   * @param adminname
   *          , the default login user name for the uploaded template (the
   *          default value is configured in: vmfactoryCofig.properties file)
   * @param adminpassword
   * @param isRBCS
   *          ,this param. is used to track whether the added template is
   *          belonged to RBCS or not, by default, the value is false.
   * @param opUser
   * @return
   * 
   *         Any admin user can add the os, this method will not be called by
   *         the web ui.
   */
  public boolean addOs(String name, String adminname, String adminpassword, Boolean isRBCS, String opUser);

  /**
   * 
   * @param osId
   * @return
   */
  public String getOsNameById(Integer osId);

  /**
   * 
   * @param osId
   * @return
   * 
   *         return the os object by osId
   */
  public Os getOsbyOsId(Integer osId);

  /**
   * 
   * @param prodId
   * @return This method will return all the Oses which is able to install the
   *         specific productId
   */
  public List<Os> getOsListFilterByProductId(Integer prodId);
}
