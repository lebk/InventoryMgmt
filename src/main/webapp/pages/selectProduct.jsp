<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Product Management</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css" rel="stylesheet">
#container{width:500px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #linkR{text-align:right;}
</style>

</head>
<body>
<center>
<h2>VMF Product Management</h2>
<div id="container">
<div id="linkR">
<s:url action="admintilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to Administrator Page]</u></s:a>
</div>
<table align=center class="borderAll">
    <tr>
        <th><s:text name="Product Name"/></th>
        <th><s:text name="Update"/></th>
    </tr>
    <s:iterator value="productList" status="status">
        <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
            <td class="nowrap"><s:property value="name"/></td>
            <td class="nowrap">
                <s:url id="updateProductName" action="setProductNametilesAction" escapeAmp="false">
		       	    <s:param name="oldProductName" value="name"/>
		        </s:url> 
                <s:a href="%{updateProductName}">edit</s:a>
            </td>                    
        </tr>  		
	 </s:iterator>
    </table>
</div>
 </center>
</body>
</html>