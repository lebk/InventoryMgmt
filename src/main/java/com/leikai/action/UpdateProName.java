package com.leikai.action;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateProName extends ActionSupport
{
   private String productname;

  public String getProductname()
  {
    return productname;
  }

  public void setProductname(String productname)
  {
    this.productname = productname;
  }
  @Override
public String execute()
  {
    java.util.Map<String, Object> session = ActionContext.getContext().getSession();
    session.put("productname", productname);
    return "success";
  }
  public String display()
  {
    return "none";
  }
}
