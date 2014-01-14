package com.lebk.dao;

import java.util.Date;
import java.util.List;

import com.lebk.po.Ptdetails;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-7
 */

public interface PtDetailsDao
{
  // Add new record for the product ship details, if product produced and store
  // in the wareshouse, then the btId is 1(in), if ship out(sell), the btId is
  // 2(out), will use btId to distingish product in or out, other than use the
  // postive/negative product number(pNum)
  public boolean addPtDetail(Integer poId, Integer btId, Float pNum, Integer opUserId);

  public boolean deletePtDetail(Integer id);

  public boolean deletePtDetialByPoId(Integer poId);

  /**
   * Dangerous method, never call it, just for test purpose
   * 
   * @return
   */
  public boolean cleanUpAll();

  public List<Ptdetails> getAllPtDetailsbyPoId(Integer poId);

  public List<Ptdetails> searchProductDetails(List<Integer> poIdList, Date startDate, Date endDate);

  public List<Ptdetails> getAllPtDetails();

}
