<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3>
<center>
Add New User
</center>
</h3>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New User</title>
<link href="<s:url value="/css/vmfactory.css"/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
</head>

<s:form action="addUsertilesAction" enctype="multipart/form-data" method="post">
<div style="text-align:center">
    <s:textfield name="addUserName" key="User name"/>
</div>
<%--
<div style="text-align:center">
    <s:textfield name="addUserPassword" key="User password"/>
</div> 
--%>
<div style="text-align:center">
    <s:textfield name="addUserEmail" key="Email"/>
</div> 
<div style="text-align:center">
    <s:checkbox name="addasAdmin" fieldValue="true" label="Add as Admin"/>
</div> 
<div style="text-align:center">    
    <s:submit key="Add" name="update"/>
</div>
</s:form>

<div style="text-align:center">
<s:url action="showUserListtilesAction" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to user management page]</u></s:a>
</div>