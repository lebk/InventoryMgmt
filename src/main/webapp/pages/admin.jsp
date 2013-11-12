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
<title>管理页面</title>
<h3>
<center>
基本资料配置
</center>
</h3>
</head>


<body>
<table align="center" class="borderAll">
<tr>
<td>
<ul type="square">
<li>
<s:a href="showUserListtilesAction.action">用户管理</s:a>
</li>
</ul>
</td>
</tr>

<tr>
<td>
<ul type="square">
<li>
<s:a href="showProductTypeListtilesAction.action">产品分类管理</s:a>
</li>
</ul>
</td>
</tr>

<tr>
<td>
<ul type="square">
<li>
<s:a href="showProductSizeListtilesAction.action">产品尺寸管理</s:a>
</li>
</ul>
</td>
</tr>

<tr>
<td>
<ul type="square">
<li>
<s:a href="showProductColorListtilesAction.action">产品花色管理</s:a>
</li>
</ul>
</ul>
</td>
</tr>

</table>
</body>
</html>