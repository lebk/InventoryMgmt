<%@ page contentType="text/html; charset=UTF-8"%>
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
<h3>关于本仓存系统</h3>
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
        <td>简介:</th>
        <td>本仓存系统用于管理仓库的入库与出库，同时可查询库存状态</td>
    </tr>
    <tr class="even" style="text-align:left">
        <td>联系人:</th>
        <td>
        <ul type="disc">
        <li>雷波(lebk.lei@gmail.com)</li>
        <li>林凯(397769236@qq.com)</li>
        </ul>
        </td>
    </tr>
</table>
</div>
</center>
</body>
</html>