package com.lebk.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.dto.ProductDTO;
import com.lebk.po.Product;
import com.lebk.services.ProductService;
import com.lebk.services.impl.ProductServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-29
 */
public class ProductAction extends ActionSupport
{
  private List<ProductDTO> productDtoList;

  static Logger logger = Logger.getLogger(ProductAction.class);
  ProductService ps = new ProductServiceImpl();

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

  public List<ProductDTO> getProductDtoList()
  {
    return productDtoList;
  }

  public void setProductDtoList(List<ProductDTO> productDtoList)
  {
    this.productDtoList = productDtoList;
  }

  public String showProductList()
  {

    productDtoList = new ArrayList<ProductDTO>();
    logger.info("calling showProductList");
    List<Product> pl = ps.getAllProductList();
    convertProductListToProductDTOList(pl, productDtoList);
    for (Product p : pl)
    {
      logger.info("showProductList:" + p.getName());
    }
    logger.info("There are:" + productDtoList.size() + " in productDtoList");
    return "showProductList";

  }

  private void convertProductListToProductDTOList(List<Product> pl, List<ProductDTO> ProductDtoList)
  {
    for (Product p : pl)
    {
      productDtoList.add(new ProductDTO(p));
    }
  }

}
