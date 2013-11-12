package com.lebk.dao;

import java.util.List;

import com.lebk.po.Ptsize;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface PtSizeDao {

	public boolean addPtSize(String ptSizeName, Integer opUserId);

	public boolean deletePtSize(String ptSizeName);

	public Integer getIdByPtSizeName(String ptSizeName);

	public String getNameByPtSizeId(Integer ptSizeId);

	public Ptsize getPtSizeByPtsizeId(Integer ptSizeId);

	public boolean isPtSizeExisted(String ptSizeName);

	public List<Ptsize> getAllPtSize();
}
