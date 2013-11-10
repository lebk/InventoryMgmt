package com.lebk.dao;

import java.util.List;

import com.lebk.po.Pttype;
import com.lebk.po.Ptcolor;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface PtTypeDao
{

  public boolean addPtType(String ptType, Integer opUserId);


  public boolean deletePtType(String ptType);

  public List<Pttype> getAllPtType();

  public Integer getIdByPtType(String ptTypeName);

  public String getNameByPtTypeId(Integer pdTypeId);

  public Pttype getPtTypeByPtTypeId(Integer ptTypeId);

  public boolean isPtTypeExisted(String ptTypeName);

}
