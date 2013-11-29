package com.lebk.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.services.ProductService;
import com.lebk.services.impl.ProductServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-29
 */
public class ProductAction extends ActionSupport
{

  static Logger logger = Logger.getLogger(ProductAction.class);
  ProductService ps = new ProductServiceImpl();

  private List<ProductInRow> productInList;

  private static Integer listSize = 10;

  public List<ProductInRow> getProductInList()
  {
    constructProductInList();
    return productInList;
  }

  public void setProductInList(List<ProductInRow> productInList)
  {
    this.productInList = productInList;
  }

  private void constructProductInList()
  {
    productInList = new ArrayList<ProductInRow>();
    for (int i = 0; i < listSize; i++)
    {
      ProductInRow row = new ProductInRow(i, "产品类型", "产品花色", "产品尺寸", 99, 0);
      logger.info("the row is:" + row);
      logger.info("the row is:" + row);
      productInList.add(row);
      logger.info("the row is:" + row);
    }

  }

  public String gotoProductIn()
  {
    return SUCCESS;
  }

  public String gotoProductOut()
  {
    return SUCCESS;
  }

  public String productIn()
  {
    return "productIn";
  }

  public String productOut()
  {
    return "productOut";
  }

  class ProductInRow
  {
    private Integer id;
    private String ptType;
    private String ptColor;
    private String ptSize;
    private Integer ptNumber;
    private Integer InNum;

    public ProductInRow(int id, String ptType, String ptColor, String ptSize, int ptNumber, int inNum)
    {
      this.id = Integer.valueOf(id);
      this.ptType = ptType;
      this.ptColor = ptColor;
      this.ptSize = ptSize;
      this.ptNumber = Integer.valueOf(ptNumber);
      this.InNum = Integer.valueOf(inNum);
    }

    public Integer getId()
    {
      return id;
    }

    public void setId(Integer id)
    {
      this.id = id;
    }

    public String getPtType()
    {
      return ptType;
    }

    public void setPtType(String ptType)
    {
      this.ptType = ptType;
    }

    public String getPtColor()
    {
      return ptColor;
    }

    public void setPtColor(String ptColor)
    {
      this.ptColor = ptColor;
    }

    public String getPtSize()
    {
      return ptSize;
    }

    public void setPtSize(String ptSize)
    {
      this.ptSize = ptSize;
    }

    public Integer getPtNumber()
    {
      return ptNumber;
    }

    public void setPtNumber(Integer ptNumber)
    {
      this.ptNumber = ptNumber;
    }

    public Integer getInNum()
    {
      return InNum;
    }

    public void setInNum(Integer inNum)
    {
      InNum = inNum;
    }

    public String toString()
    {
      return this.getId() + ":" + this.getPtType() + ":" + this.getPtColor() + ":" + this.getPtSize() + ":" + this.getPtNumber() + ":" + this.getInNum();
    }
  }
}
