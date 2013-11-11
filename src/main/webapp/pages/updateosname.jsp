<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>update your OS name</title>
<link href="<s:url value="/css/InventoryMgmt.css"/>" rel="stylesheet" type="text/css"/>
</head>

<s:form action="updateOsNameResult" enctype="multipart/form-data" method="post">
<div style="text-align:center">
    <s:textfield name="oldOsName" key="Old OS Name" size="60"/>
</div>
<div style="text-align:center">
    <s:textfield name="newOsName" key="New OS Name" size="60"/>
</div> 
<div style="text-align:center">    
    <s:submit key="update" name="update"/>
</div>
</s:form>