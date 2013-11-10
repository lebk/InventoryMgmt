package com.lebk.action;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lebk.dto.UserDTO;
import com.lebk.po.User;
import com.lebk.services.UserService;
import com.lebk.services.impl.UserServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport
{
  private String username;
  private String password;
  private List<UserDTO> userDtoList;

  static final Integer iAdminType = 1;
  static final Integer iRegularType = 2;

  static Logger logger = Logger.getLogger(UserAction.class);
  UserService us = new UserServiceImpl();

  private String addUserName;
  private String addUserPassword;
  private String addUserEmail;
  private boolean addasAdmin;

  public void setAddUserName(String addUserName)
  {
    this.addUserName = addUserName;
  }

  public String getAddUserName()
  {
    return addUserName;
  }

  public void setAddUserPassword(String addUserPassword)
  {
    this.addUserPassword = addUserPassword;
  }

  public String getAddUserPassword()
  {
    return addUserPassword;
  }

  public void setAddUserEmail(String addUserEmail)
  {
    this.addUserEmail = addUserEmail;
  }

  public String getAddUserEmail()
  {
    return addUserEmail;
  }

  public void setAddasAdmin(boolean addasAdmin)
  {
    this.addasAdmin = addasAdmin;
  }

  public boolean isAddasAdmin()
  {
    return addasAdmin;
  }

  public String logout()
  {
    Map session = ActionContext.getContext().getSession();
    session.remove("username");
    return SUCCESS;
  }

  public String aboutvmf()
  {
    return SUCCESS;
  }

  public String authUser()
  {
    try
    {
      if (us.authUser(this.username, this.password) == true)
      {
        Map session = ActionContext.getContext().getSession();
        session.put("username", this.username);
        return SUCCESS;
      } else
      {
        logger.info("Login failed");

        addActionError(getText("error.login"));

        return ERROR;
      }
    } catch (Exception e)
    {
      // System.out.println(e);
      logger.info("Login Exception: " + e);
      return ERROR;
    }
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public List<UserDTO> getUserList()
  {
    return userDtoList;
  }

  public void setUserList(List<UserDTO> userList)
  {
    this.userDtoList = userList;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String showUserList()
  {
    userDtoList = new ArrayList<UserDTO>();

    String userName = (String) ActionContext.getContext().getSession().get("username");

    List<User> ul = us.getUserList(userName);

    convertUserListToUserDTOList(ul, userDtoList);

    return SUCCESS;
  }

  public boolean addUser(String name, String password, Integer type, String email, String opUser)
  {
    return us.addUser(name, password, type, email, opUser);
  }

  public String addUser()
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");

    if (us.isUserValid(addUserName))
    {
      session.put("ADDUSER", "false");
      return SUCCESS;
    }
    if (us.isEmailValid(addUserEmail))
    {
      session.put("ADDEMAIL", "false");
      return SUCCESS;
    }
    if (true == addasAdmin)
    {
      boolean badd = us.addUser(addUserName, addUserPassword, iAdminType, addUserEmail, opUser);
      if (true == badd)
        return SUCCESS;
      else
        return ERROR;
    } else
    {
      boolean badd = us.addUser(addUserName, addUserPassword, iRegularType, addUserEmail, opUser);
      if (true == badd)
        return SUCCESS;
      else
        return ERROR;
    }

  }

  private boolean deleteUser(String name, String opUser)
  {

    return us.deleteUser(name, opUser);
  }

  public String deleteUser()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    boolean bdelete = deleteUser(username, userName);
    if (true == bdelete)
    {
      return SUCCESS;
    } else
    {
      return ERROR;
    }
  }

  private boolean updateUserType(String name, Integer type, String opUser)
  {
    return us.updateUserType(name, type, opUser);

  }

  public String updateUser()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    boolean bAdmin = us.isUserAdmin(username);
    if (true == bAdmin)
    {
      boolean bdelete = updateUserType(username, iRegularType, userName);
      if (true == bdelete)
      {
        return SUCCESS;
      } else
        return ERROR;
    } else
    {
      boolean bdelete = updateUserType(username, iAdminType, userName);
      if (true == bdelete)
      {
        return SUCCESS;
      } else
        return ERROR;
    }

  }

  private void convertUserListToUserDTOList(List<User> ul, List<UserDTO> userDtoList)
  {
    for (User u : ul)
    {
      userDtoList.add(new UserDTO(u));
    }
  }

  public String display()
  {
    return NONE;
  }
}
