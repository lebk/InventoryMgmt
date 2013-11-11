<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h3>
<center>
Upload Product
</center>
</h3>

<head>
<title>Upload Product</title>
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
width: 60%;
margin-left:20%;
margin-right:35%;
margin-top:5%;
} 
#progressBar {
	margin-left:40%;
	margin-right:35%;
	margin-top:0.5%;
	
}

#progressBarBox {
	width: 400px;
	height: 30px;
	border: 1px inset;
	background: #eee;
}

#progressBarBoxContent {
	width: 0px;
	height: 30px;
	border-right: 1px solid #444;
	background: #9ACB34;
}
</style>
<script type="text/javascript" src="dwr/interface/UploadMonitor.js"></script>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript">
function refreshProgress()
{
    UploadMonitor.getUploadInfo(updateProgress);
}

function updateProgress(uploadInfo)
{
    if (uploadInfo.inProgress)
    {
         document.getElementById('uploadbutton').disabled = true;
         document.getElementById('file1').disabled = true;
         var d = document.getElementById('formid');
   		 d.setAttribute("disabled", "disabled");
       
        var fileIndex = uploadInfo.fileIndex;

        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);

        document.getElementById('progressBarText').innerHTML = 'upload in progress: ' + progressPercent + '%';

        document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 4) + 'px';

        window.setTimeout('refreshProgress()', 1000);
    }
    else
    {
        document.getElementById('uploadbutton').disabled = false;
        document.getElementById('file1').disabled = false;
        
    }

    return true;
}

function startProgress()
{
    document.getElementById('progressBar').style.display = 'block';
    document.getElementById('progressBarText').innerHTML = 'upload in progress: 0%';
    document.getElementById('uploadbutton').disabled = true;

    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress()", 1500);
    return true;
}


</script>
</head>

<s:form action="uploadFiletilesAction" method="post" enctype="multipart/form-data" id="formid" onsubmit="startProgress()">
<div style="text-align:center">
    <s:select label="Select Product Type" 
		headerKey="-1" headerValue="--- Select Your Product Type ---"
		list="productTypeList" 
		name="yourProductType" 
		id="protype"
	/> 
	<s:textfield label="Product Version" name="productVersion" id="proversion"/>
<%--
    <s:optiontransferselect
     label="OS Option"
     name="leftOs"
     leftTitle="All OS List"
     rightTitle="Supported OS List for Product"
     list="leftOsList"
     multiple="true"
     headerKey="-1"
     headerValue="--- Please Select ---"
     doubleList="rightSupportedOsList"
     doubleName="rightSupportedOs"
     doubleHeaderKey="-1"
     doubleHeaderValue="--- Please Select ---"
 />
--%>    
    <s:checkboxlist label="OS Support" list="supportOsNameList" id="supportOsNameId"
	   name="yourSupportOsName" disabled="false"/>
    <s:file name="fileUpload" key="Product to upload" id="file1"/>
</div> 

<div style="text-align:center">
	<s:submit key="Upload" name="upload" id="uploadbutton"/>
</div>
</s:form>

<div id="progressBar" style="display: none;">			
				<div id="theMeter">
					<div id="progressBarText"></div>
					<div id="progressBarBox">
						<div id="progressBarBoxContent"></div>
					</div>
				</div>
</div>

<div style="text-align:center">
<s:url action="jobmanagetilesAction.action" var="aURL" />
<s:a href="%{aURL}"><u>[Go back to new job page]</u></s:a>
</div>
