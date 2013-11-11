<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <link href="<s:url value="/css/InventoryMgmt.css"/>" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
    </head>
    <body>
    
    <table frame="hsides" width="100%" align="center">
    <tr>
    <td colspan=2>
                    <tiles:insertAttribute name="header" />
    </td>
    </tr>
    <tr>
    <td valign="top" width="10%" height="800">
                    <tiles:insertAttribute name="menu" />
    </td>
    <td valign="top" width="90%" height="800">
                    <tiles:insertAttribute name="body" />
    </td>
    </tr>
    <tr>
    <td colspan=2>
                    <tiles:insertAttribute name="footer" />
    </td>
    </tr>
    </table>
    </body>
</html>