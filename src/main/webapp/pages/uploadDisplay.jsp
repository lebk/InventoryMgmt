<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>



<%
    String notallowed = (String) session.getAttribute("NotAllowed");
    if ("true" == notallowed)
    {
       session.removeAttribute("NotAllowed");
%>
<script type="text/javascript">
      alert("Upload is not Allowed! Operation Error, please try again!")
</script>
<META HTTP-EQUIV="Refresh" CONTENT="0;URL=displayUploadFiletilesAction.action"/>
<%
    
    }
    else
    {
%>
<html>
<head>
<style type="text/css" rel="stylesheet">
#container{width:500px; margin:10px auto;}
#container table{text-align:center;width:100%}
#container #link{text-align:right;}
</style>
</head>
<body>
<h1>Product Upload</h1>
<center>
<div id="container">
<div id="link">
<s:url action="jobmanagetilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to new job page]</u></s:a>
</div>
<table align=center class="borderAll">
    <tr class="odd">
        <td>Filename:</th>
        <td><s:property value="fileUploadFileName"/></td>
    </tr>
    <tr class="even">
        <td>File content type:</th>
        <td><s:property value="fileUploadContentType"/></td>
    </tr>
    <tr class="odd">
        <td>File size:</th>
        <td><s:property value="fileUpload.length()"/> bytes</td>
    </tr>
    <tr class="even">
        <td>File Location:</th>
        <td>
        <s:property value="fileUploadDir"/>
        </td>
    </tr>
</table>
</div>
</center>
</body>
</html>
<%
    }
%>

