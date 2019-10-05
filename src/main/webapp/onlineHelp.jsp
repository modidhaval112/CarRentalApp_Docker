<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Online Help</title>
</head>
<body>
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
                                     href="vehicle-catalog">Vehicle Catalog<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item "><a class="nav-link"
                                     href="client-register">Client Register<span class="sr-only"></span></a>
            </li>
            <li class="nav-item active"><a class="nav-link" href="online-help">?<span class="sr-only"></span></a>
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
    <div class="row">
        <div class="col">

            <br/>
            <br/>
            <H1>Clerk Functionality</H1>
            <br/>

            <H2>Client Management</H2>
            <p> From the client Register:</p>
            <ul>
                <li>
                    <p>Navigate the registered clients</p>
                </li>
                <li>
                    <p>The clerk can remove clients by clicking on the delete button.</p>
                </li>
                <li>
                    <p>The clerk can Add clients by clicking on the Add Client button.</p>
                </li>
                <li>
                    <p>Update the client details by clicking on the edit button.</p>
                </li>
            </ul>
            <br/>

            <H2>Vehicle Catalog</H2>
            <H3>Navigating the vehicle catalog</H3>
            <p>The displayed list of vehicle can be search by entering the a criteria and clicking on search.</p>
            <p>The displayed list of vehicle can be be sorted by clicking on one of the table headers.</p>
            <p>The View Detail button will present all the transactions associated to the vehicle.</p>
            <H3>Managing rentals</H3>
            <p>To add a transaction, simply click on the Add Transaction button and fill the form.</p>
            <p>To return a transaction simply click on the Return button on the transaction displayed in the detailed view.</p>
            <p>To cancel a transaction simply click on the Cancel button on the transaction displayed in the detailed view.</p>
            <p>To edit a transaction simply cancel the transaction and add recreate it.</p>
        </div>

    </div>
</div>
<br/>
<br/>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>