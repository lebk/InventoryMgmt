package com.lebk.dao;

import java.util.List;

import com.lebk.po.Ptcolor;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface PtColorDao
{

  public boolean addPtColor(String ptColor, Integer opUserId);

  public boolean deletePtColor(String ptColor);

  public Integer getIdByPtColorName(String ptcolorName);

  public String getColorNameByPtColorId(Integer ptcolorId);

  public Ptcolor getPtColorByPtcolorId(Integer ptcolorId);

  public boolean isPtColorExisted(String ptcolorName);

  public List<Ptcolor> getAllPtColor();
}
