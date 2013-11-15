package com.lebk.services;

import java.util.List;

import com.lebk.po.Product;
import com.lebk.po.Pttype;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 * 
 * 2013-10-9
 * 
 */
public interface ProductService
{
  /**
   * 
   * This is the one, to hand ship in/ship out
   * 
   * @param pName
   * @param ptType
   * @param ptColor
   * @param ptSize
   * @param pNum
   * @param businessType
   * @param opUser
   * @return
   */
  public boolean updateProduct(String pName, String ptType, String ptColor, String ptSize, Integer pNum, String businessType, String opUser);

  /**
   * Dangerous method, honest speaking, I d not want to expose this method,
   * currently, only admin user can call it.
   * 
   * @param opUser
   * @return
   */
  public boolean deleteAllProduct(String opUser);

  /**
   * This method is used to get all the product in our store.
   * 
   * @return
   */
  public List<Pttype> getAllProductList();

}
