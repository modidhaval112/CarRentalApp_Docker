<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        body {
            background: #dedede;
        }
        .page-wrap {
            min-height: 100vh;
        }
    </style>
</head>
<body>
<!------ Include the above in your HEAD tag ---------->

<div class="page-wrap d-flex flex-row align-items-center">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12 text-center">
                <span class="display-1 d-block">403</span>
                <div class="mb-4 lead">Sorry, you are not authorized to view this page.</div>
                <a href="<c:url value="/" /> " class="btn btn-link">Back to Home</a>
            </div>
        </div>
    </div>
</div>

</body>