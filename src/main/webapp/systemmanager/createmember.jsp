<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Create Member</title>

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
	integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>

<link href="${pageContext.request.contextPath}/resources/css/index.css"
	rel="stylesheet">

<style>
#menu {
	background-color: #16385d;
}

.menuitem {
	color: rgba(255, 255, 255, 0.822);
}

.menuitem:hover {
	color: rgb(98, 145, 195);
}

.hide {
	display: none;
}

.btn-link {
	color: #4c6475;
}

label {
	display: block;
	margin-bottom: 0rem;
	margin-top: -0.2rem;
}
</style>

<script>
	var currentLocation = window.location.href.split("/");
	var currentLocation2 = "";
	for (var i = 0; i < currentLocation.length - 1; i++) {
		currentLocation2 += currentLocation[i] + "/";
	}

	function Signout() {
		window.location = currentLocation2 + "logout";
	}

	function Editprofile() {
		window.location = currentLocation2 + "editsystemmanagerprofile";
	}
</script>
</head>

<body>

	<nav class="navbar navbar-expand-md fixed-top" id="menu">
	<button class="navbar-toggler navbar-toggler-right" type="button"
		data-toggle="collapse" data-target="#navbarCollapse"
		aria-controls="navbarCollapse" aria-expanded="false"
		aria-label="Toggle navigation">
		<img class="menuitem" width="30px;"
			src="https://kulzos.com/img/logom.png">
	</button>
	<a class="navbar-brand" href="./systemmanagerprofile"> <img class="logo"
		src="${pageContext.request.contextPath}/resources/images/logo.png"
		alt="logo"> <sup style="color: white; font-size: 16px;">System Manager</sup>
	</a>

	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./manipulatelessonsystemmanager">Manipulate Lessons
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./systemmanagermembers">Members
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./systemmanagerstaff">Staffs
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./systemmanagerprofile">Profile <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./systemmanagercalendar">Calendar
			</a></li>
		</ul>


		<form class="navbar-form navbar-right" action="search">
			<div class="row">
				<div style="padding-right: 5px;">
					<input type="search" class="form-control" name="name"
						placeholder="What are you looking!" required autofocus>
				</div>
				<div style="color:white;padding-right:7px;padding-top:3px;">
					<label><input type="radio"  name="optradio" value="M" required>Member</label>
					<label><input type="radio" name="optradio" value="C" required>Course</label>
				</div>
				<div style="padding-right: 5px;">
					<button type="submit" class="btn btn-info">Search</button>
				</div>
				<div style="padding-right: 5px;">
					<button type="button" class="btn btn-danger" onclick="Signout();">Sign
						Out</button>
				</div>
			</div>
		</form>
	</div>
	</nav>

	<div class="container-fluid">

		<form method="post" action="branchmanagerregisterprocess"
			enctype="multipart/form-data" style="padding-left:40%;padding-top:40px;">
				<div class="row">
					<div class="well center-block">

						<div class="row">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="glyphicon glyphicon-envelope"></i>
										</div>
										<input type="email" class="form-control" name="email"
											placeholder="E-Mail" required>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="glyphicon glyphicon-lock"></i>
										</div>
										<input type="password" minlength="6" maxlength="20"
											placeholder="Password" name="password" class="form-control"
											required>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="glyphicon glyphicon-user"></i>
										</div>
										<input type="text" placeholder="First Name" name="name"
											class="form-control" required>

									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="glyphicon glyphicon-user"></i>
										</div>
										<input type="text" placeholder="Last Name" name="surname"
											class="form-control" required>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fas fa-id-card"></i>
										</div>
										<input type="text" name="id_number" pattern="[0-9]{11}"
											placeholder="Identity Number" class="form-control">
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="glyphicon glyphicon-phone"></i>
										</div>
										<input type="text" name="mobile_number" pattern="[0-9]{11}"
											placeholder="Mobile No." class="form-control">
									</div>
								</div>
							</div>
						</div>



						<div class="row">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="glyphicon glyphicon-list-alt"></i>
										</div>
										<textarea class="form-control" name="address"
											placeholder="Address" required></textarea>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fas fa-user-secret"></i>
										</div>
										<textarea class="form-control" name="sec_answer"
											placeholder="Security Answer" required></textarea>
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fas fa-calendar-alt"></i>
										</div>
										<input type="date" class="form-control" name="birthday"
											placeholder="MM/DD/YYY" required></input>
									</div>
								</div>
							</div>
						</div>

						<div class="row widget">
							<div class="col-md-12 col-xs-12 col-sm-12">
								<button class="btn btn-block" style="color: #16385d">
									Submit</button>
							</div>
						</div>
					</div>
			</div>


		</form>
		<hr class="featurette-divider">


		<footer class="container">
		<p class="float-right">
			<a href="#">Back to top</a>
		</p>
		<p>&copy; 2018 Equilibrium</p>
		</footer>

	</div>

	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
		integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
		crossorigin="anonymous"></script>

</body>

</html>