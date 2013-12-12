<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>

<html>
    <head>
        <link href="<s:url value="/css/InventoryMgmt.css"/>" rel="stylesheet" type="text/css" />
        <link href="<s:url value="/css/favicon.ico"/>" rel="shortcut icon" type="text/x-icon"/>
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