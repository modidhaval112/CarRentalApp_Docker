<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<nav
	class="navbar navbar-fixed-top navbar-light bg-light navbar-expand-lg">
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
		<a class="navbar-brand" href="#">Car Rental</a>

		<ul class="navbar-nav mr-auto mt-2 mt-lg-0">

			<c:set var="inputDisplay" value="${disableButton}" />
			<c:choose>
				<c:when test="${inputDisplay == 0}">
					<li class="nav-item active"><a class="nav-link"
						href="/trans-list">Transactions History<span class="sr-only"></span>
					</a></li>
				</c:when>
			</c:choose>

			<li class="nav-item active"><a class="nav-link"
				href="/vehicle-catalog">Vehicle Catalog<span class="sr-only"></span>
			</a></li>

			<c:set var="inputDisplay" value="${disableButton}" />
			<c:choose>
				<c:when test="${inputDisplay == 1}">
					<li class="nav-item active"><a class="nav-link"
						href="/client-register">Client Register<span class="sr-only"></span>
					</a></li>
				</c:when>
			</c:choose>





			<c:set var="inputDisplay" value="${disableButton}" />
			<c:choose>
				<c:when test="${inputDisplay == 0}">
					<li class="nav-item active"><a class="nav-link"
						href="/vehicle-register">Vehicle Register<span class="sr-only"></span></a>
					</li>
				</c:when>
			</c:choose>


			<li class="nav-item active"><a class="nav-link"
				href="/online-help">Online Help<span class="sr-only"></span>
			</a></li>
			<li class="nav-item active"><input type="text"
				class="form-control" id="auth" name="auth" value="${auth}"
				readonly="readonly"></li>

		</ul>

		<button
			class=" form-inline my-2 my-lg-0 btn btn-outline-success my-2 my-sm-0"
			id="logout" type="submit"
			onclick="document.forms['logoutForm'].submit()">Logout</button>

		<form id="logoutForm" method="POST" action="${contextPath}/logout">
		</form>

	</div>
</nav>