
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous"></link>
<link rel="stylesheet" href="css/main.css"></link>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<script
	src="~/node_modules/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>

<script type="text/javascript">
	function submitform() {
		document.myform.submit();
	};

	function selectDate(lpr) {

		console.log("Id : " + lpr);

		var selectedValue = document.getElementById(lpr).value;
		var value = "Rented";

		console.log("Selected Value : " + selectedValue);

		var n = value.localeCompare(selectedValue);
		console.log("n : " + n);
		if (n == 0) {
			var ret = lpr.replace('status2', '');

			document.getElementById('fromDate2' + lpr.replace('status2', '')).readOnly = true;
			let current_datetime = new Date();
			let formatted_date = current_datetime.getFullYear() + "-"
					+ (current_datetime.getMonth() + 1) + "-"
					+ current_datetime.getDate();
			console.log(formatted_date);
			document.getElementById('fromDate2' + lpr.replace('status2', '')).value = formatted_date;
		} else {
			document.getElementById('fromDate2' + lpr.replace('status2', '')).readOnly = false;
		}
	};

	function validateForm(id) {
		var x = new Date(document.forms[id]["fromDate2"].value);
		var y = new Date(document.forms[id]["toDate2"].value);

		if ((x - y) > 0) {
			alert("FormDate should not be before ToDate");
			return false;
		}
		return true;
	};
