<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>产品入库</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="dwr/interface/ProdServ.js"></script>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript">
function handleChange(index) {
	var typename="selectedProductType["+index+"]";
	var colorname="selectedProductColor["+index+"]";
	var sizename="selectedProductSize["+index+"]";
    var pNum="pNum["+index+"]";
	var type = dwr.util.getValue(typename);
    var color = dwr.util.getValue(colorname);
    var size = dwr.util.getValue(sizename);
    ProdServ.getProductNumber(type,color,size, function(data) {
    dwr.util.setValue(pNum, data);
  });
}
</script>
</head>

<body>
	<center>
		<h2>产品入库</h2>
	</center>

	<s:form action="productInSubmit" enctype="multipart/form-data"
		method="post">

		<div id="container">
			<div id="link">
				<s:submit value="提交" theme="simple" />
			</div>
			<table align=center class="borderAll">
				<tr>
					<th><s:text name="编号" /></th>
					<th><s:text name="类型" /></th>
					<th><s:text name="花色" /></th>
					<th><s:text name="尺寸" /></th>
					<th><s:text name="库存数量" /></th>
					<th><s:text name="入库数量" /></th>
				</tr>
				<s:iterator value="productInList" status="status" var="productInRow">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">
						<td class="nowrap"><s:property value="id" /></td>
						<td class="nowrap"><s:select list="ptList"
								name="productInList[%{#status.index}].selectedProductType"
								theme="simple" id="selectedProductType[%{#status.index}]"
								onchange="handleChange(%{#status.index})" /></td>
						<td class="nowrap"><s:select list="pcList"
								name="productInList[%{#status.index}].selectedProductColor"
								theme="simple" id="selectedProductColor[%{#status.index}]"
								onchange="handleChange(%{#status.index})" /></td>
						<td class="nowrap"><s:select list="psList"
								name="productInList[%{#status.index}].selectedProductSize"
								theme="simple" id="selectedProductSize[%{#status.index}]"
								onchange="handleChange(%{#status.index})" /></td>

						<s:if test="%{#status.index==0}">
							<td class="nowrap"><span id="pNum[0]"></span></td>
						</s:if>
						<s:elseif test="%{#status.index==1}">
							<td class="nowrap"><span id="pNum[1]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==2}">
							<td class="nowrap"><span id="pNum[2]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==3}">
							<td class="nowrap"><span id="pNum[3]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==4}">
							<td class="nowrap"><span id="pNum[4]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==5}">
							<td class="nowrap"><span id="pNum[5]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==6}">
							<td class="nowrap"><span id="pNum[6]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==7}">
							<td class="nowrap"><span id="pNum[7]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==8}">
							<td class="nowrap"><span id="pNum[8]"></span></td>
						</s:elseif>
						<s:elseif test="%{#status.index==9}">
							<td class="nowrap"><span id="pNum[9]"></span></td>
						</s:elseif>
						<s:else>
							<td class="nowrap"><span id="pNum[0]"></span></td>
						</s:else>
						<td class="nowrap"><s:textfield
								name="productInList[%{#status.index}].txnNum" size="5"
								theme="simple" /></td>
					</tr>
				</s:iterator>
				<table>
					</div>
					</s:form>
</body>
</html>