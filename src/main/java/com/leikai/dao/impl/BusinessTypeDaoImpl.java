package com.leikai.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.BusinessTypeDao;
import com.leikai.po.Businesstype;
import com.leikai.po.User;
import com.leikai.util.HibernateUtil;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class BusinessTypeDaoImpl implements BusinessTypeDao {

	public List<Businesstype> getAllBusinessType() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		List bl = new ArrayList<Businesstype>();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			List btq = session.createQuery(
					"from " + Businesstype.class.getName()).list();
			for (Iterator it = btq.iterator(); it.hasNext();) {
				Businesstype bt = (Businesstype) it.next();
				bl.add(bt);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();

		} finally {
			session.close();
		}
		return bl;
	}

}
