package com.leikai.services.impl;

import java.util.List;

import com.leikai.po.Os;
import com.leikai.services.OsService;

public class OsServiceImpl implements OsService
{

	public List<Os> getOsListFromDB() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getOsListFromInventory(String opUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getOsListFromInventory(String opUser, String folder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getExtraOsListFromInventory(String opUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getExtraOsListFromInventory(String opUser, String folder) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeOs(String osName, String opUser) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateOs(String osName, String adminUser,
			String adminPassword, String opUser) {
		// TODO Auto-generated method stub
		return false;
	}

	public Integer getIdByOsName(String osName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addOs(String name, String adminname, String adminpassword,
			Boolean isRBCS, String opUser) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getOsNameById(Integer osId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Os getOsbyOsId(Integer osId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Os> getOsListFilterByProductId(Integer prodId) {
		// TODO Auto-generated method stub
		return null;
	}

}
