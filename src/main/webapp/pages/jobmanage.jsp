<%@ page contentType="text/html; charset=UTF-8" import="com.leikai.services.*, com.leikai.services.impl.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css">
.borderAll {
    border:0px; width:1000px;
}
</style>
<h3>
<center>
Customize your own VM
</center>
</h3>
</head>

<body>
<table align="center" class="borderAll">
<tr>
<td>
<ul type="square">
<% 
  String userName = (String) session.getAttribute("username");
  UserService us = new UserServiceImpl();
  boolean isAdmin=us.isUserAdmin(userName);
  if(isAdmin==true)
  {
%>
<li><s:a href="displayUploadFiletilesAction.action">Consumer/Enterprise product to upload</s:a></li>
<%
  }
%>
</ul>
</td>
<tr>
<td>
<ul type="square">
<li>
<s:a href="startJobtilesAction.action">Start Job</s:a>
</li>
</ul>
</td>
</tr>
</table>
</body>

</html>




