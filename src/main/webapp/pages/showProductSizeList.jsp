<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />

<html>
<head>
<title>产品尺寸管理</title>
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
		<h2>产品尺寸管理</h2>
		<div id="container">
			<div id="link">
				<s:url action="addNewProductSizetilesAction.action" var="aURL" />
				<s:a href="%{aURL}">
					<u>[添加产品尺寸]</u>
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
				</tr>
				<s:iterator value="productSizeList" status="status">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td class="nowrap"><s:property value="size" /></td>
						<td class="nowrap"><s:url id="deleteProductSize"
								action="deleteProductSizetilesAction">
								<s:param name="selectedProductSizeId" value="id" />
							</s:url> <s:a href="%{deleteProductSize}">删除</s:a></td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</center>
</body>
</html>