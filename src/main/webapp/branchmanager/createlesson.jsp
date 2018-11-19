<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Lesson</title>

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

	window.onload = function() {
		var html2="";
		var branchs2 = new Array();
		branchs2=${branchs};
		for(var j=0; j<branchs2.length; j++){
			html2+="<option>"+branchs2[j].name+"</option>";
		}
		document.getElementById("branchs").innerHTML = html2;
		
		var html3="";
		var branchs3 = new Array();
		branchs3=${branch};
		for(var j=0; j<branchs3.length; j++){
			html3+="<option>"+branchs3[j].name+"</option>";
		}
		document.getElementById("branch").innerHTML = html3;
		
		var html4="";
		var trainers = new Array();
		trainers=${trainers};
		for(var j=0; j<trainers.length; j++){
			html4+="<option value='"+trainers[j].name+"'>"+trainers[j].name+"</option>";
		}
		document.getElementById("trainers").innerHTML = html4;
	};
</script>

<style>
.hide {
	visibility: hidden;
}

.show {
	visibility: visible;
}

label {
	 display: block;
	 margin-bottom: 0rem;
	 margin-top: -0.2rem;
}
</style>

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
	<a class="navbar-brand" href="./branchmanagerprofile"> <img class="logo"
		src="${pageContext.request.contextPath}/resources/images/logo.png"
		alt="logo"> <sup style="color: white; font-size: 16px;">Branch Manager</sup>
	</a>

	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./manipulatelessonbranchmanager">Manipulate Lessons
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./branchmanagermembers">Members
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./branchmanagerprofile">Profile <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./branchmanagercalendar">Calendar
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

	<div class="container">

		<div class="row" style="padding-left: 10px; padding-top: 35px;">
			<div class="col-md-12"
				style="padding-left: 10px; padding-bottom: 100px;">
				<form type="post" action="createlessonprocess">
					<div class="row">
						<div class="col-md-9 mb-3">
							<div class="container" method="post">
								<div class="row">
									<div class="col-md-6 mb-3">
										<b> <label>Course Name</label>
										</b><br /> <input name="name" class="form-control-static" />
									</div>
									<div class="col-md-6 mb-3">
										<b> <label>Branch</label>
										</b><br /> <select class="form-control" name="lesson_id" id="branch">
										</select> 
									</div>
								</div>
								<div class="row">
									<div class="col-md-6 mb-3">
										<b> <label>Trainers</label>
										</b><br /> <select class="form-control" name="trainerr" id="trainers">
										</select> 
									</div>
									<div class="col-md-6 mb-3">
										<b> <label>Branch Offices</label>
										</b><br /> <select class="form-control" name="branch" id="branchs">
										</select> 
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<b> <label>Start Date</label></b><br />
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fas fa-calendar-alt"></i>
												</div>
												<input type="date" class="form-control" name="date"
													 required></input>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<b> <label>End Date</label></b><br /> 
										<div class="form-group">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fas fa-calendar-alt"></i>
												</div>
												<input type="date" class="form-control" name="enddate" required></input>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<b> <label>Lesson Dates</label>
										</b><br /> <input name="days"
											class="form-control-static" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-3 mb-3">
							<div style="padding-top: 40px; padding-left: 30px;">
								
							<input type="submit" class="btn-success" value="Create Lesson" />
							</div>
						</div>
					</div>
				</form>
				<div class="menuitem"
					style="color: red; font-size: 15px; padding-left: 16px;">
					<b>${error}</b>
				</div>
			</div>

		</div>

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