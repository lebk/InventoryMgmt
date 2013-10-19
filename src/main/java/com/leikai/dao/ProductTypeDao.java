package com.leikai.dao;

import com.leikai.po.Ptcolor;

/**
 * Copyright: All Right Reserved. 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface ProductTypeDao {

	public boolean addProductType(String pdType);

	public Integer getIdByProductType(String pdType);

	public String getNameByPtTypeId(Integer pdTypeId);

	public Ptcolor getProductTypeByPtTypeId(Integer ptTypeId);

	public boolean isPtTypeExisted(String ptTypeName);
	
}


