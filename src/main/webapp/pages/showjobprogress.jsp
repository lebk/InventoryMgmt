<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.leikai.services.*, com.leikai.services.impl.*" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<title>Job Progress</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css" rel="stylesheet">
#container{width:500px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #link{text-align:right;}
</style>
<center>
<h3>Job Progress</h3>
</center>
</head>

<body>
<center>
<div id="container">
<div id="link">
<s:url action="jobstatustilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to job status page]</u></s:a>
</div>
<table align=center class="borderAll">
    <tr>
        <th><s:text name="Progress"/></th>
        <th><s:text name="Start Time"/></th>
        <th><s:text name="End Time"/></th>	 
        <th>&nbsp;</th>
    </tr>
<s:subset source="jobprogressDtoList">
   <s:iterator>
   <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
      	<td class="nowrap"><s:property value="jobStatus"/></td>
      	<td class="nowrap"><s:date name="startTime" nice="false" format="HH:mm:ss MM/dd/yyyy"/></td>
      	<td class="nowrap"><s:date name="endTime" nice="false" format="HH:mm:ss MM/dd/yyyy"/></td>   	
    </tr>
   </s:iterator>
</s:subset>
</table>
</div>

<%
   String allowtostop = (String)session.getAttribute("ALLOWTOSTOP");
   String requesttostop = (String)session.getAttribute("REQUESTTOSTOP");
   
   if("YES" == allowtostop)
    {
        session.removeAttribute("ALLOWTOSTOP");
%>

<s:form action="stopJobtilesAction" method="post" theme="simple">
<s:submit key="Stop Job" name="stopjob"/>
</s:form>
<%
    }
%>


<%
   if("YES" == requesttostop )
   {
       session.removeAttribute("REQUESTTOSTOP");
%>

<script type="text/javascript">
    alert("Job is in stop process, please wait!")
</script> 
<META HTTP-EQUIV="Refresh" CONTENT="0;URL=jobstatustilesAction.action"/>
<%
    
    }
%>

</center>
</body>