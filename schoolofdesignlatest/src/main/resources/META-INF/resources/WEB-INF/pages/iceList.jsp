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
						<h2 class="page-header" style="color:	#A9A9A9">
							 ICE LIST
						</h2>

					</div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column">
						<form id="addAnnouncement" action="${pageContext.request.contextPath}/showIcToByCourse"
							method="post">
							<fieldset>



								<legend> ICE List </legend>

								<div class="row">
									<div class="col-sm-4 column">
										<div class="form-group">
											<label for="subject">Course Name</label> <select
												id="courseName" name="courseId" class="form-control"
												required="required">
												<option value="">Please select Course</option>
												<c:forEach items="${courseData}" var="entry"
													varStatus="loop">
													<option value='<c:out value="${entry.key}"/>'>
														<c:out value="${entry.value}" /></option>
												</c:forEach>
											</select>
										</div>
									</div>


									<!-- 									<div class="col-sm-4 column"> -->
									<!-- 										<div class="form-group"> -->
									<!-- 											<label for="acadYear">Year</label> <select id="acadYear" -->
									<!-- 												name="acadYear" class="form-control" required="required"> -->
									<!-- 												<option value="">Select Year</option> -->
									<!-- 												<option value="2015">2017</option> -->
									<!-- 												<option value="2016">2018</option> -->
									<!-- 												<option value="2016">2019</option> -->
									<!-- 												<option value="2016">2020</option> -->
									<!-- 											</select> -->

									<!-- 										</div> -->



									<!-- 									</div> -->
								</div>
								<div class="col-sm-12 column">
									<div class="form-group">
										<input type="button" name="submit"
											class="btn btn-large btn-primary"
											onclick="getIcFacultyCourse();" value="Search"> </input>
										<button id="reset" type="reset" class="btn btn-danger">Reset</button>

									</div>
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

<%@include file="footer.jsp"%>

<script>
	$(function() {

		var data = [];
		var obj = {
			width : "100%",
			height : "400",
			title : "ICE List Grid",
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
			title : "ICE Id",
			dataIndx : "icId",
			width : 100,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}

		}, {
			dataIndx : "courseName",
			title : "Course Name",
			width : 200,
			dataType : "string",
			filter : {
				type : 'textbox',
				condition : 'begin',
				listeners : [ 'keyup' ]
			}
		}, {
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
			title : "Is Repeat",
			dataIndx : "isRepeat",
			width : 150,
			dataType : "boolean",
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
			title : "Grade",
			dataIndx : "grade",
			width : 150,
			dataType : "string"

		}, {
			title : "Update Ic",
			width : 150

		} ];
		obj.dataModel = {

			data : data

		};
		$("#grid_array").pqGrid(obj);

	});
</script>
<script>
	function getIcFacultyCourse() {

		var courseList = document.getElementById("courseName");
		var courseValue = courseList.options[courseList.selectedIndex].value;
		var gridUrl = "/showIcToByCourse?courseId=" + courseValue;

		$.ajax({
			type : "GET",
			url : gridUrl,
			success : function(response) {
				var parsedObj = JSON.parse(response);

				$("#grid_array").pqGrid('option', 'dataModel.data', parsedObj);

				//getter
				var colModel = $("#grid_array").pqGrid("option", "colModel");

				colModel[5].render = function(ui) {
					var rowData = ui.rowData;

					if (rowData['status'] !== 'SUBMIT') {
						return "<a href=/grade?icId=" + rowData['icId'] + ">"
								+ "Grade" + "</a>";

					}
					return "";
				};

				colModel[6].render = function(ui) {
					var rowData = ui.rowData;

					if (rowData['status'] !== 'SUBMIT'
							&& rowData['status'] !== 'DRAFT') {
						return "<a href=/beforeUpdateIc?icId="
								+ rowData['icId'] + ">" + "Edit" + "</a>";

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
</html>
