<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Availble VM</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
</head>

<body>
<center>
<h3>All available VMs</h3>
<%
    String novmfound = (String)session.getAttribute("NOVMFOUND");
    
    if ("true" == novmfound)
    {
        session.removeAttribute("NOVMFOUND");       
%>
    Oops! We don't have any available VMs right now...
<%
    }
    else
    {
%>
<table align=center class="borderAll">
    <tr>
        <th><s:text name="OS Name"/></th>
        <th><s:text name="Product Name"/></th>
        <th><s:text name="User"/></th>
        <th><s:text name="Start Time"/></th>
        <th><s:text name="Status"/></th>
        <th><s:text name="VM Location"/></th>		 
        <th>&nbsp;</th>
    </tr>
    <s:iterator value="jobDtoList" status="status">
        <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
            <td align="left" class="nowrap"><s:property value="osName"/></td>
            <td class="nowrap"><s:property value="prodName"/></td>
            <td class="nowrap"><s:property value="user"/></td>
            <td class="nowrap"><s:date name="startTime" nice="false" format="HH:mm:ss MM/dd/yyyy"/></td>
            <td class="nowrap"><s:property value="jobStatus"/></td>
            <td align="left" class="nowrap"><s:property value="targetVmlocation"/></td>
        </tr>  		
	 </s:iterator>
    </table>
 <%
    }
%>
</body>
</center>
</html>