package com.lebk.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.action.ProductAction;
import com.lebk.dto.PtDetailsDTO;
import com.lebk.po.Ptdetails;

/**
 * Author: lebk.lei@gmail.com
 * Date: 2014-1-8
 */
public class ProductUtil
{
  static Logger logger = Logger.getLogger(ProductUtil.class);

  public static List<PtDetailsDTO>  convertPtdetailsListToPtdetailsDTOList(List<Ptdetails> pdl)
  {
    List<PtDetailsDTO> ptdt = new ArrayList<PtDetailsDTO>();
    for (Ptdetails pd : pdl)
    {
      logger.info("the pd is:" + pd);
      ptdt.add(new PtDetailsDTO(pd));
      logger.info("the ptdt is:" + ptdt);
    }
    return ptdt;
  }
}
