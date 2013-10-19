package com.leikai.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.ProductTypeDao;
import com.leikai.po.Businesstype;
import com.leikai.po.Producttype;
import com.leikai.util.HibernateUtil;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class ProductTypeDaoImpl implements ProductTypeDao {

	public boolean addProductType(String pdType) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteProductTpe(String pdType) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Producttype> getAllProductType() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List ptl = new ArrayList<Producttype>();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			List btq = session.createQuery(
					"from " + Producttype.class.getName()).list();
			for (Iterator it = btq.iterator(); it.hasNext();) {
				Producttype bt = (Producttype) it.next();
				ptl.add(bt);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}
		return ptl;
	}

	public Integer getIdByProductType(String pdType) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNameByPtTypeId(Integer pdTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Producttype getProductTypeByPtTypeId(Integer ptTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPtTypeExisted(String ptTypeName) {
		// TODO Auto-generated method stub
		return false;
	}

}
