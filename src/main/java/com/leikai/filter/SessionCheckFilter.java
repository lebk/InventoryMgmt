package com.leikai.filter;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class SessionCheckFilter implements Filter
{

  protected FilterConfig filterConfig = null;
  private String redirectURL = null;
  private Set<String> notCheckURLList = new HashSet<String>();
  private String sessionKey = null;
  static Logger logger = Logger.getLogger(SessionCheckFilter.class);

  public void destroy()
  {
    notCheckURLList.clear();
  }

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
  {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpSession session = request.getSession();
    if (sessionKey == null)
    {
      filterChain.doFilter(request, response);
      return;
    }

    if ((!checkRequestURIIntNotFilterList(request)) && session.getAttribute(sessionKey) == null)
    {
      response.sendRedirect(request.getContextPath() + redirectURL);
      logger.info("session.getAttribute: " + session.getAttribute(sessionKey));
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private boolean checkRequestURIIntNotFilterList(HttpServletRequest request)
  {
    String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
    // String temp = request.getRequestURI();
    //
    // logger.info("request uri is: " + uri);
    // logger.info("request uri temp is: " + temp);

    // temp = temp.substring(request.getContextPath().length() + 1);
    return notCheckURLList.contains(uri);
  }

  public void init(FilterConfig filterConfig) throws ServletException
  {
    logger.info("session check initialization!");
    this.filterConfig = filterConfig;
    redirectURL = filterConfig.getInitParameter("redirectURL");
    sessionKey = filterConfig.getInitParameter("checkSessionKey");
    String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
    if (notCheckURLListStr != null)
    {
      System.out.println(notCheckURLListStr);
      String[] params = notCheckURLListStr.split(",");
      for (int i = 0; i < params.length; i++)
      {
        notCheckURLList.add(params[i].trim());
      }
    }
  }
}