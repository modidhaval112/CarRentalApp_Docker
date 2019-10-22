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
		<s:form method="post" modelAttribute="vehicleRecord"
			action="${pageContext.request.contextPath }/update-vehicle/${vehicleRecord.lpr}">
			<div class="row">
				<div class="col-md-auto">
					<input class="btn btn-primary" type="submit" value="Update" />
				</div>
			</div>
		</s:form>
	</div>
</body>
</html>