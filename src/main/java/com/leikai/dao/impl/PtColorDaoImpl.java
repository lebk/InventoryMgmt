package com.leikai.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.PtColorDao;
import com.leikai.po.Product;
import com.leikai.po.Ptcolor;
import com.leikai.util.HibernateUtil;

public class PtColorDaoImpl implements PtColorDao {
	static Logger logger = Logger.getLogger(PtColorDaoImpl.class);

	public boolean addPtColor(String ptColor) {

		if (this.isPtColorExisted(ptColor)) {
			logger.warn("the color " + ptColor + " is already existed");
			return false;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;

		try {
			logger.info("begin to add product color: " + ptColor);

			transaction = session.beginTransaction();
			Ptcolor pc = new Ptcolor();

			pc.setColor(ptColor);
			session.save(pc);
			transaction.commit();
			logger.info("add product color successfully");
			return true;

		} catch (HibernateException e) {

			transaction.rollback();
			logger.error(e.toString());
			e.printStackTrace();

		} finally {

			session.close();

		}
		return false;
	}

	public boolean deletePtColor(String ptColor) {

		return false;
	}

	public Integer getIdByPtColorName(String ptcolorName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getColorNameByPtColorId(Integer ptcolorId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Ptcolor getPtColorByPtcolorId(Integer ptcolorId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPtColorExisted(String ptcolorName) {
		if (ptcolorName == null) {
			logger.error("The ptcolorName should not be null");
			return true;
		}

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			List pl = session.createQuery(
					"from " + Ptcolor.class.getName() + " where color='"
							+ ptcolorName + "'").list();

			if (pl.size() >= 1) {
				logger.info("The color " + ptcolorName + " is already existed");
				return true;
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		logger.info("The color " + ptcolorName + " is not existed");
		return false;
	}

}
