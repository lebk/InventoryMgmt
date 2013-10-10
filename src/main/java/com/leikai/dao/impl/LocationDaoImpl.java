package com.leikai.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.LocationDao;
import com.leikai.po.Location;
import com.leikai.util.HibernateUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

public class LocationDaoImpl implements LocationDao
{
  static Logger logger = Logger.getLogger(LocationDaoImpl.class);
  public static final int LOCATIONTYPE_ = 0;
  private String targetVmbaseLocation = "targetVmbaseLocation";

  public boolean addNewBaseTargetVMLocation(String newBaseVMLocation)
  {

    if (this.isLoationExisted(newBaseVMLocation))
    {
      logger.error("The base location: " + newBaseVMLocation + " is already existed");
      return false;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {

      transaction = session.beginTransaction();
      Location loc = new Location();
      // TODO, should retrieve the location type by the specific location type.
      loc.setType(targetVmbaseLocation);
      loc.setUrl(newBaseVMLocation);
      session.save(loc);
      transaction.commit();
      return true;

    } catch (HibernateException e)
    {

      transaction.rollback();
      logger.error(e.toString());
      e.printStackTrace();

    } finally
    {

      session.close();

    }
    logger.error("The Loaction: " + newBaseVMLocation + " is added failed");
    return false;
  }

  public boolean updateBaseTargetVMLocation(String oldBaseVMLocaiton, String newBaseVMLocation)
  {
    Integer locationId = getIdByLocationName(oldBaseVMLocaiton);
    if (locationId == null)
    {
      logger.error("no location found by the specifed name: " + oldBaseVMLocaiton);
      return false;
    }
    if (this.isLoationExisted(newBaseVMLocation))
    {
      logger.error("there is arlready one existed location the new base location:  " + newBaseVMLocation);
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Location loc = (Location) session.get(Location.class, locationId);
      loc.setUrl(newBaseVMLocation);
      transaction.commit();
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return false;
  }

  public boolean deleteBaseTargetVMLocation(String baseVMLoc)
  {

    if (!this.isLoationExisted(baseVMLoc))
    {
      logger.error("the baseVMLoc: " + baseVMLoc + " should be existed");
      return false;
    }

    Integer id = this.getIdByLocationName(baseVMLoc);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Location loc = (Location) session.get(Location.class, id);
      session.delete(loc);
      transaction.commit();
      return true;
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return false;

  }

  private Integer getIdByLocationName(String locName)
  {

    if (!this.isLoationExisted(locName))
    {
      logger.error("localName: " + locName + "should be there");
      return null;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List lnl = session.createQuery("from Location where url='" + locName + "'").list();
      if (lnl.size() != 1)
      {
        logger.error("There should be just one location existed with name: " + locName + ", but now there are: " + lnl.size());
        return null;
      }
      for (Iterator it = lnl.iterator(); it.hasNext();)
      {
        Location loc = (Location) it.next();
        logger.info("location name: " + locName + ", id is: " + loc.getId());
        return loc.getId();
      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return null;

  }

  public List<Location> getBaseVMLocationList()
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List ll = new ArrayList<Location>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Location").list();
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Location loc = (Location) it.next();
        logger.info("location: " + loc.getType() + ", " + loc.getUrl());
        ll.add(loc);
      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
    return ll;
  }

  private boolean isLoationExisted(String url)
  {

    if (url == null)
    {
      logger.error("the url is null");
      return false;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;
    boolean status = false;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from Location where url='" + url + "'").list();
      if (ul.size() == 1)

      {
        status = true;
        return status;
      }

      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }

    return status;

  }

}
