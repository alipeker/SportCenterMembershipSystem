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

label {
	 display: block;
	 margin-bottom: 0rem;
	 margin-top: -0.2rem;
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
    	
    
        function show() {
            $("#user1").toggle();
        }
        function show2() {
            $("#user2").toggle();
        }
        function showclass() {
            $("#table1").toggle();
        }
        function showclass2() {
            $("#table2").toggle();
        }
        
        
        $(document).ready(function() {
	    	var lessons=new Array();
	    	lessons=${lessons};
	    	
	    	var members=new Array();
	    	members=${members};
	    	
	    	var numbers=new Array();
	    	numbers=${numbers};
	    	
	    	var html="";
	    	console.log(lessons);
	    	console.log(numbers);
	    	console.log(members);
	    	
	    	for(var i=0;i<lessons.length;i++){
	    		html+="<div class='card'><div class='card-header'><h5 class='mb-0'>"+
                    "<button class='btn btn-link collapsed' data-toggle='collapse' data-target='#"+lessons[i].id+"' aria-expanded='false' aria-controls='collapseThree'>"+
                    lessons[i].name+"</button></h5></div><div id='"+lessons[i].id+"' class='collapse' aria-labelledby='collapseOne' data-parent='#accordion'>"+
                "<div class='card-body'><div class='row'><div class='col-md-4'> <ul class='nav nav-pills' style='display:block;'>";
                
                if(i==0 && numbers.length!=1){
		    		for(var j=0;j<parseInt(numbers[i].number);j++){
		    			html+="<li><a data-toggle='tab' href='#"+members[j].id+"'>"+members[j].name+"</a></li>";
		    		}
		    		html+="</ul></div><div class='col-md-8' style='border-left:1px solid rgba(0, 0, 0, 0.247);'><div class='tab-content'>";
		    		
		    		for(var j=0;j<parseInt(numbers[i].number);j++){
		    			html+="<div id='"+members[j].id+"' class='tab-pane fade'><div style='padding-left: 10px;padding-bottom: 100px;'><div class='row'><div class='col-md-3 mb-3'>"+
	                                   "<img width='150px' height:='200px' src='${pageContext.request.contextPath}/resources/images/"+members[j].picture+"'/></div><div class='col-md-9 mb-3'><div class='container' method='post'><div class='row'>"+
	                                   "<div class='col-md-6 mb-3'><b><label>Email</label></b><p class='form-control-static'>"+members[j].email+"</p></div><div class='col-md-6 mb-3'><b><label>Phone Number</label></b>"+
	                                   "<p class='form-control-static'>"+members[j].phonenumber+"</p></div></div><div class='row'><div class='col-md-6 mb-3'><b><label>Name</label></b>"+
	                                   "<p class='form-control-static'>"+members[j].name+"</p></div><div class='col-md-6 mb-3'><b><label>Surname</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].surname+"</p></div></div><div class='row'><div class='col-md-12 mb-6'><b><label>Date of Birth</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].birthday+"</p></div><div class='col-md-6 mb-6'><br /><b><label>Address</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].address+"</p></div></div></div></div></div></div></div>";
		    		}
                }
                else if(numbers.length==1){
                	for(var j=0;j<parseInt(numbers[i].number)-1;j++){
		    			html+="<li><a data-toggle='tab' href='#"+members[j].id+"'>"+members[j].name+"</a></li>";
		    		}
		    		html+="</ul></div><div class='col-md-8' style='border-left:1px solid rgba(0, 0, 0, 0.247);'><div class='tab-content'>";
		    		for(var j=0;j<parseInt(numbers[i].number)-1;j++){
		    			html+="<div id='"+members[j].id+"' class='tab-pane fade'><div style='padding-left: 10px;padding-bottom: 100px;'><div class='row'><div class='col-md-3 mb-3'>"+
		    			 "<img width='150px' height:='200px' src='${pageContext.request.contextPath}/resources/images/"+members[j].picture+"'/></div><div class='col-md-9 mb-3'><div class='container' method='post'><div class='row'>"+
	                                   "<div class='col-md-6 mb-3'><b><label>Email</label></b><p class='form-control-static'>"+members[j].email+"</p></div><div class='col-md-6 mb-3'><b><label>Phone Number</label></b>"+
	                                   "<p class='form-control-static'>"+members[j].phonenumber+"</p></div></div><div class='row'><div class='col-md-6 mb-3'><b><label>Name</label></b>"+
	                                   "<p class='form-control-static'>"+members[j].name+"</p></div><div class='col-md-6 mb-3'><b><label>Surname</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].surname+"</p></div></div><div class='row'><div class='col-md-12 mb-6'><b><label>Date of Birth</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].birthday+"</p></div><div class='col-md-6 mb-6'><br /><b><label>Address</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].address+"</p></div></div></div></div></div></div></div>";
		    		}
                }
                else{
		    		
		    		for(var j=parseInt(numbers[i-1].number);j<parseInt(numbers[i].number);j++){
		    			html+="<div id='"+members[j].id+"' class='tab-pane fade'><div style='padding-left: 10px;padding-bottom: 100px;'><div class='row'><div class='col-md-3 mb-3'>"+
		    			 "<img width='150px' height:='200px' src='${pageContext.request.contextPath}/resources/images/"+members[j].picture+"'/></div><div class='col-md-9 mb-3'><div class='container' method='post'><div class='row'>"+
	                                   "<div class='col-md-6 mb-3'><b><label>Email</label></b><p class='form-control-static'>"+members[j].email+"</p></div><div class='col-md-6 mb-3'><b><label>Phone Number</label></b>"+
	                                   "<p class='form-control-static'>"+members[j].phonenumber+"</p></div></div><div class='row'><div class='col-md-6 mb-3'><b><label>Name</label></b>"+
	                                   "<p class='form-control-static'>"+members[j].name+"</p></div><div class='col-md-6 mb-3'><b><label>Surname</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].surname+"</p></div></div><div class='row'><div class='col-md-12 mb-6'><b><label>Date of Birth</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].birthday+"</p></div><div class='col-md-6 mb-6'><br /><b><label>Address</label></b>"+
	                                    "<p class='form-control-static'>"+members[j].address+"</p></div></div></div></div></div></div></div>";
		    		}
                }
	    		console.log(html);
	    		html+="</div></div></div></div></div></div></div>";
		    	document.getElementById("classes").innerHTML=html;
	    	}
	    	
	    	console.log(html);
	    	
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
	<a class="navbar-brand" href="./trainerprofile"> <img class="logo"
		src="${pageContext.request.contextPath}/resources/images/logo.png"
		alt="logo"> <sup style="color: white; font-size: 16px;">Trainer</sup>
	</a>

	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./attend">Attendance
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./manipulatelessontrainer">Manipulate Lessons
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./lessons">My Lessons
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./trainerprofile">Profile <span class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item active"><a class="nav-link menuitem"
				href="./trainercalendar">Calendar
			</a></li>
		</ul>


		<form class="navbar-form navbar-right" action="search">
			<div class="row">
				<div style="padding-right: 5px;">
					<input type="search" class="form-control" name="name"
						placeholder="What are you looking!" required autofocus>
				</div>
				<div style="color:white;padding-right:7px;">
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

		<div class="row" style="padding-left: 10px; padding-top: 20px;">

			<div class="col-md-12">
				<b style="color: #16385d; font-size: 20px;">Class List</b>
				<div id="classes"></div>
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