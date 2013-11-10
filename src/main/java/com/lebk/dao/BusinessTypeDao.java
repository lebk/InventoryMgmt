package com.lebk.dao;

import java.util.List;

import com.lebk.po.Businesstype;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface BusinessTypeDao
{

  public List<Businesstype> getAllBusinessType();

  public Integer getIdByType(String type);

  public Integer getTypeById(Integer id);
}
