<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Inicio</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'cards'}" url="/cards/menu"
					title="cards">
					<span class="glyphicon glyphicon-tower" aria-hidden="true"></span>
					<span>Cartas y mapas</span>
				</petclinic:menuItem>

				<sec:authorize url="/users">
				<petclinic:menuItem active="${name eq 'users'}" url="/users/list"
					title="users">
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					<span>Usuarios</span>
				</petclinic:menuItem>
			    </sec:authorize>

				<petclinic:menuItem active="${name eq 'games'}" url="/games?name=&state=" title="games">
				<span class="glyphicon glyphicon-play" aria-hidden="true"></span>
				<span>Partidas</span>
				</petclinic:menuItem>
				
				<sec:authorize url="/actions">
				<petclinic:menuItem active="${name eq 'actions'}" url="/actions"
					title="actions">
					<span class="glyphicon glyphicon-flash" aria-hidden="true"></span>
					<span>Acciones</span>
				</petclinic:menuItem>
			</sec:authorize>
	
			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar Sesion</a></li>
					<li><a href="<c:url value="/user/new" />">Registrarte</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Cerrar sesion</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>						
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="<c:url value="/myprofile" />"class="btn btn-primary btn-block">Mi perfil</a>
												<a href="<c:url value="/changepassword" />" class="btn btn-danger btn-block">Cambiar clave</a>
												<a href="<c:url value="/deleteprofile" />" class="btn btn-danger btn-block"> Borrar usuario</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
