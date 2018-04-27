<!DOCTYPE html>
<html lang="en">
<%@include file="header.jsp"%>

<body>
	<!-- container section start -->

	<section id="container" class="">
		<!--header start-->

		<header class="header dark-bg">

			<div class="toggle-nav">
				<img
					src="http://www.image.edu.in/images/course-banner/graphic-Design.jpg"
					width="60" height="40"></i>
			</div>

			<!--logo start-->
			<span class="lite">School Of
					Design</span>
			<!--logo end-->

			<div class="nav search-row" id="top_menu">
				<!--  search form start -->
				<!-- <ul class="nav top-menu">
					<li>
						<form class="navbar-form">
							<input class="form-control" placeholder="Search" type="text">
						</form>
					</li>
				</ul>-->
				<!--  search form end -->
			</div>

			<div class="top-nav notification-row">
				<!-- notificatoin dropdown start-->
				<ul class="nav pull-right top-menu">

					<!-- task notificatoin start -->


					<!-- user login dropdown start-->
					<li class="dropdown"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#"> <span class="profile-ava">
								<img alt="" src="img/avatar1_small.jpg">
						</span> <span class="username">Jenifer Smith</span> <b class="caret"></b>
					</a>
						<ul class="dropdown-menu extended logout">
							<div class="log-arrow-up"></div>
							<li class="eborder-top"><a href="#"><i
									class="icon_profile"></i> My Profile</a></li>

						</ul></li>
					<!-- user login dropdown end -->
				</ul>
				<!-- notificatoin dropdown end-->
			</div>
		</header>
		<!--header end-->

		<!--sidebar start-->
		<%@include file="sidebar.jsp"%>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
				<div class="row">
					<div class="col-lg-12">
						<h2 class="page-header">
							<img
								src="https://cdn4.iconfinder.com/data/icons/web-pages-seo/512/4-512.png"
								alt="Smiley face" width="42" height="42"> ICE
						</h2>
						<ol class="breadcrumb">
							<li><i class="fa fa-home"></i><a href="index.html">Home</a></li>
							<li><img
								src="https://cdn4.iconfinder.com/data/icons/web-pages-seo/512/4-512.png"
								alt="Smiley face" width="42" height="42"></i>ICE</li>

						</ol>
					</div>
				</div>
				<div class="row page-body">
					<div class="col-sm-12 column">
						<form id="addAnnouncement" action="${pageContext.request.contextPath}/addAnnouncement" method="post">
							<fieldset>

								<input id="courseId" name="courseId" type="hidden" value="" />
								<input id="programId" name="programId" type="hidden" value="" />
								<input id="announcementType" name="announcementType"
									type="hidden" value="INSTITUTE" />

								<legend> Create ICE </legend>

								<div class="row">
									<div class="col-sm-4 column">
										<div class="form-group">
											<label for="subject">Course Name</label> <select
												id="courseName" name="courseName" class="form-control"
												required="required">
												<option value="">Select Course</option>
												<option value="2015">Computer Graphics</option>
												<option value="2016">Data Structures</option>
												<option value="2016">Digital Forensics</option>
												<option value="2016">Design Theory</option>
											</select>
										</div>
									</div>


									<!--<div class="col-sm-4 column">
					<div class="form-group">
						<label for="acadYear">Year</label>
						<select id="acadYear" name="acadYear" class="form-control" required="required">
							<option value="">Select  Year</option>
							<option value="2015">2017</option><option value="2016">2018</option><option value="2016">2019</option><option value="2016">2020</option>
						</select>
					</div>
				</div>-->

									<!--<div class="col-sm-4 column">
							<div class="form-group">
								<label for="endDate">Select Month</label>
								<select id="acadYear" name="acadYear" class="form-control" required="required">
								<option value="">Select  Month</option>
								<option value="2015">Jun</option><option value="2016">Aug</option>
								</select>
							</div>
					  </div>-->
									<div class="col-sm-4 column">
										<div class="form-group">
											<label for="subject">ICE Number</label> <input id="subject"
												name="subject" type="text" class="form-control"
												required="required" value="" />

										</div>

									</div>



								</div>
					</div>
			</section>

			<section class="container">






				<div class="row clearfix page-body">
					<div class="col-sm-12 column">
						<fieldset>
							<legend>&nbsp;ICE</legend>
							<div class="table-responsive">
								<table class="table table-striped table-hover"
									style="font-size: 12px">
									<thead>
										<tr>
											<th>Criteria</th>
											<th>Name</th>
											<th>Weightage</th>
											<th>Skill</th>


										</tr>
									</thead>
									<tbody>


										<tr>
											<td>1</td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>





										</tr>
										<tr>
											<td>2</td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>





										</tr>
										<tr>
											<td>3</td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>





										</tr>
										<tr>
											<td>4</td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>
											<td><INPUT TYPE="TEXT" NAME="name" SIZE="20"></td>





										</tr>





									</tbody>
								</table>

								<button id="submit" name="submit" type="submit"
									class="btn btn-large btn-primary">Create ICE</button>

							</div>
							<br>
						</fieldset>
					</div>
				</div>

















			</section>




			</div>
			<!-- chart morris start -->
			</div>
		</section>
		<!--main content end-->

	</section>

</body>

<%@include file="footer.jsp"%>
</html>
