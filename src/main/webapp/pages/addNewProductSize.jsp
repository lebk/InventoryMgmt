<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<h3>
	<center>添加产品尺寸</center>
</h3>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加产品尺寸</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />
</head>

<div id="link">
	<s:url action="showProductSizeListtilesAction" var="aURL" />
	<s:a href="%{aURL}">
		<u>[返回产品尺寸列表界面]</u>
	</s:a>
</div>

<s:form action="addProductSizetilesAction" enctype="multipart/form-data"
	method="post">
	<div style="text-align: center">
		<s:textfield name="addProductSize" key="产品尺寸" />
	</div>
	<div style="text-align: center">
		<s:submit key="提交" name="update" />
	</div>
</s:form>
