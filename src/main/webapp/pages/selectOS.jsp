<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>OS Management</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css" rel="stylesheet">
#container{width:500px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #linkR{text-align:right;}
#container #linkL{text-align:left;}
</style>

</head>
<body>
<center>
<h3>VMF OS Management</h3>
<div id="container">
<div id="linkR">
<s:url action="syncOStilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[OS Synchronization]</u></s:a>
</div>
<div id="linkR">
<s:url action="admintilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to Administrator Page]</u></s:a>
</div>
<table align=center class="borderAll">
    <tr>
        <th><s:text name="OS Name"/></th>
        <th><s:text name="Update"/></th>
        <th>&nbsp;</th>
    </tr>
    <s:iterator value="osList" status="status">
        <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
            <td class="nowrap"><s:property value="name"/></td>
            <td class="nowrap">
                <s:url id="updateOsAccountandPassword" action="displayAccountandPasswordSettingtilesAction" escapeAmp="false">
		       	    <s:param name="oldOsName" value="name"/>
		        </s:url> 
                <s:a href="%{updateOsAccountandPassword}">edit</s:a>
            </td>                    
        </tr>  		
	 </s:iterator>
    </table>
</div>
 </center>
</body>
</html>