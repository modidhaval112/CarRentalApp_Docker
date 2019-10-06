<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/main.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js" />
<script src="https://code.jquery.com/jquery-3.3.1.js"
	integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
	rossorigin="anonymous" />

<script>
                $(function(){
                        $("#header").load("header.html");
                });
        </script>
</head>
<body>
	<div class="header"></div>
	<nav
		class="navbar navbar-fixed-top navbar-light bg-light navbar-expand-lg">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarTogglerDemo01"
			aria-controls="navbarTogglerDemo01" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
			<a class="navbar-brand" href="#">Car Rental</a>
			<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
				<li class="nav-item "><a class="nav-link"
					href="vehicle-catalog">Vehicle Catalog<span class="sr-only"></span></a>
				</li>
				<li class="nav-item active"><a class="nav-link"
					href="client-register">Client Register<span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item "><a class="nav-link" href="online-help">Online
						Help<span class="sr-only"></span>
				</a></li>
			</ul>
			<button
				class=" form-inline my-2 my-lg-0 btn btn-outline-success my-2 my-sm-0"
				id="logout" type="submit"
				onclick="document.forms['logoutForm'].submit()">Logout</button>
			<form id="logoutForm" method="POST" action="${contextPath}/logout">
			</form>
		</div>
	</nav>

	<br />
	<div class="row">
	<div class="col-2"></div>
		<div class="col-6">
			<div class="text-danger font-weight-bold">
				<span>${errorMsg}</span>
			</div>
			<div class="text-success font-weight-bold">
				<span>${successMsg}</span>
			</div>
			<div class="text-warning font-weight-bold">
				<span>${warningMsg}</span>
			</div>
		</div>
		<div class="col"></div>
	</div>
	<br />

	<div class="container">
		<br />

		<!-- Table for the clients-->

		<div id="ClientTable">
			<div class="row">
				<div class="col">
					<table class="table table-striped" id="ct">
						<thead>
							<tr>
								<th scope="col">id</th>
								<th scope="col">Client Name</th>
								<th scope="col">information</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="listValue" items="${clients}">
								<tr>
									<th scope="row">1</th>
									<td>${listValue.firstName}${listValue.lastName}</td>
									<td>
										<div class="row">
											<div class="col-md-auto">
												<p>Phone Number</p>
											</div>
											<div class="col-md-auto">
												<div>${listValue.phoneNumber}</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-auto">
												<p>Drivers license</p>
											</div>
											<div class="col-md-auto">
												<div>${listValue.driversLicenseNumber}</div>
											</div>
											<div class="col-md-auto">
												<p>Expiration Date</p>
											</div>
											<div class="col-md-auto">
												<div class="dropdown">
													<div>${listValue.expirationDate}</div>
													<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
														<button class="dropdown-item" type="button">Action</button>
														<button class="dropdown-item" type="button">Another
															action</button>
														<button class="dropdown-item" type="button">Something
															else here</button>
													</div>
												</div>
											</div>
										</div>
									</td>
									<td>
										<div class="row"></div>
										<div class="row">
											<div class="col-md-auto">
												<a
													href="${pageContext.request.contextPath}/${listValue.driversLicenseNumber}"
													class="btn btn-outline-success">Edit</a>
											</div>
										</div>
										<div class="row">
											<div class="col-md-auto">
												<a
													href="${pageContext.request.contextPath}/delete-client-record/${listValue.driversLicenseNumber}"
													onclick="return confirm('Are you sure?')"
													class="btn btn-outline-danger">Delete</a>
											</div>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<a class="btn btn-primary" href="client-sign-up">Add Client</a>

				</div>
			</div>
		</div>
	</div>
	<script>

    </script>
</body>
</html>