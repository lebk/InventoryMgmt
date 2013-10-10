<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>Extra OS List</title>
<link href="<s:url value="/css/vmfactory.css"/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<h3>
<center>
Client Check
</center>
</h3>
<style type="text/css">
table.wwFormTable {
font: 12px verdana, arial, helvetica, sans-serif;
border-width: 1px;
border-color: #030;
border-style: solid;
color: #242;
background-color: #ada;
width:50%;
margin-left:25%;
margin-right:35%;
margin-top:10%;
} 
</style>
</head>

<body>
<center>
<s:form action="showExtraClientOStilesAction" method="post">
<s:radio label="Client Support" list="vsPhereClientList" 
	   name="clientFolder" value="defaultClient"/>
<s:submit key="Confirm Client" name="confirm"/>
</s:form>
<s:url action="showOSListtilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to OS management page]</u></s:a>
</center>
</body>
</html>