</script>
</head>
<body>
	<jsp:include page="nav.jsp" />
	<br />
	<br />

	<div class="container">

		<!-- Search box-->
		<form method="GET" action="${contextPath}/vehicle-catalog-filter"
			class="input-group mb-3">
			<div class="input-group mb-3">
				<div class="input-group-prepend">

					<select name="filter" class="form-control" placeholder="filter"
						name="filter" id="filterType">
						<option value="make">Make</option>
						<option value="model">Model</option>
						<option value="color">Color</option>
						<%--						<option value="available">Availability</option>--%>
						<option value="greater">Greater than year</option>
						<option value="lesser">Lesser than year</option>
					</select> <br>
				</div>
				<input type="text" class="form-control"
					placeholder="Term to filter vehicles..." aria-label="clientName"
					aria-describedby="button-addon2" name="value" id="value" required>
				<div class="input-group-append">
					<button class="btn btn-outline-secondary" type="submit"
						id="button-addon2">Search</button>
				</div>
			</div>
		</form>
		<br />

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
										<button type="button" class="btn btn-outline-info"
											data-toggle="collapse" data-target="#${listValue.lpr}"
											aria-expanded="false" aria-controls="${listValue.lpr}">View
											Detail</button>
									</td>
								</tr>
								<tr>
									<td id="${listValue.lpr}" class="collapse"
										data-parent="#${listValue.lpr}" colspan="7">
										<div>
											<Table class="table table-striped dataTable"
												id="${listValue.lpr}Reservation">
												<thead>
													<tr>
														<td>Reserved/Rented by Client</td>
														<td>From</td>
														<td>To</td>
														<td>State</td>

														<c:set var="inputDisplay" value="${disableButton}" />
														<c:choose>
															<c:when test="${inputDisplay == 1}">
																<td>Actions</td>
															</c:when>
														</c:choose>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="transactionListValue"
														items="${listValue.vehicleTransactionList}">
														<form>
															<tr>
																<td>
																	<p>${transactionListValue.clientRecord.firstName}
																		${transactionListValue.clientRecord.lastName}</p>
																</td>
																<td>
																	<p>${transactionListValue.startDate}</p>
																</td>
																<td>
																	<p>${transactionListValue.endDate}</p>
																</td>
																<td>
																	<p>${transactionListValue.status}</p>
																</td>

																<td>
																	<div class="row">
																		<c:set var="inputDisplay" value="${disableButton}" />
																		<c:choose>
																			<c:when test="${inputDisplay == 1}">
																				<div class="col-md-auto">
																					<a
																						href="${pageContext.request.contextPath}/return-transaction/${transactionListValue.transactionId}/${transactionListValue.vehicleRecord.lpr}"
																						onclick="return confirm('Are you sure?')"
																						class="btn btn-outline-info">Return</a>
																				</div>
																				<div class="col-md-auto">
																					<a
																						href="${pageContext.request.contextPath}/cancel-transaction/${transactionListValue.transactionId}/${transactionListValue.vehicleRecord.lpr}"
																						onclick="return confirm('Are you sure?')"
																						class="btn btn-outline-danger">Cancel</a>
																				</div>
																			</c:when>
																		</c:choose>

																	</div>
																</td>
															</tr>
														</form>
														<!-- Modal -->
														<div class="modal fade"
															id="myModal${transactionListValue.transactionId}"
															tabindex="-1" role="dialog"
															aria-labelledby="myModalLabel">
															<div class="modal-dialog" role="document">
																<div class="modal-content">
																	<div class="modal-header">

																		<h5 class="modal-title" id="myModalLabel">Edit
																			Rental/Reservation</h5>
																		<button type="button" class="close"
																			data-dismiss="modal" aria-label="Close">
																			<span aria-hidden="true">X</span>
																		</button>
																	</div>
																	<div class="modal-body">
																		<s:form method="post" id="myForm"
																			action="${pageContext.request.contextPath }/edit-transaction/${listValue.lpr}">
																			<div class="form-group">
																				<label for="clientName">Reserved/Rented by
																					Client</label> <input class="form-control" id="clientName"
																					readonly="true" name="clientName"
																					value="${transactionListValue.clientRecord.firstName} ${transactionListValue.clientRecord.lastName}" />
																			</div>
																			<div class="form-group">
																				<label for="licensePlateRecord">License
																					Plate Record</label> <input type="text"
																					class="form-control" id="licensePlateRecord"
																					name="licensePlateRecord" readonly="true"
																					value="${transactionListValue.vehicleRecord.lpr}">
																			</div>
																			<div class="form-group">
																				<label for="fromDate">From</label> <input
																					type="text" class="form-control" id="fromDate"
																					name="fromDate" placeholder="yyyy-mm-dd"
																					value="${transactionListValue.startDate}">
																			</div>

																			<div class="form-group">
																				<label for="toDate">To</label> <input type="text"
																					class="form-control" id="toDate"
																					placeholder="yyyy-mm-dd" name="toDate"
																					value="${transactionListValue.endDate}">
																			</div>

																			<div class="form-group">
																				<label for="status">Status</label> <select
																					class="form-control" id="status"
																					value="${transactionListValue.status}"
																					name="status">
																					<option value="Reserved">Reserved</option>
																					<option value="Rented">Rented</option>


																				</select>
																			</div>

																			<div class="modal-footer">
																				<button class="btn btn-outline-primary"
																					type="submit">Save</button>

																			</div>

																		</s:form>
																	</div>
																</div>
															</div>
														</div>
														<!---------end of Modal------->
													</c:forEach>

												</tbody>
											</Table>


											<c:set var="inputDisplay" value="${disableButton}" />
											<c:choose>
												<c:when test="${inputDisplay == 1}">
													<button type="button" class="btn btn-outline-primary"
														data-toggle="modal"
														data-target="#myModal2${listValue.lpr}">Add
														Transaction</button>
												</c:when>
											</c:choose>



											<!-- Start modal2 -->
											<div class="modal" id="myModal2${listValue.lpr}"
												tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
												data-backdrop="false">
												<div class="modal-dialog" role="document">
													<div class="modal-content">
														<div class="modal-header">
															<%--															<button type="button" class="close" data-dismiss="modal"--%>
															<%--																aria-label="Close">--%>
															<%--																<span aria-hidden="true">&times;</span>--%>
															<%--															</button>--%>
															<h5 class="modal-title" id="myModalLabel2">Assign
																Vehicle to Client</h5>
															<button type="button" class="close" data-dismiss="modal"
																aria-label="Close">
																<span aria-hidden="true">X</span>
															</button>
														</div>
														<div class="modal-body">
															<s:form method="post" id="myForm${listValue.lpr}"
																action="${pageContext.request.contextPath}/assign-vehicle/${listValue.lpr}"
																onsubmit="return validateForm(this.id)">

																<div class="form-group">
																	<label for="forClient">Reserve Or Rent For
																		Client</label> <select class="form-control" id="forClient"
																		name="forClient">
																		<c:forEach var="client" items="${clients}">
																			<option value="${client.driversLicenseNumber}">${client.firstName}
																				${client.lastName}</option>
																		</c:forEach>
																	</select>
																</div>
																<div class="form-group">
																	<label for="fromDate2">From</label> <input type="text"
																		class="form-control" id="fromDate2${listValue.lpr}"
																		name="fromDate2" placeholder="yyyy-mm-dd"
																		pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
																		required>
																</div>

																<div class="form-group">
																	<label for="toDate2">To</label> <input type="text"
																		class="form-control" id="toDate2" name="toDate2"
																		placeholder="yyyy-mm-dd"
																		pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
																		required>
																</div>

																<div class="form-group">
																	<label for="status">Status</label> <select
																		class="form-control" id="status2${listValue.lpr}"
																		name="status2" onchange="selectDate(this.id)">
																		<option value="Reserved">Reserved</option>
																		<option value="Rented">Rented</option>
																	</select>
																</div>

																<div class="modal-footer">
																	<button class="btn btn-primary" type="submit"
																		value="Save">Save</button>

																</div>

															</s:form>
														</div>
													</div>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

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
						if (x.innerHTML.toLowerCase() > y.innerHTML
								.toLowerCase()) {

							// If so, mark as a switch and break the loop:
							shouldSwitch = true;
							break;
						}
					} else if (dir == "desc") {
						if (x.innerHTML.toLowerCase() < y.innerHTML
								.toLowerCase()) {

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
					switchcount++;
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
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
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
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</body>
</html>
