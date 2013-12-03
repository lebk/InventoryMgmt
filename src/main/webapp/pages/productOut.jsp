<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />
</head>
<body>

	<center>
		<h3>出库单</h3>
		<div id="container">
			<div id="link">
				<s:url action="showProductListtilesAction.action" var="aURL" />
				<s:a href="%{aURL}">
					<u>[返回首页]</u>
				</s:a>
			</div>
		</div>
	</center>
</body>
</html>