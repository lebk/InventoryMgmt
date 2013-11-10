package com.lebk.services;

import java.util.List;

import com.lebk.po.Pttype;

public interface PtTypeService
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

  public List<Pttype> getAllPtType();

}
