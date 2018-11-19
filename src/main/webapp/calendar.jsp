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

<title>Calendar</title>

<link
	href="${pageContext.request.contextPath}/resources/css/fullcalendar.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/fullcalendar.print.css"
	rel="stylesheet" media='print'>

<script
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/fullcalendar.min.js"></script>



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

<link
	href="${pageContext.request.contextPath}/resources/css/searchuser.css"
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

	function Editprofile() {
		window.location = currentLocation2 + "editprofile";
	}

	function Deleteprofile() {
		$("#delete").toggleClass("show");
		$("#delete").toggleClass("hide");
	}
</script>

<style>
.hide {
	visibility: hidden;
}

.show {
	visibility: visible;
}
</style>


<script>

	$(document).ready(function() {
		var d = new Date();
		var nowdate = d.getFullYear() + "-" + (d.getMonth()+1) + "-" + d.getDate();
		console.log(nowdate);
		
		var arr=new Array();
		arr=${dersler};
		console.log(arr);
		
		var lessons=[];
		var lesson={};
		
		for(var i=0; i<arr.length;i++){
			lesson={
				"title": arr[i].name,
				"url": arr[i].url,
				"start":  arr[i].date,
			};
			lessons.push(lesson);
			console.log(arr[0].name);
		}
		
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			defaultDate: nowdate,
			eventLimit: true, // allow "more" link when too many events
			events: lessons,
		});
		
	});

</script>
<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}

	#calendar {
		max-width: 900px;
		margin: 0 auto;
	}
	
	.fc-title{
		color:white !important;
	}
	
	.fc-agendaWeek-button{
		display:none;
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
	<a class="navbar-brand" href="./"> <img class="logo"
		src="${pageContext.request.contextPath}/resources/images/logo.png"
		alt="logo"> <sup style="color: white; font-size: 16px;">Trainer</sup>
	</a>

	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./trainerlessons">Lessons
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./profile">Profile <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./staffcalendar">Calendar
			</a></li>
		</ul>


		<form class="navbar-form navbar-right" action="search">
			<div class="row">
				<div style="padding-right: 5px;">
					<input type="search" class="form-control" name="name"
						placeholder="What are you looking!" required autofocus>
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

	<div style="padding-top:30px;" id='calendar'></div>

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