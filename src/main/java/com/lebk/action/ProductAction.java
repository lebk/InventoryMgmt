package com.lebk.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lebk.dto.PtDetailsDTO;
import com.lebk.enumType.BusinessEnumType;
import com.lebk.po.Product;
import com.lebk.po.Ptcolor;
import com.lebk.po.Ptdetails;
import com.lebk.po.Ptsize;
import com.lebk.po.Pttype;
import com.lebk.services.ProductColorService;
import com.lebk.services.ProductDetailsService;
import com.lebk.services.ProductService;
import com.lebk.services.ProductSizeService;
import com.lebk.services.ProductTypeService;
import com.lebk.services.impl.ProductColorServiceImpl;
import com.lebk.services.impl.ProductDetailsServiceImpl;
import com.lebk.services.impl.ProductServiceImpl;
import com.lebk.services.impl.ProductSizeServiceImpl;
import com.lebk.services.impl.ProductTypeServiceImpl;
import com.lebk.util.ProductUtil;
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
  private List<ProductRow> productInList;
  private List<ProductRow> productOutList;
  private static Integer listSize = 10;
  private static String emptyStr = "";
  private List<String> ptList;
  private List<String> pcList;
  private List<String> psList;

  private String selectedQueryProductType;
  private String selectedQueryProductColor;
  private String selectedQueryProductSize;

  private List<PtDetailsDTO> productQueryDetailsList;

  ProductDetailsService pds = new ProductDetailsServiceImpl();

  public ProductAction()
  {
    this.constructProductTxnList();
  }

  public List<ProductRow> getProductInList()
  {

    // constructProductInList();
    return productInList;
  }

  public void setProductInList(List<ProductRow> productInList)
  {
    this.productInList = productInList;
  }

  public List<ProductRow> getProductOutList()
  {
    return productOutList;
  }

  public void setProductOutList(List<ProductRow> productOutList)
  {
    this.productOutList = productOutList;
  }

  public String getSelectedQueryProductType()
  {
    return selectedQueryProductType;
  }

  public void setSelectedQueryProductType(String selectedQueryProductType)
  {
    this.selectedQueryProductType = selectedQueryProductType;
  }

  public String getSelectedQueryProductColor()
  {
    return selectedQueryProductColor;
  }

  public void setSelectedQueryProductColor(String selectedQueryProductColor)
  {
    this.selectedQueryProductColor = selectedQueryProductColor;
  }

  public String getSelectedQueryProductSize()
  {
    return selectedQueryProductSize;
  }

  public void setSelectedQueryProductSize(String selectedQueryProductSize)
  {
    this.selectedQueryProductSize = selectedQueryProductSize;
  }

  public List<PtDetailsDTO> getProductQueryDetailsList()
  {
    return productQueryDetailsList;
  }

  public void setProductQueryDetailsList(List<PtDetailsDTO> productQueryDetailsList)
  {
    this.productQueryDetailsList = productQueryDetailsList;
  }

  private void constructProductTxnList()
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

    productInList = new ArrayList<ProductRow>();
    productOutList = new ArrayList<ProductRow>();
    for (int i = 0; i < listSize; i++)
    {

      ProductRow row = new ProductRow(i, ptList, pcList, psList, 0, 0);
      productInList.add(row);
      productOutList.add(row);

    }

  }

  public String productInSubmit()
  {
    for (ProductRow productIn : productInList)
    {
      logger.info(productIn);
    }
    // Should move this method to a place where the nput has been validated.
    this.submit(productInList, BusinessEnumType.in);
    return SUCCESS;
  }

  public String productOutSubmit()
  {
    for (ProductRow productIn : productOutList)
    {
      logger.info(productIn);
    }
    // Should move this method to a place where the input has been validated.
    this.submit(productOutList, BusinessEnumType.out);
    return SUCCESS;
  }

  private boolean submit(List<ProductRow> productInList, String txnType)
  {
    for (ProductRow pr : productInList)
    {
      String ptType = pr.getSelectedProductType();
      String ptColor = pr.getSelectedProductColor();
      String ptSize = pr.getSelectedProductSize();
      String businessType = txnType;
      Integer pNum = pr.getTxnNum();
      String opUser = (String) ActionContext.getContext().getSession().get("username");
      logger.info("The submit product is:(" + ptType + ":" + ptColor + ":" + ptSize + ":" + pNum + " by " + opUser + ")");
      boolean status = ps.updateProduct(ptType, ptColor, ptSize, pNum, businessType, opUser);
      logger.info("The update status is: " + status);
    }
    return true;
  }

  public void validate()
  {
    // for (ProductRow productIn : productInList)
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

  public String gotoProductQuery()
  {

    logger.info("call gotoProductQuery");
    return SUCCESS;
  }

  public String productQueryResult()
  {
    logger.info("Selected Product type is: " + this.getSelectedQueryProductType());
    logger.info("selected Product color is: " + this.getSelectedQueryProductColor());
    logger.info("Selected Product size is: " + this.getSelectedQueryProductSize());

    List<Product> pl = ps.searchProduct(this.getSelectedQueryProductType(), this.getSelectedQueryProductColor(), this.getSelectedQueryProductSize());

    List<Integer> poIdList = new ArrayList<Integer>();
    for (Product p : pl)
    {
      poIdList.add(p.getId());
    }

    List<Ptdetails> pdl = pds.getProductDetailsByPoIdList(poIdList);

    this.productQueryDetailsList = ProductUtil.convertPtdetailsListToPtdetailsDTOList(pdl);

    return SUCCESS;
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

  // class TO hold the row of productIn
  class ProductRow
  {
    private Integer id;
    private List<String> ptList;
    private List<String> pcList;
    private List<String> psList;
    private Integer ptNumber;
    private Integer txnNum;
    private String selectedProductType;
    private String selectedProductColor;
    private String selectedProductSize;

    public ProductRow(int id, List<String> ptList, List<String> pcList, List<String> psList, int ptNumber, int inNum)
    {
      this.id = Integer.valueOf(id);
      this.ptList = ptList;
      this.pcList = pcList;
      this.psList = psList;
      this.ptNumber = Integer.valueOf(ptNumber);
      this.txnNum = Integer.valueOf(inNum);
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

    public Integer getTxnNum()
    {
      return txnNum;
    }

    public void setTxnNum(Integer txnNum)
    {
      this.txnNum = txnNum;
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
          + +this.getPtNumber() + ":" + this.getTxnNum();
    }
  }
}
