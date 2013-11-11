<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<title>Start Job</title>
<h3>
<center>
Customize your own VM
</center>
</h3>
<link href="<s:url value="/css/InventoryMgmt.css"/>" rel="stylesheet" type="text/css"/>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css">
table.wwFormTable {
font: 12px verdana, arial, helvetica, sans-serif;
border-width: 1px;
border-color: #030;
border-style: solid;
color: #242;
background-color: #ada;
width: 50%;
margin-left:25%;
margin-right:35%;
margin-top:10%;
} 
</style>
<script type="text/javascript" src="dwr/interface/ProdServ.js"></script>  
<script type="text/javascript" src="dwr/engine.js"></script>  
<script type="text/javascript" src="dwr/util.js"></script>  
<script type="text/javascript">
function isActivate()
{	
        document.getElementById("checkboxactivateid").disabled = false;
        document.getElementById("checkboxsetid").checked = false;
        document.getElementById("checkboxactivateid").checked = false;
		var obj = document.getElementById("dleSelect");
		var index = obj.selectedIndex;
		var productname = obj.options[index].text; 
		ProdServ.isSupportActivation(productname,IsActivation);				
}
function IsActivation(Is)
{
	if(Is == false)
	{		   
			document.getElementById("checkboxactivateid").disabled = true;
	}		
}
</script>
</head>

<body>
<center>
<s:form action="checkJob" id="form1" method="post">
<div style="text-align:center">

<s:doubleselect label="Product and Supported OS "
name="productName" list="productIDOsMap.keySet()"
id = "dleSelect"
onchange="isActivate()"
doubleName="osName" doubleList="productIDOsMap.get(top)"/>

<s:checkbox name="productSetting" id="checkboxsetid" fieldValue="true" label="Prepare VM for RBCS"/>
<s:checkbox name="productActivate" id="checkboxactivateid" fieldValue="true" label="Activate"/>

<s:select label="Select Target Location" 
		headerKey="-1" headerValue="--- Select Your Location---"
		list="locationNameList" 
		id = "selectid"
		name="locationName" />

</div>
<div style="text-align:center">
<s:submit key="Start Job" name="Start"/>	
</div>
</s:form>

<div style="text-align:center">
<s:url action="jobmanagetilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to new job page]</u></s:a>
</div>
</center>
</body>