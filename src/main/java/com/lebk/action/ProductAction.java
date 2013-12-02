package com.lebk.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.enumType.BusinessEnumType;
import com.lebk.po.Ptcolor;
import com.lebk.po.Ptsize;
import com.lebk.po.Pttype;
import com.lebk.services.ProductColorService;
import com.lebk.services.ProductService;
import com.lebk.services.ProductSizeService;
import com.lebk.services.ProductTypeService;
import com.lebk.services.impl.ProductColorServiceImpl;
import com.lebk.services.impl.ProductServiceImpl;
import com.lebk.services.impl.ProductSizeServiceImpl;
import com.lebk.services.impl.ProductTypeServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-29
 */
public class ProductAction extends ActionSupport
{

  static Logger logger = Logger.getLogger(ProductAction.class);
  ProductService ps = new ProductServiceImpl();
  ProductTypeService pts = new ProductTypeServiceImpl();
  ProductSizeService pss = new ProductSizeServiceImpl();
  ProductColorService pcs = new ProductColorServiceImpl();
  private List<ProductInRow> productInList;
  private static Integer listSize = 10;
  private static String emptyStr = "";
  private List<String> ptList;
  private List<String> pcList;
  private List<String> psList;

  public ProductAction()
  {
    this.constructProductInList();
  }

  public List<ProductInRow> getProductInList()
  {

    // constructProductInList();
    return productInList;
  }

  public void setProductInList(List<ProductInRow> productInList)
  {
    for (ProductInRow productIn : productInList)
    {
      logger.info(productIn);
    }
    this.productInList = productInList;
  }

  private void constructProductInList()
  {
    // get the product type list, append a empty string
    ptList = new ArrayList<String>();
    List<Pttype> ptl = pts.getAllPtType();
    ptList.add(emptyStr);
    for (Pttype pt : ptl)
    {
      ptList.add(pt.getType());
    }

    // get the product color list, append a empty string

    pcList = new ArrayList<String>();

    List<Ptcolor> pcl = pcs.getAllPtColor();
    pcList.add(emptyStr);
    for (Ptcolor pt : pcl)
    {
      pcList.add(pt.getColor());
    }

    // get the product size list, append a empty string

    psList = new ArrayList<String>();

    List<Ptsize> psl = pss.getAllPtSize();
    psList.add(emptyStr);
    for (Ptsize ps : psl)
    {
      psList.add(ps.getSize());
    }

    productInList = new ArrayList<ProductInRow>();
    for (int i = 0; i < listSize; i++)
    {

      ProductInRow row = new ProductInRow(i, ptList, pcList, psList, 99, 0);
      productInList.add(row);

    }

  }

  public String productInSubmit()
  {
    for (ProductInRow productIn : productInList)
    {
      logger.info(productIn);
    }
    // Should move this method to a place where the nput has been validated.
    this.submit(productInList);
    return SUCCESS;
  }

  private boolean submit(List<ProductInRow> productInList)
  {
    for (ProductInRow pir : productInList)
    {
      String ptType = pir.getSelectedProductType();
      String ptColor = pir.getSelectedProductColor();
      String ptSize = pir.getSelectedProductSize();
      String businessType = BusinessEnumType.in;
      Integer pNum = pir.getInNum();
      String opUser = (String) ActionContext.getContext().getSession().get("username");
      logger.info("The submit product is:(" + ptType + ":" + ptColor + ":" + ptSize + ":" + pNum + " by " + opUser + ")");
      ps.updateProduct(ptType, ptColor, ptSize, pNum, businessType, opUser);
    }
    return true;
  }

  public void validate()
  {
    // for (ProductInRow productIn : productInList)
    // {
    // if (productIn.getInNum() > 0)
    // {
    // addFieldError("productInList.ptList", "产品类型没有选择");
    // }
    // }
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

  // class TO hold the row of productIn
  class ProductInRow
  {
    private Integer id;
    private List<String> ptList;
    private List<String> pcList;
    private List<String> psList;
    private Integer ptNumber;
    private Integer InNum;
    private String selectedProductType;
    private String selectedProductColor;
    private String selectedProductSize;

    public ProductInRow(int id, List<String> ptList, List<String> pcList, List<String> psList, int ptNumber, int inNum)
    {
      this.id = Integer.valueOf(id);
      this.ptList = ptList;
      this.pcList = pcList;
      this.psList = psList;
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

    public List<String> getPtList()
    {
      return ptList;
    }

    public void setPtList(List<String> ptList)
    {
      this.ptList = ptList;
    }

    public List<String> getPcList()
    {
      return pcList;
    }

    public void setPcList(List<String> pcList)
    {
      this.pcList = pcList;
    }

    public List<String> getPsList()
    {
      return psList;
    }

    public void setPsList(List<String> psList)
    {
      this.psList = psList;
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

    public String getSelectedProductType()
    {
      return selectedProductType;
    }

    public void setSelectedProductType(String selectedProductType)
    {
      this.selectedProductType = selectedProductType;
    }

    public String getSelectedProductColor()
    {
      return selectedProductColor;
    }

    public void setSelectedProductColor(String selectedProductColor)
    {
      this.selectedProductColor = selectedProductColor;
    }

    public String getSelectedProductSize()
    {
      return selectedProductSize;
    }

    public void setSelectedProductSize(String selectedProductSize)
    {
      this.selectedProductSize = selectedProductSize;
    }

    public String toString()
    {
      return this.getId() + ":" + this.getSelectedProductType() + ":" + this.getSelectedProductColor() + ":" + this.getSelectedProductSize() + ":"
          + +this.getPtNumber() + ":" + this.getInNum();
    }
  }
}
