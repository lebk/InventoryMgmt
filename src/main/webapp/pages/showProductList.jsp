<%@ page contentType="text/html; charset=UTF-8"
	import="com.lebk.services.*, com.lebk.services.impl.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>欢迎使用本系统</title>
<META HTTP-EQUIV="Refresh" CONTENT="15" />
<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<center>
		<h3>
			欢迎使用本系统，
			<s:property value="username" />
			!
		</h3>
		<%
		  String nojobfound = (String) session.getAttribute("NOJOBFOUND");

		  if ("true" == nojobfound)
		  {
		    session.removeAttribute("NOJOBFOUND");
		%>
		显示当前库存状态
		<%
		  } else
		  {
		%>
		<table align=center class="borderAll">
			<tr>
				<th><s:text name="编号" /></th>
				<th><s:text name="名字" /></th>
				<th><s:text name="类型" /></th>
				<th><s:text name="花色" /></th>
				<th><s:text name="大小" /></th>
				<th><s:text name="数量" /></th>
				<th>&nbsp;</th>
			</tr>
			<s:iterator value="productList" status="status">
				<tr
					class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
					<td class="nowrap"><s:property value="id" /></td>
					<td class="nowrap"><s:property value="name" /></td>
					<td class="nowrap"><s:property value="ptTypeId" /></td>
					<td class="nowrap"><s:property value="ptColorId" /></td>
					<td class="nowrap"><s:property value="ptSizeId" /></td>
					<td class="nowrap"><s:property value="ptNumber" /></td>
					<!--             <td class="nowrap"><s:date name="startTime" nice="false" format="HH:mm:ss MM/dd/yyyy"/></td>
            <td class="nowrap"><s:property value="jobStatus"/></td>
            <td class="nowrap">
                   	<s:url id="showJobProgress" action="showJobProgresstilesAction">
		       		   <s:param name="jobID" value="id"/>
		       		</s:url> 
                <s:a href="%{showJobProgress}">Progress</s:a>
            </td>
     -->
				</tr>
			</s:iterator>
		</table>
		<s:url id="url_first" action="showProductListtilesAction">
			<s:param name="pageNow" value="1" />
		</s:url>
		<s:a href="%{url_first}">第一页</s:a>
		&nbsp; &nbsp; &nbsp;
		<s:url id="url_pre" action="showProductListtilesAction">
			<s:param name="pageNow" value="pageNow-1" />
		</s:url>
		<s:url id="url_next" action="showProductListtilesAction">
			<s:param name="pageNow" value="pageNow+1" />
		</s:url>
		<s:if test="pageNow>1">
			<s:a href="%{url_pre}">[上一页]</s:a>
		</s:if>
		<s:if test="pageNow<=1">
 [上一页]
 </s:if>
		&nbsp; &nbsp; &nbsp;
		<s:if test="pageNow<pageInTotal">
			<s:a href="%{url_next}">[下一页]</s:a>
		</s:if>
		<s:if test="pageNow>=pageInTotal">
 [下一页]
 </s:if>
		&nbsp; &nbsp; &nbsp;
		<s:url id="url_last" action="showProductListtilesAction">
			<s:param name="pageNow" value="pageInTotal" />
		</s:url>
		<s:a href="%{url_last}">最后一页</s:a>
		<%
		  }
		%>
	</center>
</body>
</html>