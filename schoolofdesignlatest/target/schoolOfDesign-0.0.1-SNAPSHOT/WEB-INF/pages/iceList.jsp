<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<%@include file="header.jsp"%>

<body>

	<section id="container" class="">
		<!--header start-->
		<%@include file="top.jsp"%>

		<!--header end-->

		<!--sidebar start-->

		<%@include file="sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h2 class="page-header" style="color: #A9A9A9">ICA LIST</h2>

					</div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column">
						<form id="addAnnouncement"
							action="${pageContext.request.contextPath}/showIcToByCourse"
							method="post">
							<fieldset>
								<legend> ICA List </legend>
								<div class="row">
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
											<%-- <label for="subject">Module Name</label> <select
												id="courseName" name="courseId" class="form-control"
												required="required" onchange="getIcFacultyCourse(this)">
												<option value="">Please select Module</option>
												<c:forEach items="${courseData}" var="entry"
													varStatus="loop">
													<option value='<c:out value="${entry.key}"/>'>
														<c:out value="${entry.value}" /></option>
												</c:forEach>
											</select> --%>
											<label for="subject">Module Name</label> <select
												id="courseName" name="courseId" class="form-control"
												required="required" onchange="getIcFacultyCourse(this)">
												<option value="">Please select Module</option>

												<c:forEach var="preAssigment" items="${preAssigments}"
													varStatus="status">
													<option value="${preAssigment.sap_course_id}">${preAssigment.course_Name}</option>
												</c:forEach>

											</select>
										</div>
									</div>


								</div>
								<div class="col-sm-12 column">
									<div class="form-group"></div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>

			</section>
			<div class="clearfix"></div>
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
</body>
<script>
	$(function() {

		var data = [];
		var obj = {
			width : "100%",
			height : "400",
			title : "ICA List Grid",
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
			title : "ICA Id",
			dataIndx : "icId",
			width : 100,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}

		},

		{
			title : "ICA Name",
			dataIndx : "iceName",
			width : 100,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}

		},

		{
			title : "Owner Faculty Name",
			dataIndx : "ownerfaculyName",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		}, {
			title : "Assigned Faculty Name",
			dataIndx : "assignedfacultyName",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		},

		{
			title : "Status",
			dataIndx : "status",
			width : 150,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}

		}, {
			title : "Edit ICA",
			dataIndx : "grade",
			width : 150,
			dataType : "string"

		}, {
			title : "Start Grading",
			dataIndx : "grade",
			width : 150,
			dataType : "string"

		}, {
			title : "View Graded",
			dataIndx : "status",
			width : 150,
			dataType : "string"

		} ];
		obj.dataModel = {

			data : data

		};
		$("#grid_array").pqGrid(obj);

	});
</script>
<script>
	if (document.getElementById("courseName")) {
		//alert("courseName")
		var courseList = document.getElementById("courseName");
		var courseValue = courseList.options[courseList.selectedIndex].value;
		if (courseValue)
			getIcFacultyCourse(null);
	}

	function getIcFacultyCourse(sel) {
		var courseList = document.getElementById("courseName");
		//alert("courseList "+courseList)
		var courseValue = courseList.options[courseList.selectedIndex].value;
		//alert("courseValue "+courseValue)
		var gridUrl = "${pageContext.request.contextPath}/showIcToByCourse?courseId="
				+ courseValue;

		$
				.ajax({
					type : "GET",
					url : gridUrl,
					success : function(response) {
						var parsedObj = JSON.parse(response);

						$("#grid_array").pqGrid('option', 'dataModel.data',
								parsedObj);

						//getter
						var colModel = $("#grid_array").pqGrid("option",
								"colModel");

						colModel[6].render = function(ui) {
							var rowData = ui.rowData;

							if (rowData['status'] !== 'SUBMIT') {
								return "<a href=${pageContext.request.contextPath}/grade?icId="
										+ rowData['icId']
										+ ">"
										+ "Grade ICA"
										+ "</a>";

							}
							return "";
						};

						colModel[7].render = function(ui) {
							var rowData = ui.rowData;

							if (rowData['status'] == 'SUBMIT') {
								return "<a href=${pageContext.request.contextPath}/lookGradedIc?icId="
										+ rowData['icId']
										+ ">"
										+ "View ICA"
										+ "</a>";

							}
							return "";
						};

						colModel[5].render = function(ui) {
							var rowData = ui.rowData;

							if (rowData['status'] !== 'SUBMIT'
									&& rowData['status'] !== 'DRAFT') {
								return "<a href=${pageContext.request.contextPath}/beforeUpdateIc?icId="
										+ rowData['icId']
										+ ">"
										+ "Edit ICA"
										+ "</a>";

							}
							return "";
						};
						$("#grid_array").pqGrid("option", "colModel", colModel);
						$("#grid_array").pqGrid('refreshDataAndView');
					},
					error : function() {
						alert('Error here');
					}
				});

	}
</script>
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
																
																$(
																'#courseName')
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
</html>