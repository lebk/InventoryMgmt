<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css" rel="stylesheet">
#container{width:600px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #link{text-align:right;}
#uppserSection{
    height: 80px;
    margin-left: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
	padding-left:18px;
    padding-top:2px;
    padding-bottom:10px;
    padding-right:5px;
	}


.tableHeader { text-align:center; font-weight:bold; font-size:14px; padding-right:10px;background-color:white}


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
<s:form  action="productQuery" enctype="multipart/form-data"  method="post">
<table>
				<tr>
					<th class="tableHeader"><s:text name="类型" /></th>
					<th  class="tableHeader"><s:text name="花色" /></th>
					<th class="tableHeader"> <s:text name="尺寸" /></th>
					<th class="tableHeader"><s:submit value="查询" theme="simple" /></th>
<tr>
<td class="nowrap"><s:select list="ptList" theme="simple"/></td>
<td class="nowrap"><s:select list="pcList" theme="simple"/></td>
<td class="nowrap"><s:select list="psList" theme="simple"/></td>

</tr>
</table>
</s:form>
</div>
</div>

</body>
</html>