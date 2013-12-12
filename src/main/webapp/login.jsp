<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>登录 佳瑞佳仓存管理系统</title>
<link href="<s:url value="/css/InventoryMgmt.css"/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value="/css/favicon.ico"/>" rel="shortcut icon" type="text/x-icon"/>
<style type="text/css">
table.wwFormTable {
font: 12px verdana, arial, helvetica, sans-serif;
border-width: 1px;
border-color: #030;
border-style: solid;
color: #242;
background-color: #ada;
width: 30%;
margin-left:35%;
margin-right:35%;
margin-top:15%;
} 
</style>
</head>


<body>
<h1 style="text-align:center"> <font color="black">佳瑞佳仓存管理系统</font></h1>
<s:actionerror />
<s:form action="login.action" method="post">
	<s:textfield name="username" key="用户名" size="20" />
	<s:password name="password" key="用户密码" size="20" />
	<tr>
	<td style="text-align:left">
   
	</td>
	<td style="text-align:left">
	<s:submit value="登录" name="login" theme="simple"/>
	<s:reset value="重置" name="reset" theme="simple"/>
	</td>
	</tr>
</s:form>
</body>
</html>
