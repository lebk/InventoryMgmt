package com.leikai.dao;

import java.util.List;

import com.leikai.po.Ptdetails;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-7
 */

public interface PtDetailsDao
{
  public boolean addPtDetail(Integer poId, Integer btId, Integer pNum, Integer opUserId);

  public boolean deletePtDetail(Integer id);

  public List<Ptdetails> getAllPtDetails();
}
