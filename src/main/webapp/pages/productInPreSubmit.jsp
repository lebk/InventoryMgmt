<%@ page contentType="text/html; charset=UTF-8"
	import="com.lebk.services.*, com.lebk.services.impl.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>入库前检测</title>
<META HTTP-EQUIV="Refresh" CONTENT="15" />
<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<center>
		<h3>入库前检测</h3>
		<table align=center class="borderAll">
			<tr>
				<th><s:text name="编号" /></th>
				<th><s:text name="名字" /></th>
				<th><s:text name="类型" /></th>
				<th><s:text name="花色" /></th>
				<th><s:text name="尺寸" /></th>
				<th><s:text name="数量" /></th>
			</tr>
			<s:iterator value="productList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td class="nowrap"><s:property value="id" /></td>
					<td class="nowrap"><s:property value="name" /></td>
					<td class="nowrap"><s:property value="ptType" /></td>
					<td class="nowrap"><s:property value="ptColor" /></td>
					<td class="nowrap"><s:property value="ptSize" /></td>
					<td class="nowrap"><s:property value="ptNumber" /></td>
				</tr>
			</s:iterator>
		</table>
	</center>
</body>
</html>