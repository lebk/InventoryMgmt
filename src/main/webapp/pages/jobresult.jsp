<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>

<center>
<h3>Job Details</h3>
</center>
<table align=center class="borderAll">
<tr class="odd">
<td>Product Name:</td>
<td><s:property value="productName"/></td>
</tr>

<tr class="even">
<td>Product Version: </td> 
<td><s:property value="productVersion"/></td>
</tr>

<tr class="odd">
<td>OS Name:</td> 
<td><s:property value="osName"/></td>
</tr>

<tr class="even">
<td>Location:</td> 
<td><s:property value="locationName"/></td>
</tr>

<tr class="odd">
<td>Job Status:</td>
<td><b>Complete</b></td>
</tr>

</table>