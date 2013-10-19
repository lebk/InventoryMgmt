package com.leikai.dao;

import com.leikai.po.Ptcolor;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public interface PtColorDao {

	public boolean addPtColor(String ptColor);

	public Integer getIdByPtColorName(String ptcolorName);

	public String getNameByPtColorId(Integer ptcolorId);

	public Ptcolor getPtcolorByPtcolorId(Integer ptcolorId);

	public boolean isPtcolorExisted(String ptcolorName);
}
