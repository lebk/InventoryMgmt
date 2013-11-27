<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />
<html>
<head>
<title>产品花色管理</title>
<style type="text/css" rel="stylesheet">
#container {
	width: 500px;
	margin: 10px auto;
}

#container table {
	text-align: center;
	width: 100%
}

#container #link {
	text-align: right;
}
</style>

</head>
<body>
	<center>
		<h3>产品花色管理</h3>
		<div id="container">
			<div id="link">
				<s:url action="addNewProductColortilesAction.action" var="aURL" />
				<s:a href="%{aURL}">
					<u>[添加产品花色]</u>
				</s:a>
			</div>
			<div id="link">
				<s:url action="admintilesAction.action" var="aURL" />
				<s:a href="%{aURL}">
					<u>[返回首页]</u>
				</s:a>
			</div>
			<table align=center class="borderAll">
				<tr>
					<th><s:text name="类型" /></th>
					<th><s:text name="删除" /></th>
					<th>&nbsp;</th>
				</tr>
				<s:iterator value="productColorList" status="status">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td class="nowrap"><s:property value="color" /></td>
						<td class="nowrap"><s:url id="deleteProductColor"
								action="deleteProductColortilesAction">
								<s:param name="selectedProductColorId" value="id" />
							</s:url> <s:a href="%{deleteProductColor}">删除</s:a></td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</center>
</body>
</html>