<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<DIV class=header >
<DIV class=title_img>
<IMG src="/InventoryMgmt/css/InventoryIcon.jpg"/>
</DIV>
<DIV id=welcome_msg>
<span style="FLOAT:left">当前用户：<%=session.getAttribute("username")%>  &nbsp; &nbsp;</span>
<a href="<s:url action="logout"/>"><font color="yellow">注销</font></a>
</P>
</DIV>
</DIV>
