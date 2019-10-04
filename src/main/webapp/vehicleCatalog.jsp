<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head><%@ page language="java"
               contentType="text/html; charset=ISO-8859-1"
               pageEncoding="ISO-8859-1"%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/main.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"/>
    <script src="~/node_modules/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>

</head>
<body>
    <nav class="navbar navbar-fixed-top navbar-light bg-light navbar-expand-lg">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
            <a class="navbar-brand" href="#">Car Rental</a>
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="vehicle-catalog">Vehicle Catalog<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="client-register">Client Register<span class="sr-only"></span></a>
            </li>
            </ul>

            <button class=" form-inline my-2 my-lg-0 btn btn-outline-success my-2 my-sm-0" id="logout" type="submit" onclick="document.forms['logoutForm'].submit()">Logout</button>

            <form id="logoutForm" method="POST" action="${contextPath}/logout">
            </form>

        </div>
    </nav>

    <br/>
    <br/>

    <div class="container">
        <!-- Search box-->
        <form method="GET" action="${contextPath}/vehicle-catalog-filter" class="input-group mb-3">
        <div class="input-group mb-3">
           <div class="input-group-prepend">

                  <select name="filter" class="form-control" placeholder="filter" name="filter" id="filterType">
                    <option value="make">Make</option>
                    <option value="model">Model</option>
                    <option value="color">Color</option>
                    <option value="available">Availability</option>
                  </select>
                  <br>
                  </div>
            <input type="text" class="form-control" placeholder="Search a vehicle" aria-label="clientName"
            aria-describedby="button-addon2" name="value" id="value">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Search</button>
            </div>
           </div>
           </form>
        <br/>

        <!-- Table for the vehicle catalog-->
        <div id="VehicleCatalogTableSection">
            <div class="row">
                <div class="col">
                    <table class="table table-hover dataTable" id="VehicleCatalogTable">
                        <thead>
                            <tr>
                                <th onclick="sortTable(0)">Vehicle Type</th>
                                <th onclick="sortTable(1)">Make</th>
                                <th onclick="sortTable(2)">Model</th>
                                <th onclick="sortTable(3)">Year</th>
                                <th onclick="sortTable(4)">Color</th>
                                <th onclick="sortTable(5)">License Plate Number</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="listValue" items="${vehicles}">
                                <tr class="header">
                                    <td>${listValue.carType}</td>
                                    <td>${listValue.make}</td>
                                    <td>${listValue.model}</td>
                                    <td>${listValue.year}</td>
                                    <td>${listValue.color}</td>
                                    <td>${listValue.lpr}</td>
                                    <td>
                                        <button type="button" class="btn btn-outline-info" data-toggle="collapse" data-target="#${listValue.lpr}" aria-expanded="false" aria-controls="${listValue.lpr}">View Detail</button>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="${listValue.lpr}" class="collapse" data-parent="#${listValue.lpr}" colspan="6">
                                        <div>
                                            <Table class="table table-striped dataTable" id="${listValue.lpr}Reservation">
                                                <thead>
                                                    <tr>
                                                        <td>Reserved/Rented by Client</td>
                                                        <td>From</td>
                                                        <td>To</td>
                                                        <td>State</td>
                                                        <td>Action</td>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    
                                                        <tr>
                                                            <td>
                                                                <div class="dropdown">
                                                                    <button disabled class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    Dominick Cobb
                                                                    </button>
                                                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                                        <a class="dropdown-item" href="#">Asterix</a>
                                                                        <a class="dropdown-item" href="#">Obelix</a>
                                                                        <a class="dropdown-item" href="#">Cesar</a>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <!--<div>
                                                                    <input type="text" class="form-control" id="From" placeholder="Start date" required>
                                                                    <script type="text/javascript">
                                                                        $(document).ready(function() {
                                                                            $('#MaintenancePeriodStartDate').datepicker({
                                                                            format: "yyyy/mm/dd"
                                                                            });
                                                                        });
                                                                    </script>
                                                                </div>-->
                                                                <p>${listValue.startDate}</p>
                                                            </td>
                                                            <td>
                                                                <p>${listValue.endDate}</p>
                                                            </td>
                                                            <td>
                                                                <div class="dropdown">
                                                                    <button disabled class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButtonState" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    Reserved
                                                                    </button>
                                                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButtonState">
                                                                        <a class="dropdown-item" href="#">Rented</a>
                                                                        <a class="dropdown-item" href="#">Available</a>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <a onclick="return confirm('Are you sure?')" class="btn btn-outline-danger">Delete</a>
                                                            </td>
                                                        </tr>
                                                    
                                                </tbody>
                                            </Table>
                                            <a class="btn btn-primary" >Add Transaction</a>
                                      </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <script>
    // Sorting of table comes from: https://www.w3schools.com/howto/howto_js_sort_table.asp
    function sortTable(n) {
        var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
        table = document.getElementById("VehicleCatalogTable");
        switching = true;

        // Set the sorting direction to ascending:
        dir = "asc";

        /* Make a loop that will continue until no switching has been done: */
        while (switching) {
            // Start by saying: no switching is done:
            switching = false;
            rows = table.rows;

            // Loop through all table rows (except the first, which contains table headers):
            for (i = 1; i < (rows.length - 1); i++) {

                // Start by saying there should be no switching:
                shouldSwitch = false;

                // Get the two elements you want to compare, one from current row and one from the next:
                x = rows[i].getElementsByTagName("TD")[n];
                y = rows[i + 1].getElementsByTagName("TD")[n];

                // Check if the two rows should switch place, based on the direction, asc or desc:
                if (dir == "asc") {
                    if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {

                        // If so, mark as a switch and break the loop:
                        shouldSwitch = true;
                        break;
                    }
                    } else if (dir == "desc") {
                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {

                            // If so, mark as a switch and break the loop:
                            shouldSwitch = true;
                            break;
                        }
                    }
                }

                if (shouldSwitch) {
                    // If a switch has been marked, make the switch and mark that a switch has been done:
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;

                    // Each time a switch is done, increase this count by 1:
                    switchcount ++;
                } else {

                    // If no switching has been done AND the direction is "asc", set the direction to "desc" and run the while loop again.
                    if (switchcount == 0 && dir == "asc") {
                    dir = "desc";
                    switching = true;
                }
            }
        }
    }
    </script>
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