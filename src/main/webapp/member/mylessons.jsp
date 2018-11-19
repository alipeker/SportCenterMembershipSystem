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

<title>Class Information</title>

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
		var currentLocation2="";
		for (var i=0;i<currentLocation.length-1;i++){
			currentLocation2+=currentLocation[i]+"/";
		}
		
	    function Signout(){
	    	window.location=currentLocation2+"logout";
		}
    	
	    $(document).ready(function() {
	    	var arr=new Array();
	    	arr=${lessons};
	    	
	    	var lessonnames="<ul class='nav nav-pills' style='display: block;'>";
	    	var lessoninformations="";
			for(var i=0; i<arr.length;i++){
				var dates = arr[i].lessondates.split("-");
				var datehtml="";
				for(var s=0;s<dates.length;s++){
					if(dates[s]=="1"){
						datehtml+="Monday ";
					}
					else if(dates[s]=="2"){
						datehtml+="Tuesday ";
					}
					else if(dates[s]=="3"){
						datehtml+="Wednesday ";
					}
					else if(dates[s]=="4"){
						datehtml+="Thursday ";
					}
					else if(dates[s]=="5"){
						datehtml+="Friday ";
					}
					else if(dates[s]=="6"){
						datehtml+="Saturday ";
					}
					else if(dates[s]=="7"){
						datehtml+="Sunday ";
					}
				}
				lessonnames+="<li class='active'><a data-toggle='tab' href='#"+arr[i].id+"'>"+arr[i].coursename+"</a></li>";
				
				
				lessoninformations+= "<div id="+arr[i].id+" class='tab-pane fade'> <div style='padding-left: 10px; padding-bottom: 100px;'>"+
					"<div class='row'> <div class='col-md-9 mb-3'>"+
							"<div class='container' method='post'> <div class='row'>"+
									"<div class='col-md-6 mb-3'><b> <label>Course Name</label></b><p class='form-control-static'>"+arr[i].coursename+"</p></div>"+
									"<div class='col-md-6 mb-3'><b> <label>Branch Name</label></b>"+
										"<p class='form-control-static'>"+arr[i].branchname+"</p></div></div>"+
								"<div class='row'><div class='col-md-6 mb-3'><b> <label>Branch Office</label></b>"+
										"<p class='form-control-static'>"+arr[i].branchoffice+"</p></div><div class='col-md-6 mb-3'><b> <label>Trainer Name</label></b>"+
										"<p class='form-control-static'>"+arr[i].trainername+"</p></div></div><div class='row'><div class='col-md-6 mb-3'>"+
									"<b> <label>Start Date</label></b><p class='form-control-static'>"+arr[i].lessondate+"</p></div><div class='col-md-6 mb-3'>"+
									"<b> <label>End Date</label></b><p class='form-control-static'>"+arr[i].enddate+"</p></div></div><div class='row'><div class='col-md-6 mb-3'>"+
								"<b> <label>Lesson Dates</label></b><p class='form-control-static'>"+datehtml+"</p></div></div></div></div></div></div></div>";
			}
			lessonnames+="</ul>";
			document.getElementById("classnames").innerHTML=lessonnames;
			document.getElementById("classinformations").innerHTML=lessoninformations;
		});
	    
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
	<a class="navbar-brand" href="./profile"> <img
		class="logo"
		src="${pageContext.request.contextPath}/resources/images/logo.png"
		alt="logo"> <sup style="color: white; font-size: 16px;">User</sup>
	</a>

	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./mylessons">My Lessons
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./profile">Profile <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./calendar">Calendar
			</a></li>
		</ul>


		<form class="navbar-form navbar-right" action="searchcourse">
			<div class="row">
				<div style="padding-right: 5px;">
					<input type="search" class="form-control" name="name"
						placeholder="Search Courses!" required autofocus>
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

		<div class="card-body">
			<div class="row">
				<div class="col-4" id="classnames">
				</div>
				<div class="col-8" style="border-left: 1px solid rgba(0, 0, 0, 0.247);">
					<div class="tab-content" id="classinformations">
					</div>
				</div>
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

	<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
		integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
		crossorigin="anonymous"></script>

</body>

</html>

