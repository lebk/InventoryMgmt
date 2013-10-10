<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css">
.borderAll {
    border:0px; width:1000px;
}
</style>
<title>Admin</title>
<h3>
<center>
Administrator Park
</center>
</h3>
</head>


<body>
<table align="center" class="borderAll">
<tr>
<td>
<ul type="square">
<li>
<s:a href="showUserListtilesAction.action">User Management</s:a>
</li>
</ul>
</td>
</tr>

<tr>
<td>
<ul type="square">
<li>
<s:a href="showOSListtilesAction.action">OS Management</s:a>
</li>
</ul>
</td>
</tr>

<tr>
<td>
<ul type="square">
<li>
<s:a href="displayProducttilesAction.action">Product Management</s:a>
</li>
</ul>
</td>
</tr>

<tr>
<td>
<ul type="square">
<li>
<s:a href="displayUploadFiletilesAction.action">Upload Product</s:a>
</li>
</ul>
</ul>
</td>
</tr>

</table>
</body>
</html>