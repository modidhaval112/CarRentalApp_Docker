<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>


<html>
<head><%@ page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/main.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"
	integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
	crossorigin="anonymous">
    </script>

</head>
<body>
	<div id="header"></div>
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
					<li class="nav-item "><a class="nav-link" href="online-help">Online Help<span class="sr-only"></span></a>
					</li>
    			</ul>
    			<button
    				class=" form-inline my-2 my-lg-0 btn btn-outline-success my-2 my-sm-0"
    				id="logout" type="submit"
    				onclick="document.forms['logoutForm'].submit()">Logout</button>
    			<form id="logoutForm" method="POST" action="${contextPath}/logout">
    			</form>
    		</div>
    	</nav>
	<div class="container">
		<h2>Client Sign up</h2>
		<s:form method="post" modelAttribute="clientRecord"
		action="${pageContext.request.contextPath }/update-client/${clientRecord.driversLicenseNumber}">
			<div class="row">
				<div class="form-group col-sm-6">
					<label for="firstName">First name:</label> <input type="text"
						class="form-control" id="firstName" placeholder="first name"
						name="firstName" value="${clientRecord.firstName}" required>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-6">
					<label for="lastName">Last name:</label> <input type="text"
						class="form-control" id="lastName" placeholder="last name"
						name="lastName" value="${clientRecord.lastName}" required>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-6">
					<label for="phoneNumber">Phone number:</label> <input type="text"
						class="form-control" id="phoneNumber" placeholder="phone number"
						name="phoneNumber" value="${clientRecord.phoneNumber}" required>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-6">
					<label for="driversLicenseNumber">Drivers license:</label> <input
						type="text" class="form-control" id="driversLicenseNumber"
						placeholder="license" name="driversLicenseNumber"
						value="${clientRecord.driversLicenseNumber}" readonly="readonly">
					<p id="errDl" style="color: red"></p>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-sm-6">
					<label for="expirationDate">Expiration date:</label> <input type="text"
						class="form-control" id="expirationDate" placeholder="yyyy-mm-dd"
						name="expirationDate" value="${clientRecord.expirationDate}" required>
				</div>
			</div>
			<div class="row">
				<div class="col-md-auto">
					<input class="btn btn-primary"  type="submit" value="Update"/>
				</div>
			</div>
		</s:form>
	</div>
	<script src="js/client.js"></script>
</body>
</html>