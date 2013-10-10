<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<html>
<%
   String status = (String) session.getAttribute("NoExtraList"); 
   if("noextralist" == status )
   {
       session.removeAttribute("NoExtraList");
%>
<script type="text/javascript">
    alert("Sorry! There are NO OSes need to be synced!")
</script> 
<META HTTP-EQUIV="Refresh" CONTENT="0;URL=syncOStilesAction.action"/>
<%
    
    }
    else
    {
%>

<head>
<title>Extra OS List</title>
<link href="<s:url value="/css/vmfactory.css"/>" rel="stylesheet" type="text/css"/>
<h3>
<center>
OS Synchronization
</center>
</h3>
</head>

<body>
<center>
<div style="text-align:center">
<s:form action="writeExtraOSinDBtilesAction" method="post">
	<tr>
	<td>
	<b>Client:</b>
	</td>
	<td align="left">
	<s:property value="clientFolder"/>
	</td>

<s:checkboxlist label="Extra OS" list="extraOsNameList" 
	   name="extraOsName"/>
<s:submit key="Sync with vSphere" name="sync"/>
</s:form>
</div>
</center>
<div style="text-align:center">
<s:url action="syncOStilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to client check page]</u></s:a>
</div>
</body>
</html>
<%
    }
%>