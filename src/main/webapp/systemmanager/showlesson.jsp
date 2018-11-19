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
	
	window.onload = function(){ 
		var dates = document.getElementById("lessondates").innerHTML.split("-");
		var datehtml="";
		for(var i=0;i<dates.length;i++){
			if(dates[i]=="1"){
				datehtml+="Monday "
			}
			else if(dates[i]=="2"){
				datehtml+="Tuesday "
			}
			else if(dates[i]=="3"){
				datehtml+="Wednesday "
			}
			else if(dates[i]=="4"){
				datehtml+="Thursday "
			}
			else if(dates[i]=="5"){
				datehtml+="Friday "
			}
			else if(dates[i]=="6"){
				datehtml+="Saturday "
			}
			else if(dates[i]=="7"){
				datehtml+="Sunday "
			}
		}
		
		
		document.getElementById("lessondates").innerHTML=datehtml;
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

.fc-widget-header{
	background-color:#3a87ad;
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

	<div class="container">

		<div class="row" style="padding-left: 10px; padding-top: 35px;">
			<div class="col-md-12"
				style="padding-left: 10px; padding-bottom: 100px;">
				<div class="row">
					<div class="col-md-3 mb-3">
					</div>
				
					<div class="col-md-9 mb-3">
						<div class="container" method="post">
							<div class="row">
								<div class="col-md-6 mb-3">
									<b> <label>Course Name</label>
									</b>
									<p class="form-control-static">${coursename}</p>
								</div>
								<div class="col-md-6 mb-3">
									<b> <label>Branch</label>
									</b>
									<p class="form-control-static">${branchname}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 mb-3">
									<b> <label>Trainer Name</label>
									</b>
									<p class="form-control-static">${trainername}</p>
								</div>
								<div class="col-md-6 mb-3">
									<b> <label>Branch Offices</label>
									</b>
									<p class="form-control-static">${branchoffice}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<b> <label>Start Date</label>
									</b>
									<p class="form-control-static">${lessondate}</p>
								</div>
								<div class="col-md-6">
									<b> <label>End Date</label>
									</b>
									<p class="form-control-static">${enddate}</p>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<b> <label>Lesson Dates</label>
									</b>
									<p id="lessondates" class="form-control-static">${lessondates}</p>
								</div>
							</div>
						</div>
					</div>
					
					
				</div>
				<div class="menuitem" style="color:red;font-size:15px;padding-left:16px;"><b>${error}</b></div>
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