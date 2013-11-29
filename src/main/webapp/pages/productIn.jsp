<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<html>
<head>
<title>产品入库</title>

<style type="text/css" rel="stylesheet">
#container {
	width: 80%;
	margin: 10px auto;
}

#container table {
	text-align: center;
	width: 80%
}

#container #link {
	text-align: right;
}
</style>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css" />
</head>

<body>
	<center>
		<h2>产品入库</h2>
</center>
<s:form action="login" enctype="multipart/form-data" method="post">
	
	<div id="container">
			<div id="link">
				<s:submit value="提交"  theme="simple"/>
			</div>
		<table align=center class="borderAll">
			<tr>
				<th><s:text name="编号" /></th>
				<th><s:text name="类型" /></th>
				<th><s:text name="花色" /></th>
				<th><s:text name="尺寸" /></th>
				<th><s:text name="库存数量" /></th>
				<th><s:text name="入库数量" /></th>
				<th>&nbsp;</th>
			</tr>
			<s:iterator value="productInList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td class="nowrap"><s:property value="id" /></td>
				<td class="nowrap"><s:select list="ptList" theme="simple"/></td>
				<td class="nowrap"><s:select list="pcList" theme="simple"/></td>
				<td class="nowrap"><s:select list="psList" theme="simple"/></td>
					<td class="nowrap"><s:property value="ptNumber" /></td>
					<td class="nowrap"><s:textfield name="inNum" size="5" theme="simple"/></td>
				</tr>
			</s:iterator>
		<table>
		</div>
</s:form>
</body>
</html>