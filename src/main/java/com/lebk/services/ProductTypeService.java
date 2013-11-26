package com.lebk.services;

import java.util.List;

import com.lebk.po.Pttype;

public interface ProductTypeService
{
  /**
   * Add a new product type, if it existed, then return false.
   * 
   * @param ptType
   * @param opUser
   * @return
   */

  public boolean addPtType(String ptType, String opUser);

  /**
   * Dangous method, if the product type has been used before, then does not
   * allow to delete it.
   * 
   * @param ptType
   * @param opUser
   * @return
   */
  public boolean deletePtType(String ptType, String opUser);

  public boolean deletePtType(Integer ptTypeId, String opUser);

  public List<Pttype> getAllPtType();

  public Integer getIdByPtType(String pttypeName);

  public boolean isUsed(Integer ptTypeId);

}
