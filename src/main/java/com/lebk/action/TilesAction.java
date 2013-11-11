package com.lebk.action;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lebk.dto.UserDTO;
import com.lebk.enumType.UserEnumType;
import com.lebk.po.Product;
import com.lebk.po.Pttype;
import com.lebk.po.User;
import com.lebk.services.ProductService;
import com.lebk.services.ProductTypeService;
import com.lebk.services.UserService;
import com.lebk.services.impl.ProductServiceImpl;
import com.lebk.services.impl.ProductTypeServiceImpl;
import com.lebk.services.impl.UserServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TilesAction extends ActionSupport
{

  static Logger logger = Logger.getLogger(TilesAction.class);
  private static final long serialVersionUID = -2613425890762568273L;
  private UserService us = new UserServiceImpl();
  static final Integer iAdminType = 1;
  static final Integer iRegularType = 2;
  private String addUserName;
  private String addUserPassword;
  private String addUserEmail;
  private boolean addasAdmin;
  private List<UserDTO> userDtoList;

  ProductTypeService pts = new ProductTypeServiceImpl();

  private List<Pttype> productTypeList = new ArrayList<Pttype>();

  private String username;

  private Integer pageNow = 1;
  private Integer pageSize = 30;
  private Integer pageInTotal;

  public Integer getPageNow()
  {
    return pageNow;
  }

  public void setPageNow(Integer pageNow)
  {
    this.pageNow = pageNow;
  }

  public Integer getPageSize()
  {
    return pageSize;
  }

  public void setPageSize(Integer pageSize)
  {
    this.pageSize = pageSize;
  }

  public Integer getPageInTotal()
  {
    return pageInTotal;
  }

  public void setPageInTotal(Integer pageInTotal)
  {
    this.pageInTotal = pageInTotal;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public Integer countTotalPage(Integer pageSize, Integer allRow)
  {
    Integer totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow / pageSize + 1;
    return totalPage;
  }

  public String jobstatus()
  {
    return "jobstatus";
  }

  public String jobmanage()
  {
    return "jobmanage";
  }

  public String showvmlist()
  {

    return "showvmlist";
  }

  public List<Pttype> getProductTypeList()
  {
    return productTypeList;
  }

  public void setProductTypeList(List<Pttype> productTypeList)
  {
    this.productTypeList = productTypeList;
  }

  public String admin()
  {
    return "admin";
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

  public String displayUser()
  {
    return "displayUser";
  }

  public List<UserDTO> getUserList()
  {
    return userDtoList;
  }

  public void setUserList(List<UserDTO> userList)
  {
    this.userDtoList = userList;
  }

  public String aboutVMF()
  {
    return "aboutVMF";
  }

  public String showUserList()
  {
    userDtoList = new ArrayList<UserDTO>();

    String userName = (String) ActionContext.getContext().getSession().get("username");

    List<User> ul = us.getUserList(userName);

    convertUserListToUserDTOList(ul, userDtoList);

    return "showUserList";
  }

  public String showProductTypeList()
  {
    // this.productTypeList = new ArrayList<Pttype>();

    List<Pttype> ptl = pts.getAllPtType();
    this.productTypeList = ptl;

    return "showProductTypeList";
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
}
