
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0, minimal-ui" />

<title>Svkm's NMIMS deemed to be University</title>

<%-- <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico">
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resources/favicon.png">

<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css" rel="stylesheet">
<!-- bootstrap-progressbar -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-progressbar-3.3.4.min.css"
	rel="stylesheet">

<!-- Custom Toggle Style -->
<link href="${pageContext.request.contextPath}/resources/css/toggle.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/responsive.css" rel="stylesheet">
 --%>
<style>

.login_box {
      width: 830px;
      margin: 16% auto;
      padding: 50px;
      background: #fff;
      content: "";
      display: table;
      clear: both;
      color: #2a3f54;
      box-shadow: 2px 7px 9px rgba(0, 0, 0, 0.30);
      -moz-box-shadow: 2px 7px 9px rgba(0, 0, 0, 0.30);
      -webkit-box-shadow: 2px 7px 9px rgba(0, 0, 0, 0.30);
}

.login_box .col-md-7 p {
      margin-bottom: 10px;
}

.login_box .col-md-1 {
      background: url(../images/line.gif) repeat-y center;
}

.login_box h3 {
      margin: 0;
      padding: 0;
      color: #d2232a;
      letter-spacing: 1px;
      font-size: 30px;
      line-height: 30px;
}

.login_box h6 {
      color: #d2232a;
}

.login_box .col-md-4 input {
      border: 0;
      width: 100%;
      border-bottom: 1px solid #e4e8ec;
      line-height: 24px;
      padding: 10px 0;
      margin: 5px 0;
      background: #fff;
}

.login_box .col-md-4 input[type=submit] {
      border: 2px solid #d2232a;
      color: #d2232a;
      width: 100px;
      padding: 10px 0;
      margin: 5px auto !important;
      border-radius: 30px;
      font-size: 16px;
      letter-spacing: 1px;
      text-transform: uppercase;
      text-align: center;
}

.login_box .col-md-4 input[type=button]:hover {
      border: 2px solid #000;
      color: #000;
}

.login_box .col-md-4 p {
      margin: 10px 0 20px 0;
}

.login_box .col-md-4 img {
      margin-bottom: 20px;
}


.login_box h3 {
      font-size: 26px;
      line-height: 26px;
}

.login_box h4 {
      font-size: 18px;
      line-height: 22px;
}

.login_box .col-md-4 input {
      line-height: 24px;
      padding: 8px 0;
      margin: 3px 0;
}

.login_box .col-md-4 input[type=button] {
      width: 90px;
      padding: 5px 0;
      margin: 5px auto !important;
      font-size: 14px;
}

.login_box .col-md-4 p {
      margin: 15px 0 20px 0;
}

.login_box .col-md-4 img {
      margin-bottom: 10px;
}

.login_box {
      width: 90%;
      padding: 5%;
      margin: 50px auto !important;
}

.login_box .col-md-1 {
      display: block;
      width: 100%;
      background-repeat: repeat-x;
}

.login_Height {
      height: auto !important;
      min-height: 0px !important;
}
.loginmain {
            position:relative;
            height:auto !important;
            min-height:inherit !important;
            width:100%;
            display:block;
      }
      .login_box {
            position:relative;
            width:80%;
            margin:50px auto !important; 
      }
      .loginmain .footer {
            position:relative !important;
            width:100% !important;
            bottom:inherit !important;
      }
      
</style>
</head>

<body class="BG1" style=" background: url(${pageContext.request.contextPath}/resources/images/bg.jpg) repeat center top;
      background-size: 100% 100% !important;">


	<div class="loginmain">

		<div class="login_box" style="width: 500px;">
			<div class="row">
				<div class="col-md-7 login_Height">
					<h3 style="text-align: center;">Welcome to School of Design</h3>

				</div>
				<div class="col-md-1 login_Height"></div>
				<div class="col-md-4 login_Height">
					<a href="dashboard.html"><img src="${pageContext.request.contextPath}/resources/images/logoSOD.png" style="margin-left: 50px; margin-top: 50px;"
						alt=""></a>

					<form class="form"
						action="<c:url value="${pageContext.request.contextPath}/resetPassword" />"
						method="get">
						<!--start form-->
						<!--add form action as needed-->
						<!-- <fieldset> -->
							<div class="form-group">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-envelope color-blue"></i></span>
									<!--EMAIL ADDRESS-->
									<input id="userName" name="username" placeholder="User name"
										class="form-control" type="text" value="${userName}" required>
										
									<p style="font:small-caption;">A mail will be sent to your email id containing your new password details!


								</div>
							</div>
							<div class="form-group">
								<input class="btn btn-lg btn-primary btn-block" onclick="return confirm('A mail will be sent to your email id containing new password details ')"
									value="Send My Password" type="submit" style="width: 200px;">
							</div>
						<!-- </fieldset> -->
					</form>
					<!--/end form-->

				</div>
			</div>
		</div>

		<!-- <div class="footer">
			<footer>
				<div class="col-md-6">
					&copy;2017 NMIMS. All Rights Reserved.<br>V.L.Mehta Road, Vile
					Parle (W), Mumbai, Maharashtra - 400056
				</div>
				<div class="col-md-3">
					<a href="mailto:ngasce@nmims.edu">ngasce@nmims.edu</a><br>1800-1025-136
					( Toll Free)
				</div>
				<div class="col-md-3 tar">
					Follow Us: <a href="#"><i class="fa fa-facebook"></i></a> <a
						href="#"><i class="fa fa-twitter"></i></a> <a href="#"><i
						class="fa fa-google-plus"></i></a> <a href="#"><i
						class="fa fa-youtube"></i></a>
				</div>
			</footer>
		</div>
 -->
	</div>

	<%-- <!-- jQuery -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
	<!-- Bootstrap -->
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- FastClick -->
	<script src="${pageContext.request.contextPath}/resources/js/fastclick.js" type="text/javascript"></script>

	<!-- Balance Height.js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.balance.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/custom.js"></script> --%>


</body>
</html>