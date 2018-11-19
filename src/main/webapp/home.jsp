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

<title>Equilibrium</title>

<script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
	integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ"
	crossorigin="anonymous">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
	integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
	integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
	crossorigin="anonymous"></script>

<link href="${pageContext.request.contextPath}/resources/css/index.css"
	rel="stylesheet">

<style>
#menu {
	background-color: #16385dd1;
}

#menu:hover {
	background-color: #16385d;
}

.menuitem {
	padding-right: 10px;
}
</style>

<script>
	var currentLocation = window.location.href.split("/");
	var currentLocation2 = "";
	for (var i = 0; i < currentLocation.length - 1; i++) {
		currentLocation2 += currentLocation[i] + "/";
	}

	function Register() {
		window.location = currentLocation2 + "register";
	}
	
	function StaffLogin() {
		window.location = currentLocation2 + "loginstaff";
	}
</script>
</head>

<body>

	<nav class="navbar navbar-toggleable-md navbar-inverse fixed-top"
		id="menu">
	<button class="navbar-toggler navbar-toggler-right" type="button"
		data-toggle="collapse" data-target="#navbarCollapse"
		aria-controls="navbarCollapse" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<a class="navbar-brand" href="#"><img class="logo"
		src="${pageContext.request.contextPath}/resources/images/logo.png"
		alt="logo"></a>
	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
		</ul>
		${test}
		<form class="navbar-form navbar-right" method="post"
			action="loginprocess" modelAttribute="login">
			<div class="row">
				<div class="menuitem" style="color:red;font-size:15px;padding-top:10px;"><b>${error}</b></div>
				<div class="menuitem">
					<input type="email" id="inputEmail" class="form-control"
						name="email" placeholder="Email address" required autofocus>
				</div>
				<div class="menuitem">
					<input type="password" id="inputPassword" class="form-control"
						name="password" placeholder="Password" required>
				</div class="menuitem">
				<div class="menuitem">
					<button type="submit" class="btn btn-success">Sign In</button>
				</div>
				<div class="menuitem">
					<button type="button" class="btn btn-danger" onClick="Register();">Sign
						Up</button>
				</div>
			</div>
		</form>
	</div>
	</nav>

	<div style="top: 9px;" id="myCarousel" class="carousel slide"
		data-ride="carousel">
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
		</ol>
		<div class="carousel-inner" role="listbox">
			<div class="carousel-item active">
				<img class="second-slide"
					src="https://sporty.com.tr/wp-content/uploads/2017/05/homepage-background-kick-boks.jpg"
					alt="logo">
				<div class="container">
					<img
						src="https://sporty.com.tr/wp-content/uploads/2017/05/gril-starting-run.png"
						alt="Gril Starting Run"
						data-ww="['407px','407px','407px','407px']"
						data-hh="['525','525','525','525']" width="407" height="525"
						data-lazyload="https://sporty.com.tr/wp-content/uploads/2017/05/gril-starting-run.png"
						data-no-retina=""
						style="width: 407px; height: 525px; transition: none; text-align: left; line-height: 0px; border-width: 0px; margin: 0px; padding: 0px; letter-spacing: 0px; font-weight: 400; font-size: 13px;">
					<div class="carousel-caption d-none d-md-block">
						<h1>Just Do It!</h1>
						<p>I had the chance to visit Equilibrium for the first time
							and it is definitely one of the best gyms I have ever been in! It
							has something for everyone and is very affordable!</p>
						<p>Sign up now.</p>
						<p>
							<a class="btn btn-lg btn-danger" href="./register"
								role="button">Sign Up</a>
						</p>
					</div>
				</div>
			</div>
			<div class="carousel-item" style="background-color: #020002">
				<div class="tp-mask-wrap"
					style="position: absolute; display: block; overflow: visible;">
					<img
						src="https://sporty.com.tr/wp-content/uploads/2017/05/homepage-title-bar-image-one.png"
						alt="Kadın ve Erkek Fitness Model"
						data-ww="['306px','306px','306px','306px']"
						data-hh="['514px','514px','514px','514px']" width="718"
						height="1205"
						data-lazyload="https://sporty.com.tr/wp-content/uploads/2017/05/homepage-title-bar-image-one.png"
						data-no-retina=""
						style="width: 306px; height: 514px; transition: none; text-align: inherit; line-height: 0px; border-width: 0px; margin: 0px; padding: 0px; letter-spacing: 0px; font-weight: 400; font-size: 13px;">
				</div>

				<div class="container">
					<div class="carousel-caption d-none d-md-block" style="color: #fff">
						<h1>Design your body with professional instructors.</h1>
						<p>Excellent equipment, awesome group exercise classes and
							instructors, friendly atmosphere, friendly staff, owners who are
							responsive to members' concerns and suggestions. Gym is also
							clean and attractive. I highly recommend Equilibrium. Oh, and
							their price is definitely reasonable !</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Learn
								more</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<a class="carousel-control-prev" href="#myCarousel" role="button"
			data-slide="prev"> <span class="carousel-control-prev-icon"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="carousel-control-next" href="#myCarousel" role="button"
			data-slide="next"> <span class="carousel-control-next-icon"
			aria-hidden="true"></span> <span class="sr-only">Next</span>
		</a>
	</div>

	<div class="container marketing">
		<div class="row">
			<div class="col-lg-4">
				<img class="rounded-circle"
					src="${pageContext.request.contextPath}/resources/images/body-fit.jpg"
					alt="Generic placeholder image" width="180" height="180">
				<h2>Bob Harper</h2>
				<p>Robert "Bob" Harper is an American personal trainer and author. He appears on the American television series The Biggest Loser.“TODAY I just did 18.1 (a CrossFit workout) ... in the same room where I went into cardiac arrest,” he wrote. “To say I am grateful for my life is a MAJOR understatement. The whole time I did that workout I just kept saying to myself ‘I’m still here.’”</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="rounded-circle"
					src="${pageContext.request.contextPath}/resources/images/zumba.jpg"
					alt="Generic placeholder image" width="200" height="180">
				<h2>AI LEE SYARIEF</h2>
				<p>In 2010 she fell in love with the Zumba® program and went on to become a licensed Zumba instructor. In February 2012, she was selected as a Zumba® Jammer and presented her first ZIN™ Jam session. In December 2012, she was selected as a ZES™ representing Switzerland and Austria.</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="rounded-circle"
					src="${pageContext.request.contextPath}/resources/images/yoga.jpg"
					alt="Generic placeholder image" width="180" height="180">
				<h2>Marla Apt</h2>
				<p>Marla Apt's classes at the Iyengar Yoga Institute of Los Angeles are packed. But she doesn't let the popularity go to her hea—she stays focused on transmitting the tradition that she holds so dear. "Asana and pranayama are understood in Iyengar Yoga as a means to practice the yamas and niyamas, gain emotional stability, connect with your subtle anatomy, and steady the mind," she says. "I hope to convey this to the best of my ability to students."</p>
			</div>
			<!-- /.col-lg-4 -->
		</div>
		<!-- /.row -->


		<footer>
		<p class="float-right">
			<button type="button" class="btn btn-primary" onClick="StaffLogin();">Staff
				Login</button>
		</p>
		<p>
			&copy; 2018 Equilibrium
		</p>
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