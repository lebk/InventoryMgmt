<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<head>
<center>
<h3>Account&Password Setting</h3>
</center>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OS Edit</title>
<link href="<s:url value="/css/InventoryMgmt.css"/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
</head>

<s:form action="updateOsAccountandPasswordResulttilesAction" enctype="multipart/form-data" method="post">

<div style="text-align:center">
    <s:textfield name="oldOsName" key="OS Name" size="60"/>
</div>

<%--
<div style="text-align:center">
    <s:textfield name="newOsName" key="New OS Name"/>
</div> 
--%>

<div style="text-align:center">
    <s:textfield name="osAccount" key="New Account"/>
</div>

<div style="text-align:center">
    <s:textfield name="osPassword" key="New Password"/>
</div> 

<div style="text-align:center">    
    <s:submit key="update" name="update"/>
</div>

</s:form>