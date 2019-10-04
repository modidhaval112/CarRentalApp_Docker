<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
	<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">

		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

		<title>Log in with your credentials</title>
	</head>

	<body>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#">Rent My Car</a>
			<div class="collapse navbar-collapse" id="navbarNav"/>
		</nav>
		<div class="container">
			<div class="row">
				<div class="col"></div>
				<div class="col-6">
					<H1>User Authentication</H1>
				</div>
				<div class="col"></div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col-6">
					<form method="POST" action="${contextPath}/login"
						class="form-signin">
						<div class="form-group row">
							<label for="usernameLabel" class="col-sm-2 col-form-label">Username</label>
							<div class="col-sm-10">
								<input name="username" type="text" class="form-control"
									id="username" placeholder="Please enter your user name."
									autofocus>
							</div>
						</div>
						<div class="form-group row">
							<label for="passwordLabel" class="col-sm-2 col-form-label">Password</label>
							<div class="col-sm-10">
								<input name="password" type="password" class="form-control"
									id="password" placeholder="Password">
							</div>
						</div>

						<button type="submit" class="btn btn-primary mb-2">Login</button>
					</form>
				</div>
				<div class="col"></div>
			</div>

			<div class="row">
				<div class="col-5"></div>
				<div class="col-4">
					<div class="form-group text-success font-weight-bold">
						<span>${msg}</span>
					</div>
					<div class="form-group text-danger font-weight-bold">
						<span>${errorMsg}</span>
					</div>
				</div>
				<div class="col"></div>
			</div>

		</div>

		<script
			src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
			integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
			crossorigin="anonymous">
		</script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
			integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
			crossorigin="anonymous">
		</script>
		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
			integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
			crossorigin="anonymous">
		</script>
	</body>
</html>
