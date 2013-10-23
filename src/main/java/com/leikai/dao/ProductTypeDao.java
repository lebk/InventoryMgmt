package com.leikai.dao;

import java.util.List;

import com.leikai.po.Producttype;
import com.leikai.po.Ptcolor;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface ProductTypeDao {

	public boolean addProductType(String pdType);

	public boolean deleteProductType(String pdType);

	public List<Producttype> getAllProductType();

	public Integer getIdByProductType(String pdType);

	public String getNameByPtTypeId(Integer pdTypeId);

	public Producttype getProductTypeByPtTypeId(Integer ptTypeId);

	public boolean isPtTypeExisted(String ptTypeName);

}
