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

	<div class="container">
		<br />

		<!-- Table for the clients-->

		<div id="ClientTable">
			<div class="row">
				<div class="col">
					<table class="table table-striped" id="ct">
						<thead>
							<tr>
								<th scope="col">Car Type</th>
								<th scope="col">Vehicle Make</th>
								<th scope="col">Model</th>
								<th scope="col">Year</th>
								<th scope="col">Color</th>
								<th scope="col">License Plate Record</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="listValue" items="${vehicles}">
								<tr>
									<td>${listValue.carType}</td>
									<td>${listValue.make}</td>
									<td>${listValue.model}</td>
									<td>${listValue.year}</td>
									<td>${listValue.color}</td>
									<td>${listValue.lpr}</td>
									<td>
										<div class="row"></div>
										<div class="row">
											<div class="col-md-auto">
												<a
													href="${pageContext.request.contextPath}/edit-vehicle/${listValue.lpr}"
													class="btn btn-outline-success">Edit</a>
											</div>
										</div>
										<div class="row">
											<div class="col-md-auto">
												<a
													href="${pageContext.request.contextPath}/delete-vehicle-record/${listValue.lpr}"
													onclick="return confirm('Are you sure?')"
													class="btn btn-outline-danger">Delete</a>
											</div>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<a class="btn btn-primary" href="vehicle-add">Add Vehicle</a>

				</div>
			</div>
		</div>
	</div>
	<script>

    </script>
</body>
</html>