<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css" rel="stylesheet">
#container{width:500px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #link{text-align:right;}
</style>

</head>


<body>
<center>
<div id="container">
<div id="link">
<s:url action="jobmanagetilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>Go back to new job page</u></s:a>
</div>
<%
   String errorstatus = (String) session.getAttribute("JobErrorID"); 
   if("Error" == errorstatus )
   {
       session.removeAttribute("JobErrorID");
%>

<table align=center class="borderAll">
<tr class="odd">
<td>
Job Result:
</td>
<td>
<b>Error!</b>
</td>
</tr>

<%
    }
%>


<%
   String completestatus = (String) session.getAttribute("JobCompleteID"); 
   if("Complete" == completestatus )
   {
     session.removeAttribute("JobCompleteID");
%>

<table align=center class="borderAll">
<tr class="odd">
<td>
Job Result:
</td>
<td>
<b>Complete!</b>
</td>
</tr>

<%
    }
%>
</div>
</center>
</body>