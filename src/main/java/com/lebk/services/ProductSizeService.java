package com.lebk.services;

import java.util.List;

import com.lebk.po.Ptcolor;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-12
 */

public interface ProductSizeService
{
  public boolean addPtColor(String ptColor, String opUser);

  public boolean deletePtColor(String ptColor, String opUser);

  public List<Ptcolor> getAllPtColor();

}
