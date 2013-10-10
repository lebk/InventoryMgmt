package com.leikai.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class LoginFilter implements Filter
{
  static Logger logger = Logger.getLogger(LoginFilter.class);

  public void destroy()
  {

    logger.info("login filter destroy");
  }

  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
  {
    logger.info("login filter: doFilter");
    HttpServletRequest request = (HttpServletRequest) req;
    HttpSession session = request.getSession();
    String login = (String) session.getAttribute("username");
    // If it is the login page, skip the filter operation

    if ("/InventoryMgmt/src/main/webapp/Login.jsp".equals(request.getRequestURI()))
    {
      
      chain.doFilter(req, res);

    } else
    {
      // Check whether the user has loged in, if not, forward to the login page
      if (login == null || "".equals(login))
      {
        request.getRequestDispatcher("Login.jsp").forward(req, res);
      } else
      {
        chain.doFilter(req, res);
      }
    }

  }

  public void init(FilterConfig filterConfig) throws ServletException
  {
    logger.info("login filter init");

  }

}
