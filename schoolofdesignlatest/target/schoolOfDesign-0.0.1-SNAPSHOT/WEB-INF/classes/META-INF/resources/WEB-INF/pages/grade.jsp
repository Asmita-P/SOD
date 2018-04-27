<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@include file="header.jsp"%>


<style>
table, th, td {
	border: 1px solid #ddd;
	text-align: left;
}

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	padding: 15px;
}
</style>

<body>
	<!-- container section start -->

	<section id="container" class="">
		<!--header start-->

		<%@include file="top.jsp"%>
		<!--header end-->

		<!--sidebar start-->
		<%@include file="sidebar.jsp"%>S

		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">
							<i class="icon_piechart"></i> ICE
						</h3>
						<ol class="breadcrumb">
						
							<li><i class="icon_piechart"></i>ICE</li>

						</ol>
					</div>
				</div>


				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<form id="grade" action="${pageContext.request.contextPath}/gradeSubmit" method="post">
							<input type="hidden" name="icId" value="${icId}"></input>
							<fieldset>
								<table>
									<tr>
										<td rowspan="2">Sr No</td>
										<td rowspan="2">SAP ID</td>
										<td rowspan="2">Name</td>
										<td>Criteria</td>
										<td>Criteria 1</td>
										<td>Criteria 2</td>
										<td>Criteria 3</td>
										<td>Criteria 4</td>
										<td>Total</td>
									</tr>
									<%-- <tr>
										<c:forEach items="${tableheader}" var="h" varStatus="loop">
											<c:if test="${loop.index < 3}">
												<td rowspan="2">${h}</td>
											</c:if>
											<c:if test="${loop.index >= 3}">
											</c:if>
											<td>${h}</td>
										</c 
									:forEach></tr>--%>

									<tr>

										<td>Weightage</td>
										<c:forEach items="${weightage}" var="entry" varStatus="loop">
											<td id="c${loop.index+1}">${entry}</td>
										</c:forEach>
										<td>100</td>
									</tr>
									<c:forEach items="${mrkList}" var="entry" varStatus="loop">

										<tr>
											<td rowspan="2">${loop.index}${entry.sapId}</td>
											<td rowspan="2"><input type="text"
												id="sapId${loop.index}" value="${entry.sapId}"
												readonly="readonly"></input></td>
											<td rowspan="2">${entry.name}</td>
											<td>out of 100</td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria1${entry.sapId}"
														name="criteria1${entry.sapId}" class="form-control notes"
														value="${entry.criteria_1_marks}">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria2${entry.sapId}"
														name="criteria2${entry.sapId}" class="form-control notes"
														value="${entry.criteria_2_marks}">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria3${entry.sapId}"
														name="criteria3${entry.sapId}" class="form-control notes"
														value="${entry.criteria_3_marks}">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="criteria4${entry.sapId}"
														name="criteria4${entry.sapId}" class="form-control notes"
														value="${entry.criteria_4_marks}">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="total${entry.sapId}" name="total${entry.sapId}"
														class="form-control notes" value="${entry.criteriaTotal}"
														readonly="readonly"> <input type="hidden"
														id="didChange${entry.sapId}"
														name="didChange${entry.sapId}" value="false">
													<div class="input-group-addon"></div>
												</div></td>
											</td>

										</tr>

										<tr>


											<td>Weightage</td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output1${entry.sapId}" name="output1${entry.sapId}"
														class="form-control notes" value="${entry.weightage_1}"
														readonly="readonly">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output2${entry.sapId}" name="output2${entry.sapId}"
														class="form-control notes" value="${entry.weightage_2}"
														readonly="readonly">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output3${entry.sapId}" name="output3${entry.sapId}"
														class="form-control notes" value="${entry.weightage_3}"
														readonly="readonly">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="output4${entry.sapId}" name="output4${entry.sapId}"
														class="form-control notes" value="${entry.weightage_4}"
														readonly="readonly">
													<div class="input-group-addon"></div>
												</div></td>
											<td><div class="input-group">
													<input type="text" data-tier-id="1"
														id="weighted${entry.sapId}" name="weighted${entry.sapId}"
														class="form-control" value="${entry.weightedTotal}"
														readonly="readonly">
													<div class="input-group-addon"></div>
												</div></td>

										</tr>
									</c:forEach>
								</table>
							</fieldset>
							<div class="clearfix"></div>
							<div id="savedStatus"></div>
							<div class="form-group">
								<input type="button" id="saveas" value="SAVE AS DRAFT" /> <input
									type="submit" id="genId" value="GENERATE" />
								<!-- <button class="btn btn-danger" id="genId">GENERATE</button>-->
							</div>
						</form>
					</div>
				</div>


			</section>




		</section>
		<!--main content end-->

	</section>
	<%@include file="footer.jsp"%>

</body>
<script>
	$(".notes").change(function() {

		var idOfCriteria = $(this).attr("id");

		var sumOfWeightages = 0;
		var total = 0;
		var multipliedValue;
		var sapId = idOfCriteria.substring(9);
		$("#didChange".concat(sapId)).val("true");

		if (idOfCriteria.startsWith('criteria1')) {
			multipliedValue = multiply($(this).val(), $("#c1").text());
			var str = "#output1".concat(sapId);
			$(str).val(multipliedValue / 100);
		}
		if (idOfCriteria.startsWith('criteria2')) {
			multipliedValue = multiply($(this).val(), $("#c2").text());
			var str = "#output2".concat(sapId);
			$(str).val(multipliedValue / 100);
		}
		if (idOfCriteria.startsWith('criteria3')) {
			multipliedValue = multiply($(this).val(), $("#c3").text());
			var str = "#output3".concat(sapId);
			$(str).val(multipliedValue / 100);
		}
		if (idOfCriteria.startsWith('criteria4')) {
			multipliedValue = multiply($(this).val(), $("#c4").text());
			var str = "#output4".concat(sapId);
			$(str).val(multipliedValue / 100);
		}

		sumOfWeightages = performsumOfWeightagesCalc(sapId);

		total = performtotalCalc(sapId);
		if (!isNaN(sumOfWeightages))
			$("#weighted".concat(sapId)).val(sumOfWeightages);
		if (!isNaN(total))
			$("#total".concat(sapId)).val(total);
	});

	function performsumOfWeightagesCalc(sapId) {
		var sumOfWeightages = parseFloat($("#output1" + sapId).val())
				+ parseFloat($("#output2".concat(sapId)).val())
				+ parseFloat($("#output3".concat(sapId)).val())
				+ parseFloat($("#output4".concat(sapId)).val());
		return sumOfWeightages;

	}

	function performtotalCalc(sapId) {
		var total = parseFloat($("#criteria1".concat(sapId)).val())
				+ parseFloat($("#criteria2".concat(sapId)).val())
				+ parseFloat($("#criteria3".concat(sapId)).val())
				+ parseFloat($("#criteria4".concat(sapId)).val());
		return total;
	}

	function multiply(x, y) {
		var one = parseInt(x, 10);
		var two = parseInt(y, 10);
		return one * two;
	}

	$("")
</script>

<script type="text/javascript">
	$(document).ready(function() {

		$("#saveas").click(function() {
			alert("here");
			var datastring = $("#grade").serialize();
			$.ajax({
				type : "POST",
				url : "/saveasdraft",
				data : datastring,
				success : function(data) {
					$("#savedStatus").html(data);
				},
				error : function() {
					alert('Error here');
				}
			});
		});
	});
</script>


</html>
