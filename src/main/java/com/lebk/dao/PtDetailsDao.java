package com.lebk.dao;

import java.util.List;

import com.lebk.po.Ptdetails;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-7
 */

public interface PtDetailsDao
{
  public boolean addPtDetail(Integer poId, Integer btId, Integer pNum, Integer opUserId);

  public boolean deletePtDetail(Integer id);

  public boolean deletePtDetialByPoId(Integer poId);

  public boolean cleanUpAll();

  public List<Ptdetails> getAllPtDetails();

  public List<Ptdetails> getAllPtDetailsbyPoId(Integer poId);
}
