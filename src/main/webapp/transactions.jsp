<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="css/main.css">

    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script
            src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script
            src="~/node_modules/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>

</head>
<body>
<jsp:include page="nav.jsp"/>

<br/>
<br/>

<div class="container">

    <!-- Search box-->
    <form method="GET" action="#"
          class="input-group mb-3">
        <div class="input-group mb-3">
<%--            <div class="input-group-prepend">--%>

<%--                <select name="filter" class="form-control" placeholder="filter"--%>
<%--                        name="filter" id="filterType">--%>
<%--                    <option value="vehicle-model">Model</option>--%>
<%--                    <option value="vehicle-make">Make</option>--%>
<%--                    <option value="car-type">Type</option>--%>
<%--                    <option value="car-color">Color</option>--%>
<%--                    <option value="client">Client</option>--%>
<%--                    &lt;%&ndash;						<option value="returned">Returned</option>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;						<option value="rented">Rented</option>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;						<option value="reserved">Reserved</option>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;						<option value="cancelled">Cancelled</option>&ndash;%&gt;--%>
<%--                </select> <br>--%>
<%--            </div>--%>
            <input type="text" class="form-control" id="trans-filter-field-id"
                   placeholder="Term to filter vehicles..." aria-label="clientName"
                   aria-describedby="button-addon2" name="value" required>
<%--            <div class="input-group-append">--%>
<%--                <button class="btn btn-outline-secondary" type="submit"--%>
<%--                        id="button-addon2">Search--%>
<%--                </button>--%>
<%--            </div>--%>
        </div>
    </form>
    <br/>

    <%--		<div class="btn-group btn-group-toggle" data-toggle="buttons">--%>
    <%--			<label class="btn btn-secondary active">--%>
    <%--				<input type="radio" name="Rented" id="RentedFilter" autocomplete="off"  value="Rented" > Rented--%>
    <%--			</label>--%>
    <%--			<label class="btn btn-secondary">--%>
    <%--				<input type="radio" name="options" id="ReservedFilter" autocomplete="off" value="Reserved" > Reserved--%>
    <%--			</label>--%>
    <%--			<label class="btn btn-secondary">--%>
    <%--				<input type="radio" name="options" id="ReturnedFilter" value="Returned"autocomplete="off"> Returned--%>
    <%--			</label><label class="btn btn-secondary">--%>
    <%--			<input type="radio" name="options" id="CancelledFilter" value="Cancelled" autocomplete="off"> Cancelled--%>
    <%--		</label>--%>
    <%--		</div>--%>
    <select id='mySelector' class="btn btn-info btn-sm dropdown-toggle">
        <option value="">Filter based on Status</option>
        <option value='Rented'>Rented</option>
        <option value='Reserved'>Reserved</option>
        <option value='Returned'>Returned</option>
        <option value='Cancelled'>Cancelled</option>
    </select>
    <%--		<div class="btn-toolbar" role="toolbar" aria-label="Basic example">--%>
    <div class="btn-group" role="group">
        <a class=" btn btn-outline-secondary btn-sm active" href="${pageContext.request.contextPath }/trans-list"
           style="width:200px" >All</a>
<%--        <a class="btn btn-outline-secondary btn-sm active" href="${pageContext.request.contextPath }/overdue-trans-list"--%>
<%--           style="width:200px">Over Due</a>--%>
<%--        <a class="btn btn-outline-secondary btn-sm active" href="${pageContext.request.contextPath }/due-today-trans-list" style="width:200px">Due Today</a>--%>
    </div>
    <%--		</div>--%>

    <div id="TransactionCatalogTableSection">
        <div class="row">
            <div class="col">
                <table id="mainTable" class="table table-hover dataTable">
                    <thead>
                    <tr>
                        <th>Time Stamp</th>
                        <th>Transaction Number</th>
                        <th>Vehicle Type</th>
                        <th>Model</th>
                        <th>License Plate Number</th>
                        <th>Client Name</th>
                        <th>Reserved Date</th>
                        <th>Return Date</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody id="TransactionCatalogTable">
                    <c:forEach var="listValue" items="${transactionsList}">
                        <tr class="header">
                            <td>${listValue.timeStamp}</td>
                            <td>${listValue.transactionId}</td>
                            <td>${listValue.vehicleType}</td>
                            <td>${listValue.model}</td>
                            <td>${listValue.lpr}</td>
                            <td>${listValue.clientName}</td>
                            <td>${listValue.startDate}</td>
                            <td>${listValue.endDate}</td>
                            <td>${listValue.status}</td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script
            src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script>
        $(document).ready(function () {
            $("#trans-filter-field-id").on("keyup", function () {
                var value = $(this).val().toLowerCase();
                $("#TransactionCatalogTable tr").filter(function () {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
            $('#mySelector').change(function () {
                // $('table').show();
                var selection = $(this).val();
                console.log(selection);
                var dataset = $('#mainTable tbody').find('tr');
                // show all rows first
                dataset.show();
                // filter the rows that should be hidden
                dataset.filter(function (index, item) {
                    return $(item).find('td:last-child').text().indexOf(selection) === -1;
                }).hide();
            });
        });
    </script>
</body>
</html>
