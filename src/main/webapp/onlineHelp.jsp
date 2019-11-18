<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>

<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

    <title>Online Help</title>
</head>
<body>
<jsp:include page="nav.jsp" />

<c:set var="inputDisplay" value="${disableButton}" />
<c:choose>
    <c:when test="${inputDisplay == 0}">
        <div class="container">
            <div class="row">
                <div class="col">

                    <br /> <br />
                    <H1>Admin Functionality</H1>
                    <br />
                    <H3 style="color: crimson">Check if only a single Admin can Login</H3>
                    <p>Steps to check admin login concurrency:</p><br>
                    <p>1. Login as admin (username: admin, password:admin ) in <b>Chrome browser</b>, login will be successful</p>
                    <p>2. Login as admin1 (username: admin1, password: admin1) in <b>Firefox or Internet explorer or Edge browser</b>, Expected output: Error message saying another admin is already logged in.</p>


                    <H2>Transaction History Management</H2>
                    <p>From the 'Transaction History'</p>
                    <ul>
                        <li>
                            <p>The transactions can be filtered based on the Client name, Vehicle License plate number, model, make and also by a specific due date.<br>
                                Just type the data in the search bar.
                            </p>
                            <p>The transactions can also be filtered by the status of the vehicle by selecting options from the drop down menu</p>
                        </li>
                        <li>
                            <p>Can view all the transactions by clicking on 'All'
                                button.</p>
                        </li>
                        <li>
                            <p>Can view overdue the transactions by clicking on 'Over
                                Due' button.</p>
                        </li>
                        <li>
                            <p>Can view transactions which are due today by clicking on
                                'Due Today' button.</p>
                        </li>
                    </ul>
                    <br />

                    <H2>Vehicle Catalog</H2>
                    <H3>Navigating the vehicle catalog</H3>
                    <p>The displayed list of vehicle can be search by entering the
                        a criteria and clicking on search.</p>

                    <h4>The filters list and ways to use them:<br></h4>
                    <p>
                        <ol>
                    <li><b>Overdue:</b> Input the date in the newly appeared date field and click search</li>
                    <li><b>Available:</b>   Input from date and to date in newly appeared date fields and click search</li>
                    <li><b>Due:</b> </li>
                    <li><b>Currently out vehicles: </b>Select filter and press search</li>
                    <li><b>Make, model, color: </b> Input the appropriate data as per the filter in the search bar and click search</li>
                    <li><b>Greater than, Lesser than:</b> This refers to the make year of the vehicle,<br> enter desired value (year of format 'yyyy') and click search</li>
                </ol>
                    </p>
                    <p>The displayed list of vehicle can be be sorted by clicking
                        on one of the table headers.</p>
                    <p>The Vehicles can be sorted based on year by entering only
                        the year.</p>
                    <p>The View Detail button will present all the transactions
                        associated to the vehicle.</p>

                    <H2>Managing Vehicles</H2>
                    <p>From the 'Vehicle Register'</p>
                    <p>To add a vehicle, simply click on the Add vehicle button
                        and fill the form.</p>
                    <p>To edit a vehicle details, simply click on the Edit button
                        button and update the form.</p>
                    <p>To delete a vehicle simply click on the Delete button and
                        confirm.</p>
                </div>

            </div>
        </div>

    </c:when>



    <c:when test="${inputDisplay == 1}">
        <div class="container">
            <div class="row">
                <div class="col">

                    <br /> <br />
                    <H1>Clerk Functionality</H1>
                    <br />

                    <H2>Client Management</H2>
                    <p>From the client Register:</p>
                    <p>Navigate the registered clients</p>
                    <p>The clerk can remove clients by clicking on the delete
                        button.</p>
                    <p>The clerk can Add clients by clicking on the Add Client
                        button.</p>
                    <p>Update the client details by clicking on the edit button.</p>
                    <br />
<%--                    <blockquote class="blockquote">--%>
                    <fieldset class="border" >
                        <div class="well well-lg">
                    <H3 style="color: crimson">Client Record concurrency: Optimistic Offline Lock</H3>
                        <p>Steps to check Concurrency:</p><br>
                        <p>1. Login as clerk (username: clerk, password:clerk)  in <b>chrome browser </b></p>
                        <p>2. Login as clerk1 (username: clerk1, password:clerk1) in <b>another browser (ex: Firefox or Edge or internet explorer)</b></p>
                        <p>3. Go to client register page</p>
                        <p>4. Click on edit on the same client record in both clerk and clerk1 session
                            Excepted action: they will be allowed access to the record</p>
                        <p>5. Modify the record in both the sessions</p>
                        <p>6. Click save on clerk session. The record will be saved</p>
                        <p>7. Click save on clerk1 session. The record will not be saved.
                            <div class="warning">Error message saying the record has been updated please go back and retrieve the new version of the record will be displayed.</div> </p>
<%--                    </blockquote>--%>
                        </div>
                    </fieldset>

                    <H2>Vehicle Catalog</H2>
                    <H3>Navigating the vehicle catalog</H3>
                    <p>The displayed list of vehicle can be search by entering the
                        a criteria and clicking on search.</p>
                    <p>The displayed list of vehicle can be be sorted by clicking
                        on one of the table headers.</p>
                    <p>The Vehicles can be sorted based on year by entering only
                        the year.</p>
                    <p>The View Detail button will present all the transactions
                        associated to the vehicle.</p>
                    <H3>Managing rentals</H3>
                    <p>To add a transaction, simply click on the Add Transaction
                        button and fill the form.</p>
                    <p>To return a transaction simply click on the Return button
                        on the transaction displayed in the detailed view.</p>
                    <p>To cancel a transaction simply click on the Cancel button
                        on the transaction displayed in the detailed view.</p>
                    <p>To edit a transaction simply cancel the transaction and add
                        recreate it.</p>
                </div>

            </div>
        </div>
    </c:when>
</c:choose>
<br />
<br />



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
</body>
</html>