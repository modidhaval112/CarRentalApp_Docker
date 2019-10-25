<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	<jsp:include page="nav.jsp" />


	<br />
	<div class="container">
		<div class="row">
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
	</div>
	<br />
	</div>
	<div class="row">
		<div class="col-1"></div>
		<div class="col-8 md-auto">

		<input type="text" class="form-control mb-3" id="client-search"
		   placeholder="Type to search..." aria-label="clientName"
		   aria-describedby="button-addon2" name="value" required>
		</div>
	</div>
	<div class="container">
		<br />
			<!-- Table for the clients-->

		<div >
			<div class="row">
				<div class="col">
					<table class="table table-striped" id="ct">
						<thead>
							<tr>
								<th scope="col">Client Name</th>
								<th scope="col">information</th>
								<th></th>
							</tr>
						</thead>
						<tbody id="ClientTable">
							<c:forEach var="listValue" items="${clients}">
								<tr>
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
										</div>
										<div class="row">
											<div class="col-md-auto">
												<p>Expiration Date</p>
											</div>
											<div class="col-md-auto">
												<div class="dropdown">
													<div>${listValue.expirationDate}</div>
												</div>
											</div>
										</div>
									</td>
									<td>
										
										<div class="row">
											<div class="col-md-auto">
												<a href="${pageContext.request.contextPath}/${listValue.driversLicenseNumber}"
													class="btn btn-outline-success">Edit</a>
											</div>
											<div class="col-md-auto">
												<a  href="${pageContext.request.contextPath}/delete-client-record/${listValue.driversLicenseNumber}"
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
		$(document).ready(function(){
			$("#client-search").on("keyup", function() {
				var value = $(this).val().toLowerCase();
				$("#ClientTable tr").filter(function() {
					$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
				});
			});
		});
	</script>
</body>

</html>