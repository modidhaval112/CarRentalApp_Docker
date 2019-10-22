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
    <h2>Vehicle Add</h2>

    <s:form method="post" modelAttribute="vehicleRecord"
            action="${contextPath}/create-vehicle">
        <div class="row">
            <div class="form-group col-sm-6">
                <label for="carType">Car type:</label> <input type="text"
                                                                  class="form-control" id="carType" placeholder="Car Type"
                                                                  name="carType" required>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-6">
                <label for="make">Make:</label><input type="text"
                                                               class="form-control" id="make" placeholder="Car Make"
                                                               name="make"  required>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-6">
                <label for="model">Model:</label> <input type="text"
                                                                class="form-control" id="model" placeholder="Car Model"
                                                                name="model"  required>
            </div>
        </div>
         <div class="row">
            <div class="form-group col-sm-6">
                <label for="color">Color:</label> <input type="text"
                                                                class="form-control" id="color" placeholder="Color"
                                                                name="color"  required>
            </div>
        </div>
         <div class="row">
            <div class="form-group col-sm-6">
                <label for="year">Car Year:</label> <input type="int"
                                                                class="form-control" id="year" placeholder="Car Make Year"
                                                                name="year"  required>
            </div>
        </div>
         <div class="row">
            <div class="form-group col-sm-6">
                <label for="lpr">LPR:</label> <input type="text"
                                                                class="form-control" id="lpr" placeholder="lpr"
                                                                name="lpr"  required>
            </div>
        </div>
     
     
        <div class="row">
            <div class="col-md-auto">
                <input class="btn btn-primary"  type="submit" value="Submit"/>
            </div>
        </div>
    </s:form>
</div>
<script src="js/client.js"></script>
</body>
</html>