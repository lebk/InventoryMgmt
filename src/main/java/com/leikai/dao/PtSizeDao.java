package com.leikai.dao;

import com.leikai.po.Ptsize;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface PtSizeDao {

	public boolean addPtSize(String pName);

	public Integer getIdByPtSizeName(String ptSizeName);

	public String getNameByPtSizeId(Integer ptSizeId);

	public Ptsize getPtSizeByPtsizeId(Integer ptSizeId);

	public boolean isPtSizeExisted(String ptSizeName);

}
