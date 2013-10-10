<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>

<center>
<h1>Sorry! There are too many jobs are running, Please wait!</h1>
<table align=center class="borderAll">
<tr class="even">
<td>Product Name:</td>
<td><s:property value="productName"/></td>
</tr>

<tr class="odd">
<td>OS Name:</td> 
<td><s:property value="osName"/></td>
</tr>
</table>
<div style="text-align:center">
<s:url action="jobmanagetilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>Go back to new job page</u></s:a>
</div>
</center>