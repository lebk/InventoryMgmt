<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css" rel="stylesheet">
#container{width:500px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #link{text-align:right;}
</style>
</head>
<body>
<h2>库存查询</h2>

<div id="container">
<div id="link">
<s:url action="showProductListtilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[返回首页]</u></s:a>
</div>
<div id="uppserSection">
<table>
<tr>
<td class="nowrap"><s:select label="类型" list="ptList" theme="simple"/></td>
<td class="nowrap"><s:select list="pcList" theme="simple"/></td>
<td class="nowrap"><s:select list="psList" theme="simple"/></td>
</tr>
</table>
</div>

<hr/>

<div id="lowerSection">
</div>
</div>

</body>
</html>