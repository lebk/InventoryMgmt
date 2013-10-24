package com.leikai.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.PtColorDao;
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

		if (!this.isPtColorExisted(ptColor)) {
			logger.error("the color: " + ptColor + " should be existed");
			return false;
		}

		Integer id = this.getIdByPtColorName(ptColor);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Ptcolor pc = (Ptcolor) session.get(Ptcolor.class, id);
			session.delete(pc);
			transaction.commit();
			logger.info("delete color: " + ptColor + " successfully");
			return true;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		logger.error("fail to delete color: " + ptColor);

		return false;
	}

	public Integer getIdByPtColorName(String ptcolorName) {
		if (!this.isPtColorExisted(ptcolorName)) {
			logger.error("The Ptcolor with name: " + ptcolorName
					+ " should be existed");
			return null;
		}

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			List pcl = session.createQuery(
					"from " + Ptcolor.class.getName() + " where color='"
							+ ptcolorName + "'").list();

			if (pcl.size() != 1) {
				logger.error("There should be just one ptSizeName existed with name: "
						+ ptcolorName + ", but now there are: " + pcl.size());
				return null;
			}
			for (Iterator iterator = pcl.iterator(); iterator.hasNext();) {
				Ptcolor pc = (Ptcolor) iterator.next();
				logger.info("color: " + ptcolorName + ", id is: " + pc.getId());

				return pc.getId();
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		logger.error("fail to find the record for color name " + ptcolorName);
		return null;
	}

	public String getColorNameByPtColorId(Integer ptcolorId) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			List pcl = session.createQuery(
					"from " + Ptcolor.class.getName() + " where id='"
							+ ptcolorId + "'").list();

			if (pcl.size() != 1) {
				logger.error("There should be just one ptSizeName existed with id: "
						+ ptcolorId + ", but now there are: " + pcl.size());
				return null;
			}
			for (Iterator iterator = pcl.iterator(); iterator.hasNext();) {
				Ptcolor pc = (Ptcolor) iterator.next();
				logger.info("color id: " + ptcolorId + ", the color is: "
						+ pc.getColor());
				return pc.getColor();
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		logger.error("fail to find the record for color id " + ptcolorId);
		return null;
	}

	public Ptcolor getPtColorByPtcolorId(Integer ptcolorId) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			List pcl = session.createQuery(
					"from " + Ptcolor.class.getName() + " where id='"
							+ ptcolorId + "'").list();

			if (pcl.size() != 1) {
				logger.error("There should be just one ptSizeName existed with id: "
						+ ptcolorId + ", but now there are: " + pcl.size());
				return null;
			}
			for (Iterator iterator = pcl.iterator(); iterator.hasNext();) {
				Ptcolor pc = (Ptcolor) iterator.next();
				logger.info("color id: " + ptcolorId + ", the color is: "
						+ pc.getColor());
				return pc;
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		logger.error("fail to find the record for color id " + ptcolorId);
		return null;

	}

	public boolean isPtColorExisted(String ptcolorName) {
		if (ptcolorName == null) {
			logger.error("The ptSizeName should not be null");
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
