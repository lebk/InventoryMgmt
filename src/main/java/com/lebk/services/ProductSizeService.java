package com.lebk.services;

import java.util.List;

import com.lebk.po.Ptcolor;
import com.lebk.po.Ptsize;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-12
 */

public interface ProductSizeService
{
  public Boolean addPtSize(String ptSize, String opUser);

  public Boolean deletePtSize(String ptSize, String opUser);

  public Boolean deletePtSize(Integer ptSizeId, String opUser);

  public List<Ptsize> getAllPtSize();

  public Integer getIdByPtSize(String ptsizeName);

  public Boolean isUsed(Integer ptSizeId);

}
