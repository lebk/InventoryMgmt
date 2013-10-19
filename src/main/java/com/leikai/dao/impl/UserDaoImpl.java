package com.leikai.dao.impl;
/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.leikai.dao.UserDao;
import com.leikai.enumType.UserEnumType;
import com.leikai.po.User;
import com.leikai.util.HibernateUtil;


public class UserDaoImpl implements UserDao
{

  static Logger logger = Logger.getLogger(UserDaoImpl.class);

  public boolean addUser(String name, String password, Integer type, String email)
  {

    if (this.isUserExisted(name))
    {
      if (this.isUserValid(name) == true)
      {
        logger.error("The user:" + name + " is already existed");
        return false;
      } else
      {
        updateUser(name, password, type, email);
        markUserValidStatus(name, true);
        return true;
      }
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {

      transaction = session.beginTransaction();
      User u = new User();

      u.setName(name);
      u.setPassword(password);
      u.setType(type);
      u.setEmail(email);
      u.setCreateTime(new Date());
      u.setIsValid(true);
      session.save(u);
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
    return false;
  }

  public List<User> getUserList()
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    List ul = new ArrayList<User>();
    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List uq = session.createQuery("from User where  isValid='1'").list();
      for (Iterator it = uq.iterator(); it.hasNext();)
      {
        User u = (User) it.next();
        ul.add(u);
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
    return ul;
  }

  public boolean deleteUser(String name)
  {
    if (this.isUserExisted(name) == false)
    {
      logger.error("the user: " + name + " should be existed");
      return false;
    } else
    {
      if (this.isUserValid(name) == false)
      {
        logger.warn("This user has been delete before, return false");
        return false;
      }
      this.markUserValidStatus(name, false);
      return true;
    }
    // if (this.isUserExisted(name) == true)
    // {
    //
    // }
    // Integer id = this.getuserIdByUsername(name);
    //
    // Session session = HibernateUtil.getSessionFactory().openSession();
    // Transaction transaction = null;
    // try
    // {
    // transaction = session.beginTransaction();
    // User u = (User) session.get(User.class, id);
    // session.delete(u);
    // transaction.commit();
    // return true;
    // } catch (HibernateException e)
    // {
    // transaction.rollback();
    // e.printStackTrace();
    // } finally
    // {
    // session.close();
    // }
    // return false;
  }

  private boolean updateUser(String name, String password, Integer type, String email)
  {
    Integer userId = getUserIdByUsername(name);
    if (userId == null)
    {
      logger.error("no User found by the specifed name: " + name);
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      User u = (User) session.get(User.class, userId);
      u.setPassword(password);
      u.setType(type);
      u.setEmail(email);
      u.setType(type);
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

  public boolean updateUserType(String name, Integer type)
  {
    Integer userId = getUserIdByUsername(name);
    if (userId == null)
    {
      logger.error("no User found by the specifed name: " + name);
      return false;
    }
    if (this.isUserValid(name) == false)
    {
      logger.error("User: " + name + ", is invalid now, forbidden to update the status");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      User u = (User) session.get(User.class, userId);
      u.setType(type);
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

  public boolean authUser(String name, String password)
  {
    if (this.isUserValid(name) == false)
    {
      logger.warn("The auth user: " + name + ", is invalid!");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;
    boolean authStatus = false;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from User where name='" + name + "' and password='" + password + "'").list();
      if (ul.size() == 1)
      {
        authStatus = true;
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

    return authStatus;
  }

  private boolean isUserExisted(String name)
  {
    if (name == null)
    {
      logger.error("the user is null");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;
    boolean authStatus = false;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from User where name='" + name + "'").list();
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

  public Integer getUserIdByUsername(String name)
  {

    if (!this.isUserExisted(name))
    {
      logger.error("The user with name: " + name + " should be existed");
      return null;
    }

    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from User where name='" + name + "'").list();

      if (ul.size() != 1)
      {
        logger.error("There should be just one user existed with name: " + name + ", but now there are: " + ul.size());
        return null;
      }
      for (Iterator iterator = ul.iterator(); iterator.hasNext();)
      {
        User u = (User) iterator.next();
        logger.info("user: " + name + ", id is: " + u.getId());

        return u.getId();
      }
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      logger.info("session closed: getIdByUsername ");
      session.close();
    }
    return null;
  }

  public String getUsernamebyUserid(Integer userId)
  {
    if (userId == null)
    {
      logger.warn("The quried userId is null, return null");
      return null;
    }
    User u = this.getUserByUserId(userId);
    if (u == null)
    {
      logger.warn("No user found by the userId:" + userId);
      return null;

    }
    logger.info("user name is" + u.getName());
    return u.getName();
  }

  public boolean isUserAdmin(String name)
  {

    if (!this.isUserExisted(name))
    {
      logger.error("user: " + name + " is not existed");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;
    boolean isAdmin = false;
    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from User where name='" + name + "'  and type in (select id from Usertype where name ='" + UserEnumType.admin + "')")
          .list();

      if (ul.size() == 1)
      {
        logger.info("the user: " + name + " is an admin user");
        isAdmin = true;

        return isAdmin;
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
    return isAdmin;
  }

  public boolean isUserAdmin(Integer userId)
  {

    return this.isUserAdmin(this.getUsernamebyUserid(userId));

  }

  public boolean isUserValid(String name)
  {
    if (name == null || name.equals(""))
    {
      logger.error("the user queried is null");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from User where name='" + name + "'").list();

      if (ul.size() != 1)
      {
        logger.error("There should be just one user existed with name: " + name + ", but now there are: " + ul.size());
        return false;
      }
      for (Iterator iterator = ul.iterator(); iterator.hasNext();)
      {
        User u = (User) iterator.next();
        logger.info("user: " + name + ", is valid: " + u.getIsValid());

        return u.getIsValid();
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

  public boolean isEmailValid(String uEmail)
  {
    if (uEmail == null || uEmail.equals(""))
    {
      logger.error("the user email queried is null");
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from User where email='" + uEmail + "'").list();

      if (ul.size() != 1)
      {
        logger.error("There should be just one user existed with email: " + uEmail + ", but now there are: " + ul.size());
        return false;
      }
      for (Iterator iterator = ul.iterator(); iterator.hasNext();)
      {
        User u = (User) iterator.next();
        logger.info("user: " + uEmail + ", is valid: " + u.getIsValid());

        return u.getIsValid();
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

  private void markUserValidStatus(String name, boolean isValid)
  {

    Integer userId = getUserIdByUsername(name);
    if (userId == null)
    {
      logger.error("no User found by the specifed name: " + name);
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      User u = (User) session.get(User.class, userId);
      u.setIsValid(isValid);
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

  public User getUserByUserId(Integer userId)
  {
    if (userId == null)
    {
      logger.warn("The quried userId is null, return null");
      return null;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();

    Transaction transaction = null;

    try
    {
      transaction = session.beginTransaction();
      List ul = session.createQuery("from User where id='" + userId + "'").list();

      if (ul.size() != 1)
      {
        logger.error("There should be just one user existed with userId: " + userId + ", but now there are: " + ul.size());
        return null;
      }
      for (Iterator it = ul.iterator(); it.hasNext();)
      {
        User u = (User) it.next();
        logger.info("userId: " + userId + ", name is: " + u.getName());

        return u;
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
