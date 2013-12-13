<%@ page contentType="text/html; charset=UTF-8" import="com.lebk.services.*, com.lebk.services.impl.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!--nav begin -->
<DIV>
<!--begin -->
<br>
<br>

<ul><a href="<s:url action="showProductListtilesAction"/>"><b>库存状态</b></a><br></ul>
<ul><a href="<s:url action="gotoProductIn"/>"><b>入库单</b></a><br></ul>
<ul><a href="<s:url action="gotoProductOut"/>"><b>出库单</b></a><br></ul>
<% 
  String userName = (String) session.getAttribute("username");
  UserService us = new UserServiceImpl();
  boolean isAdmin=us.isUserAdmin(userName);
  if(isAdmin==true)
  {
%>
<ul><a href="<s:url action="admintilesAction"/>"><b>管理页面</b></a><br></ul>
<%
  }
%>
<ul><a href="<s:url action="gotoProductQuery"/>"><b>库存查询</b></a><br></ul>

</DIV>
   


