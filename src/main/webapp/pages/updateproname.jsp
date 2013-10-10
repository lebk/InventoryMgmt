<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<head>
<center>
<h3>Update Product Name</h3>
</center>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Product Name</title>
<link href="<s:url value="/css/vmfactory.css"/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
</head>

<s:form action="updateProductNameResulttilesAction" enctype="multipart/form-data" method="post">
<div style="text-align:center">
    <s:textfield name="oldProductName" key="Old Name" size="50"/>
</div>
<div style="text-align:center">
    <s:textfield name="newProductName" key="New Name" size="50"/>
</div> 
<div style="text-align:center">    
    <s:submit key="update" name="update"/>
</div>
</s:form>