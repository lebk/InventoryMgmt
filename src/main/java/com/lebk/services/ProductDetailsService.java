package com.lebk.services;

import java.util.List;

import com.lebk.po.Ptdetails;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-17
 */

public interface ProductDetailsService
{

  List<Ptdetails> getProductDetailsByProductId(Integer poId);

  List<Ptdetails> getAllProductDetails();

}
