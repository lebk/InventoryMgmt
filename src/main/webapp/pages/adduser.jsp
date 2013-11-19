<%@ page contentType="text/html; charset=UTF-8"%>
    
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3>
<center>
添加新用户
</center>
</h3>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加新用户</title>
<link href="<s:url value="/css/InventoryMgmt.css"/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
</head>

<div id="link">
<s:url action="showUserListtilesAction" var="aURL" />
<s:a href="%{aURL}"><u>[返回用户列表界面]</u></s:a>
</div>

<s:form action="addUsertilesAction" enctype="multipart/form-data" method="post">
<div style="text-align:center">
    <s:textfield name="addUserName" key="用户名"/>
</div>

<div style="text-align:center">
    <s:textfield name="addUserPassword" key="用户密码"/>
</div> 

<div style="text-align:center">
    <s:checkbox name="addasAdmin" fieldValue="true" label="添加为管理员？"/>
</div> 
<div style="text-align:center">    
    <s:submit key="提交" name="update"/>
</div>
</s:form>
