package com.leikai.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.OsDao;
import com.leikai.po.Os;
import com.leikai.util.HibernateUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

public class OsDaoImpl implements OsDao
{
  static Logger logger = Logger.getLogger(OsDaoImpl.class);

  public List<Os> getOsList()
  {
    List ol = new ArrayList<Os>();

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ql = session.createQuery("from Os").list();
      for (Iterator it = ql.iterator(); it.hasNext();)
      {
        Os o = (Os) it.next();
        logger.info("os: " + o.getName());
        if (this.isOsValid(o.getName()))
        {
          ol.add(o);
        }
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
    return ol;
  }

  public boolean removeOs(String osName)
  {

    if (this.isOsExisted(osName) == false)
    {
      logger.error("the os: " + osName + " should be existed");
      return false;
    } else
    {
      if (this.isOsValid(osName) == false)
      {
        logger.warn("This Os has been delete before, return false");
        return false;
      }
      this.markOsValid(osName, false);
      return true;
    }

  }

  public boolean updateOs(String osName, String adminUser, String adminPassword)
  {
    // TODO, this implement has some issue (but in the web ui, it is ok).
    Integer osId = getIdByOsName(osName);
    if (osId == null)
    {
      logger.error("no Os found by the specifed name: " + osName);
      return false;
    }
    if (adminUser == null || adminUser.equals("") || adminPassword == null || adminPassword.equals(""))
    {
      logger.error("The admin user name or admin password should not be null or empty. adminUser= " + adminUser + ", adminPassword=" + adminPassword);
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Os o = (Os) session.get(Os.class, osId);
      o.setAdminname(adminUser);
      o.setAdminpassword(adminPassword);
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

  public boolean addOs(String name, String adminname, String adminpassword, Boolean isRBCS)
  {

    if (this.isOsExisted(name) == true)
    {
      if (this.isOsValid(name) == true)
      {
        logger.error("The Os:" + name + " is already existed");
        return false;
      } else
      {
        markOsValid(name, true);
        return true;
      }
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {

      transaction = session.beginTransaction();
      Os o = new Os();
      o.setName(name);
      o.setAdminname(adminname);
      o.setAdminpassword(adminpassword);
      o.setAddTime(new Date());
      o.setIsValid(true);
      o.setIsRBCS(isRBCS);
      session.save(o);
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
    logger.error("The Os: " + name + " is added failed");
    return false;
  }

  private boolean isOsExisted(String name)
  {
    if (name == null)
    {
      logger.error("the name is null");
      return false;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;
    boolean authStatus = false;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from Os where name='" + name + "'").list();
      if (ul.size() == 1)

      {
        return true;
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

    return false;
  }

  public Integer getIdByOsName(String osName)
  {

    if (!this.isOsExisted(osName))
    {
      logger.error("The name should be existed");
      return null;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ol = session.createQuery("from Os where name='" + osName + "'").list();
      if (ol.size() != 1)
      {
        logger.error("There should be just one os existed with name: " + osName + ", but now there are: " + ol.size());

        return null;
      }
      for (Iterator it = ol.iterator(); it.hasNext();)
      {
        Os o = (Os) it.next();
        logger.info("Os: " + osName + ", id is: " + o.getId());
        return o.getId();
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

  public boolean isOsValid(String name)
  {
    if (name == null)
    {
      logger.error("the user queried is null");
      return false;
    }
    if (!this.isOsExisted(name))
    {
      logger.error("The Os with name: " + name + " should be existed");
      return false;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from Os where name='" + name + "'").list();

      if (ul.size() != 1)
      {
        logger.error("There should be just one Os existed with name: " + name + ", but now there are: " + ul.size());
        return false;
      }
      for (Iterator iterator = ul.iterator(); iterator.hasNext();)
      {
        Os o = (Os) iterator.next();
        logger.info("Os: " + name + ", is valid: " + o.getIsValid());

        return o.getIsValid();
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
    return false;

  }

  private void markOsValid(String name, boolean isValid)
  {

    Integer osId = getIdByOsName(name);
    if (osId == null)
    {
      logger.error("no Os found by the specifed name: " + name);
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Os o = (Os) session.get(Os.class, osId);
      o.setIsValid(isValid);
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }

  }

  public String getOsNameById(Integer id)
  {

    Os o = this.getOsbyOsId(id);

    return o.getName();
  }

  public Os getOsbyOsId(Integer id)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ol = session.createQuery("from Os where id='" + id + "'").list();
      if (ol.size() == 0)
      {
        logger.error("No Os found by the id: " + id);
        return null;
      }
      if (ol.size() > 1)
      {
        logger.error("There should be just one os existed with os id: " + id + ", but now there is: " + ol.size());

        return null;
      }
      for (Iterator it = ol.iterator(); it.hasNext();)
      {
        Os o = (Os) it.next();
        logger.info("os id: " + o.getId() + " the os name is: " + o.getName());
        return o;
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

}
