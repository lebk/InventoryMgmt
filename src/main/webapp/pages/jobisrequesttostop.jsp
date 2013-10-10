<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>

<center>
<h1>Sorry! The job you started is in stop process, please wait and try again!</h1>
</center>
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