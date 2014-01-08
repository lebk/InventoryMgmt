<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.lebk.services.*, com.lebk.services.impl.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<title>产品详细信息</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css" rel="stylesheet">
#container{width:500px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #link{text-align:right;}
</style>
<center>
<h2>产品明细目录</h2>
</center>
</head>

<body>
<center>
<div id="container">
<div id="link">
<s:url action="showProductListtilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[返回首页]</u></s:a>
</div>
<table align=center class="borderAll">
    <tr>
        <th><s:text name="编号"/></th>
        <th><s:text name="产品编号"/></th>
        <th><s:text name="出库/入库"/></th>
         <th><s:text name="数量"/></th>
         <th><s:text name="操作员"/></th>
         <th><s:text name="时间"/></th>	 
    </tr>
<s:subset source="productDetailsList">
   <s:iterator>
   <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
      	<td class="nowrap"><s:property value="id"/></td>
      	<td class="nowrap"><s:property value="poId"/></td>
      	<td class="nowrap"><s:property value="businessType"/></td>
      	<td class="nowrap"><s:property value="ptNumber"/></td>
      	<td class="nowrap"><s:property value="opUser"/></td>
      	<td class="nowrap"><s:date name="date" nice="false" format="HH:mm:ss MM/dd/yyyy"/></td>   	
    </tr>
   </s:iterator>
</s:subset>
</table>
</div>

</center>
</body>