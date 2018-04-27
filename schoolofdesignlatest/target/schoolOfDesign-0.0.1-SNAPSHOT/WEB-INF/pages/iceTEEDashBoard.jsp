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
							<i class="icon_piechart"></i> TEE & ICA DashBoard
						</h3>

					</div>
				</div>


				<div class="row">
					<%-- <div class="col-sm-4 column">
						<div class="form-group">
							<label for="subject">Module Name</label> <select id="courseName"
								name="courseId" class="form-control" required="required"
								onchange="getTEEDashBoardForCourse(this)">
								<option value="">Please select Module</option>
								<c:forEach items="${courseData}" var="entry" varStatus="loop">
									<option value='<c:out value="${entry.key}"/>'>
										<c:out value="${entry.value}" /></option>
								</c:forEach>
							</select>
						</div>
					</div> --%>
					<div class="col-sm-4 column">
						<div class="form-group">
							<label for="semester">Select Semester</label> <select
								id="semester" class="form-control">
								<option value="">Select semester</option>
								<option value="Semester I">Semester I</option>
								<option value="Semester II">Semester II</option>
							</select>
						</div>
					</div>
					<div class="col-sm-4 column">
						<div class="form-group">

							<label for="subject">Module Name</label> <select id="courseName"
								name="courseId" class="form-control" required="required"
								onchange="getTEEDashBoardForCourse(this)">
								<option value="">Please select Module</option>

								<c:forEach var="preAssigment" items="${preAssigments}"
									varStatus="status">
									<option value="${preAssigment.sap_course_id}">${preAssigment.course_Name}</option>
								</c:forEach>

							</select>
						</div>
					</div>
				</div>

				<section class="container">
					<div id="grid_array"></div>


					<div class="row clearfix page-body">
						<div class="col-sm-12 column">
							<fieldset>

								<div class="table-responsive" id="showData"></div>
								<br>
							</fieldset>
						</div>
					</div>

				</section>
			</section>




		</section>
		<!--main content end-->

	</section>
	<%@include file="footer.jsp"%>

</body>
<script>
	$(document)
			.ready(
					function() {

						$("#semester")
								.on(
										'change',
										function() {
											var semesterid = $(this).val();
											if (semesterid) {
												$
														.ajax({
															type : 'GET',
															url : '${pageContext.request.contextPath}/getCourseBySemester?'
																	+ 'semesterid='
																	+ semesterid,
															success : function(
																	data) {
																var json = JSON
																		.parse(data);
																var optionsAsString = "";

																$('#courseName')
																		.find(
																				'option')
																		.remove();
																console
																		.log(json);
																for (var i = 0; i < json.length; i++) {
																	var idjson = json[i];
																	console
																			.log(idjson);

																	for ( var key in idjson) {
																		console
																				.log(key
																						+ ""
																						+ idjson[key]);
																		optionsAsString += "<option value='" +key + "'>"
																				+ idjson[key]
																				+ "</option>";
																	}
																}
																console
																		.log("optionsAsString"
																				+ optionsAsString);

																$('#courseName')
																		.append(
																				optionsAsString);
																$('#courseName')
																		.trigger(
																				'change');

															}
														});
											} else {
												alert('Error no course');
											}
										});

					});
</script>
<script>
	$(function() {

		var data = [];
		var obj = {
			width : "90%",
			height : "400",
			title : "TEE ICA Grid",
			resizable : true,
			draggable : false,
			editable : false,
			filterModel : {
				on : true,
				mode : "AND",
				header : true
			}
		};
		obj.colModel = [ {
			title : "Name",
			dataIndx : "firstName",
			width : 100,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}

		}, {
			title : "SAPID",
			dataIndx : "sapId",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		}, {
			title : "Roll No",
			dataIndx : "rollNo",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		}, {
			title : "TEE TOTAL",
			dataIndx : "teeTotal",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		},

		{
			title : "TEE Weighted Total",
			dataIndx : "teeWeightedTotal",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		},

		{
			title : "ICA TOTAL",
			dataIndx : "iceTotal",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}

		},

		{
			title : "ICA Weighted Total",
			dataIndx : "iceWeightedTotal",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		} ];
		obj.dataModel = {

			data : data

		};
		$("#grid_array").pqGrid(obj);

	});
</script>

<script type="text/javascript">
	$(document).ready(function() {

		$("#print").click(function() {
			window.print();
		});
	});

	function getTEEDashBoardForCourse(sel) {
		var courseList = document.getElementById("courseName");
		var courseValue = courseList.options[courseList.selectedIndex].value;
		var gridUrl = "${pageContext.request.contextPath}/getConsolidatedDetailsBasedOnCourse?courseId="
				+ courseValue;

		$.ajax({
			type : "GET",
			url : gridUrl,
			success : function(response) {
				console.log(response);
				var parsedObj = JSON.parse(response);
				$("#grid_array").pqGrid('option', 'dataModel.data', parsedObj);

				//getter
				var colModel = $("#grid_array").pqGrid("option", "colModel");

				$("#grid_array").pqGrid("option", "colModel", colModel);
				$("#grid_array").pqGrid('refreshDataAndView');

			},
			error : function() {
				alert('Error here');
			}
		});
	}
</script>


</html>
