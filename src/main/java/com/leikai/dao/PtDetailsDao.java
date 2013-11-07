package com.leikai.dao;

import java.util.List;

import com.leikai.po.Ptdetails;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-7
 */

interface PtDetailsDao
{
  public boolean addPtDetail(Integer poId, Integer btId, Integer opUserId);

  public boolean deletePtDetail();

  public List<Ptdetails> getAllPtDetails();
}
