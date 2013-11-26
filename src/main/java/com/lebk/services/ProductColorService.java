package com.lebk.services;

import java.util.List;

import com.lebk.po.Ptcolor;
import com.lebk.po.Pttype;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-12
 */

public interface ProductColorService
{
  /**
   * 
   * @param ptColor
   * @param opUser
   * @return
   */
  public boolean addPtColor(String ptColor, String opUser);

  /**
   * 
   * Dangous method, if the product type has been used before, then does not
   * allow to delete it.
   * 
   * @param ptColor
   * @param opUser
   * @return
   */
  public boolean deletePtColor(String ptColor, String opUser);

  public boolean deletePtColor(Integer ptColorId, String opUser);

  public List<Ptcolor> getAllPtColor();

  public Integer getIdByPtColor(String ptcolorName);

  public Boolean isUsed(Integer ptColorId);
}
