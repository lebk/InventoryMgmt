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

<center>
<h3>About VMF</h3>
<div id="container">
<div id="link">
<s:url action="jobstatustilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to home page]</u></s:a>
</div>
<table align=center class="borderAll">
    <tr class="even"style="text-align:left">
        <td>Version:</th>
        <td>1.1</td>
    </tr>
    <tr class="even" style="text-align:left">
        <td>Introduction:</th>
        <td>The Virtual Machine Factory (VMF) is a new system to produce VM images that can be deployed to RBCS or other systems that utilize VM images for automation needs!</td>
    </tr>
    <tr class="even" style="text-align:left">
        <td>Contact:</th>
        <td>
        <ul type="disc">
        <li>Daniel Sosa</li>
        <li>Terry Lei</li>
        <li>Azure Gao</li>
        </ul>
        </td>
    </tr>
</table>
</div>
</center>
</body>
</html>