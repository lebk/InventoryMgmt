<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>VMF-Login | Symantec</title>
<link href="<s:url value="/css/vmfactory.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css">
table.wwFormTable {
font: 12px verdana, arial, helvetica, sans-serif;
border-width: 1px;
border-color: #030;
border-style: solid;
color: #242;
background-color: #ada;
width: 30%;
margin-left:35%;
margin-right:35%;
margin-top:15%;
} 
</style>
</head>


<body>
<h2 style="text-align:center"> <font color="black">Symantec VMFactory</font></h2>
<s:actionerror />
<s:form action="login.action" method="post">
	<s:textfield name="username" key="label.username" size="20" />
	<s:password name="password" key="label.password" size="20" />
	<tr>
	<td style="text-align:left">
   
	</td>
	<td style="text-align:left">
	<s:submit value="Login" name="login" theme="simple"/>
	<s:reset value="Reset" name="reset" theme="simple"/>
	</td>
	</tr>
</s:form>
</body>
</html>
