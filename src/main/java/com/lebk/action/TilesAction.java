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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lebk.dto.ProductDTO;
import com.lebk.dto.PtDetailsDTO;
import com.lebk.dto.UserDTO;
import com.lebk.enumType.UserEnumType;
import com.lebk.po.Product;
import com.lebk.po.Ptcolor;
import com.lebk.po.Ptdetails;
import com.lebk.po.Ptsize;
import com.lebk.po.Pttype;
import com.lebk.po.User;
import com.lebk.services.ProductColorService;
import com.lebk.services.ProductDetailsService;
import com.lebk.services.ProductService;
import com.lebk.services.ProductSizeService;
import com.lebk.services.ProductTypeService;
import com.lebk.services.UserService;
import com.lebk.services.impl.ProductColorServiceImpl;
import com.lebk.services.impl.ProductDetailsServiceImpl;
import com.lebk.services.impl.ProductServiceImpl;
import com.lebk.services.impl.ProductSizeServiceImpl;
import com.lebk.services.impl.ProductTypeServiceImpl;
import com.lebk.services.impl.UserServiceImpl;
import com.lebk.util.ProductUtil;
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
  private boolean addasAdmin;
  private List<UserDTO> userDtoList;
  private String addProductType;
  private String addProductSize;
  private String addProductColor;
  ProductTypeService pts = new ProductTypeServiceImpl();
  ProductSizeService pss = new ProductSizeServiceImpl();
  ProductColorService pcs = new ProductColorServiceImpl();
  private List<Pttype> productTypeList = new ArrayList<Pttype>();
  private List<Ptsize> productSizeList = new ArrayList<Ptsize>();
  private List<Ptcolor> productColorList = new ArrayList<Ptcolor>();
  private String username;

  ProductService ps = new ProductServiceImpl();
  private List<ProductDTO> productDtoList;

  private Integer selectedPoId;

  private Integer selectedUserId;

  private Integer selectedProductTypeId;

  private Integer selectedProductColorId;
  private Integer selectedProductSizeId;

  ProductDetailsService pds = new ProductDetailsServiceImpl();

  private List<PtDetailsDTO> productDetailsList;

  private Integer pageNow = 1;
  private Integer pageSize = 20;
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

  public List<Pttype> getProductTypeList()
  {
    return productTypeList;
  }

  public void setProductTypeList(List<Pttype> productTypeList)
  {
    this.productTypeList = productTypeList;
  }

  public List<Ptsize> getProductSizeList()
  {
    return productSizeList;
  }

  public void setProductSizeList(List<Ptsize> productSizeList)
  {
    this.productSizeList = productSizeList;
  }

  public List<Ptcolor> getProductColorList()
  {
    return this.productColorList;
  }

  public void setProductColorList(List<Ptcolor> productColorList)
  {
    this.productColorList = productColorList;
  }

  public List<ProductDTO> getProductList()
  {
    return productDtoList;
  }

  public void setProductList(List<ProductDTO> productList)
  {
    this.productDtoList = productList;
  }

  public String showProductList()
  {

    productDtoList = new ArrayList<ProductDTO>();
    logger.info("calling showProductList");
    List<Product> pl = ps.getAllProductList();
    Integer rowInTotal = pl.size();
    pageInTotal = countTotalPage(pageSize, rowInTotal);

    logger.info("page in total is: " + pageInTotal);
    logger.info("page size is: " + pageSize);
    logger.info("page now is: " + pageNow);
    List<Product> currentPL;
    if (pageSize * pageNow > rowInTotal)
    {
      currentPL = pl.subList(pageSize * (pageNow - 1), rowInTotal);

    } else
    {
      currentPL = pl.subList(pageSize * (pageNow - 1), pageSize * pageNow);
    }
    convertProductListToProductDTOList(currentPL, productDtoList);
    for (Product p : pl)
    {
      logger.info("showProductList:" + p.getName());
    }
    logger.info("There are:" + productDtoList.size() + " in productDtoList");
    return "showProductList";

  }

  public Integer getSelectedPoId()
  {
    return selectedPoId;
  }

  public void setSelectedPoId(Integer selectedPoId)
  {
    this.selectedPoId = selectedPoId;
  }

  public Integer getSelectedUserId()
  {
    return selectedUserId;
  }

  public void setSelectedUserId(Integer selectedUserId)
  {
    this.selectedUserId = selectedUserId;
  }

  public Integer getSelectedProductTypeId()
  {
    return selectedProductTypeId;
  }

  public void setSelectedProductTypeId(Integer selectedProductTypeId)
  {
    this.selectedProductTypeId = selectedProductTypeId;
  }

  public Integer getSelectedProductColorId()
  {
    return selectedProductColorId;
  }

  public void setSelectedProductColorId(Integer selectedProductColorId)
  {
    this.selectedProductColorId = selectedProductColorId;
  }

  public Integer getSelectedProductSizeId()
  {
    return selectedProductSizeId;
  }

  public void setSelectedProductSizeId(Integer selectedProductSizeId)
  {
    this.selectedProductSizeId = selectedProductSizeId;
  }

  public List<PtDetailsDTO> getProductDetailsList()
  {
    return productDetailsList;
  }

  public void setProductDetailsList(List<PtDetailsDTO> productDetailsList)
  {
    this.productDetailsList = productDetailsList;
  }

  public String showProductDetailsList()
  {

    productDetailsList = new ArrayList<PtDetailsDTO>();

    logger.info("The selected product id is: " + this.selectedPoId);

    List<Ptdetails> pdl = pds.getProductDetailsByProductId(selectedPoId);

    this.productDetailsList = ProductUtil.convertPtdetailsListToPtdetailsDTOList(pdl);
    return "showProductDetailsList";
  }

  public String admin()
  {
    return "admin";
  }

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

  public void setAddasAdmin(boolean addasAdmin)
  {
    this.addasAdmin = addasAdmin;
  }

  public boolean isAddasAdmin()
  {
    return addasAdmin;
  }

  public String getAddProductType()
  {
    return addProductType;
  }

  public void setAddProductType(String addProductType)
  {
    this.addProductType = addProductType;
  }

  public String getAddProductSize()
  {
    return addProductSize;
  }

  public void setAddProductSize(String addProductSize)
  {
    this.addProductSize = addProductSize;
  }

  public String getAddProductColor()
  {
    return addProductColor;
  }

  public void setAddProductColor(String addProductColor)
  {
    this.addProductColor = addProductColor;
  }

  public String addNewUser()
  {
    return "addNewUser";
  }

  public List<UserDTO> getUserList()
  {
    return userDtoList;
  }

  public void setUserList(List<UserDTO> userList)
  {
    this.userDtoList = userList;
  }

  public String aboutSystem()
  {
    return "aboutSystem";
  }

  public String showUserList()
  {
    userDtoList = new ArrayList<UserDTO>();

    String userName = (String) ActionContext.getContext().getSession().get("username");

    List<User> ul = us.getUserList(userName);

    convertUserListToUserDTOList(ul, userDtoList);
    for (User u : ul)
    {
      logger.info("showUserList:" + u.getName());
    }
    return "showUserList";
  }

  public String showProductTypeList()
  {
    // this.productTypeList = new ArrayList<Pttype>();

    List<Pttype> ptl = pts.getAllPtType();
    this.productTypeList = ptl;

    return "showProductTypeList";
  }

  public String deleteProductType()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");

    logger.info("The selected product type id is:" + this.selectedProductTypeId);
    logger.info("The user is::" + userName);

    Boolean status = pts.deletePtType(this.selectedProductTypeId, userName);

    if (status == true)
    {
      logger.info("Delete the product type successfully, the product type id is:" + selectedProductTypeId);
      return SUCCESS;
    }

    return ERROR;
  }

  public String deleteProductColor()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");

    logger.info("The selected product color id is:" + this.selectedProductColorId);
    logger.info("The user is::" + userName);

    Boolean status = pcs.deletePtColor(this.selectedProductColorId, userName);

    if (status == true)
    {
      logger.info("Delete the product color successfully, the product color id is:" + selectedProductColorId);
      return SUCCESS;
    }

    return ERROR;
  }

  public String deleteProductSize()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");

    logger.info("The selected product size id is:" + this.selectedProductSizeId);
    logger.info("The user is::" + userName);

    Boolean status = pss.deletePtSize(this.selectedProductSizeId, userName);

    if (status == true)
    {
      logger.info("Delete the product size successfully, the product size id is:" + selectedProductSizeId);
      return SUCCESS;
    }

    return ERROR;
  }

  public String showProductSizeList()
  {

    List<Ptsize> psl = pss.getAllPtSize();
    this.productSizeList = psl;

    return "showProductSizeList";
  }

  public String showProductColorList()
  {

    List<Ptcolor> pcl = pcs.getAllPtColor();
    this.productColorList = pcl;

    return "showProductColorList";
  }

  public boolean addUser(String name, String password, Integer type, String opUser)
  {
    return us.addUser(name, password, type, opUser);
  }

  public String addUser()
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    if (true == addasAdmin)
    {
      boolean badd = us.addUser(addUserName, addUserPassword, iAdminType, opUser);
      if (true == badd)
        return SUCCESS;
      else
        return ERROR;
    } else
    {
      boolean badd = us.addUser(addUserName, addUserPassword, iRegularType, opUser);
      if (true == badd)
        return SUCCESS;
      else
        return ERROR;
    }

  }

  public String addNewProductType()
  {
    return "addNewProductType";
  }

  public String addNewProductSize()
  {
    return "addNewProductSize";
  }

  public String addProductType()
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    String ptType = this.addProductType;
    logger.info("The product type is:" + ptType);
    if (ptType == null || ptType.length() == 0)
    {
      logger.info("The product type is emtpy");
      return ERROR;
    }
    Boolean status = pts.addPtType(ptType, opUser);
    if (status == true)
    {
      return SUCCESS;
    }
    return ERROR;
  }

  public String addProductColor()
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    String ptColor = this.addProductColor;
    logger.info("The product color is:" + ptColor);
    if (ptColor == null || ptColor.length() == 0)
    {
      logger.info("The product color is emtpy");
      return ERROR;
    }
    Boolean status = pcs.addPtColor(ptColor, opUser);
    if (status == true)
    {
      return SUCCESS;
    }
    return ERROR;
  }

  public String addProductSize()
  {
    Map session = ActionContext.getContext().getSession();
    String opUser = (String) session.get("username");
    String ptSize = this.addProductSize;
    logger.info("The product size is:" + ptSize);
    if (ptSize == null || ptSize.length() == 0)
    {
      logger.info("The product size is emtpy");
      return ERROR;
    }

    Boolean status = pss.addPtSize(ptSize, opUser);
    if (status == true)
    {
      return SUCCESS;
    }
    return ERROR;
  }

  public String addNewProductColor()
  {
    return "addNewProductColor";
  }

  private boolean deleteUser(Integer userId, String opUser)
  {

    return us.deleteUser(userId, opUser);
  }

  public String deleteUser()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    logger.info("The selected user id is: " + this.selectedUserId);
    if (us.getUserByUserId(this.selectedUserId).getName().equalsIgnoreCase(userName))
    {
      logger.info("Can not delete the user itself");
      return ERROR;
    }
    boolean bdelete = deleteUser(selectedUserId, userName);
    if (true == bdelete)
    {
      return SUCCESS;
    } else
    {
      return ERROR;
    }
  }

  private boolean updateUserType(Integer userId, Integer type, String opUser)
  {
    return us.updateUserType(userId, type, opUser);

  }

  public String updateUser()
  {
    Map session = ActionContext.getContext().getSession();
    String userName = (String) session.get("username");
    logger.info("The selected user id is: " + this.selectedUserId);
    boolean bAdmin = us.isUserAdmin(this.selectedUserId);
    if (us.getUserByUserId(this.selectedUserId).getName().equalsIgnoreCase(userName))
    {
      logger.info("Can not update the user itself");
      return ERROR;
    }
    if (true == bAdmin)
    {
      boolean bUpdate = updateUserType(selectedUserId, iRegularType, userName);
      if (true == bUpdate)
      {
        return SUCCESS;
      } else
        return ERROR;
    } else
    {
      boolean bUpdate = updateUserType(selectedUserId, iAdminType, userName);
      if (true == bUpdate)
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

  private void convertProductListToProductDTOList(List<Product> pl, List<ProductDTO> ProductDtoList)
  {
    for (Product p : pl)
    {
      productDtoList.add(new ProductDTO(p));
    }
  }

}
