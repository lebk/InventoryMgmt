package com.leikai.services.impl;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.leikai.dao.OsDao;
import com.leikai.dao.impl.OsDaoImpl;
import com.leikai.dao.impl.ProductDaoImpl;
import com.leikai.po.Os;
import com.leikai.po.Product;
import com.leikai.services.OsService;
import com.leikai.services.ProductService;
import com.leikai.services.UserService;
import com.leikai.util.VMFactoryConfigUtil;
import com.leikai.util.VMUtil;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.VirtualMachine;

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
