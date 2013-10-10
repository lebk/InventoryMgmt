<%@ page contentType="text/html; charset=UTF-8" import="com.leikai.services.*, com.leikai.services.impl.*"%>
<%@taglib uri="/struts-tags" prefix="s"%>

<!--nav begin -->
<DIV>
<!--begin -->
<br>
<br>

<ul><a href="<s:url action="jobstatustilesAction"/>"><b>Job&nbspStatus</b></a><br></ul>
<ul><a href="<s:url action="jobmanagetilesAction"/>"><b>New&nbspJob</b></a><br></ul>
<ul><a href="<s:url action="showvmlisttilesAction"/>"><b>VM&nbspList</b></a><br></ul>
<% 
  String userName = (String) session.getAttribute("username");
  UserService us = new UserServiceImpl();
  boolean isAdmin=us.isUserAdmin(userName);
  if(isAdmin==true)
  {
%>
<ul><a href="<s:url action="admintilesAction"/>"><b>Go&nbspto&nbspAdministrator</b></a><br></ul>
<%
  }
%>
</DIV>
   


