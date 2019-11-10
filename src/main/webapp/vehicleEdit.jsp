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

<jsp:include page="nav.jsp" />

<div class="container">
	<h2>Vehicle Update</h2>
	<s:form method="post" modelAttribute="vehcileRecord"
			action="${pageContext.request.contextPath }/update-vehicle/${lpr}">
		<div class="row">
			<div class="form-group col-sm-6">
				<label for="year">LicensePlateRecord</label>
				<input type="text" class="form-control" id="lpr" placeholder="ABC-123" name="lpr" value="${lpr}" pattern="[A-Za-z]{3}-[0-9]{3}" readonly="readonly">
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6">
				<label for="carType">Car type:</label>
				<input type="text" class="form-control" id="carType" placeholder="Car type" name="carType" value="${carType}" required>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6">
				<label for="make">Make:</label>
				<input type="text" class="form-control" id="make" placeholder="car make" name="make" value="${make}" required>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6">
				<label for="model">Model:</label>
				<input type="text" class="form-control" id="model" placeholder="make model" name="model" value="${model}" required>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6">
				<label for="color">Car Color</label>
				<input type="text" class="form-control" id="color" placeholder="Color" name="color" value="${color}">
				<p id="errDl" style="color: red"></p>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6">
				<label for="year">Make year</label>
				<input type="number" class="form-control" id="year" placeholder="yyyy" name="year" value="${year}" min="1500" max="2030" required>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-sm-6">
				<label for="version">Version</label>
				<input type="text" class="form-control" id="version" name="version" value="${version}" readonly="readonly">
			</div>
		</div>
		<div class="row">
			<div class="col-md-auto">
				<input class="btn btn-primary" type="submit" value="Update" />
			</div>
		</div>
	</s:form>
</div>
</body>
</html>