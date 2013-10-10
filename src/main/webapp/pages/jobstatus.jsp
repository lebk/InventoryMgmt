<%@ page contentType="text/html; charset=UTF-8" import="com.leikai.services.*, com.leikai.services.impl.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>welcome</title>
<META HTTP-EQUIV="Refresh" CONTENT="15"/>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
 <center>
 <h3>Welcome <s:property value="username" />!</h3>
<%
    String nojobfound = (String)session.getAttribute("NOJOBFOUND");
    
    if ("true" == nojobfound)
    {
        session.removeAttribute("NOJOBFOUND");       
%>
    Thank you for using VMF, we don't find any record of creating VM by you. Please go to <a href="<s:url action="jobmanagetilesAction"/>"><b>Start</b></a> to create your VM...
<%
    }
    else
    {
%>
 <table align=center class="borderAll">
    <tr>
        <th><s:text name="ID"/></th>
        <th><s:text name="OS Name"/></th>
        <th><s:text name="Product Name"/></th>
        <th><s:text name="User"/></th>
        <th><s:text name="Start Time"/></th>
        <th><s:text name="Status"/></th>
        <th><s:text name="JobProgress"/></th>		 
        <th>&nbsp;</th>
    </tr>
    <s:iterator value="jobDtoList" status="status">
        <tr class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
            <td class="nowrap"><s:property value="id"/></td>
            <td class="nowrap"><s:property value="osName"/></td>
            <td class="nowrap"><s:property value="prodName"/></td>
            <td class="nowrap"><s:property value="user"/></td>
            <td class="nowrap"><s:date name="startTime" nice="false" format="HH:mm:ss MM/dd/yyyy"/></td>
            <td class="nowrap"><s:property value="jobStatus"/></td>
            <td class="nowrap">
                   	<s:url id="showJobProgress" action="showJobProgresstilesAction">
		       		   <s:param name="jobID" value="id"/>
		       		</s:url> 
                <s:a href="%{showJobProgress}">Progress</s:a>
            </td>
        </tr>  		
	 </s:iterator>
 </table>
 <s:url id="url_first" action="jobstatustilesAction">
    <s:param name="pageNow" value="1"/>
 </s:url>
 <s:a href="%{url_first}">First Page</s:a>
 &nbsp;
 &nbsp;
 &nbsp;
 <s:url id="url_pre" action="jobstatustilesAction">
    <s:param name="pageNow" value="pageNow-1"/>
 </s:url>
 <s:url id="url_next" action="jobstatustilesAction">
    <s:param name="pageNow" value="pageNow+1"/>
 </s:url>
 <s:if test="pageNow>1">
 <s:a href="%{url_pre}">[Page Up]</s:a>
 </s:if>
 <s:if test="pageNow<=1">
 [Page Up]
 </s:if>
 &nbsp;
 &nbsp;
 &nbsp;
 <s:if test="pageNow<pageInTotal">
 <s:a href="%{url_next}">[Page Down]</s:a>
 </s:if>
 <s:if test="pageNow>=pageInTotal">
 [Page Down]
 </s:if>
 &nbsp;
 &nbsp;
 &nbsp;
<s:url id="url_last" action="jobstatustilesAction">
    <s:param name="pageNow" value="pageInTotal"/>
 </s:url>
 <s:a href="%{url_last}">Last Page</s:a>
 <%
    }
%>
  </center>
 </body>
 </html>