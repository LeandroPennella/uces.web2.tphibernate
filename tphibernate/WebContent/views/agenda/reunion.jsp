<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="master_header.jsp"></jsp:include>
<title><fmt:message key="reunion.tituloAgregar"/></title>
</head>
<body>
	<jsp:include page="master_menu.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span3" id="sidebar">
			</div>
			<!--/span-->
			<div class="span9" id="content">
				<!-- morris stacked chart -->
				<div class="row-fluid">
					<!-- block -->
					<div class="block">
						<div class="navbar navbar-inner block-header">
							<div class="muted pull-left"><fmt:message key='reunion.titulo'/></div>
						</div>
						<div class="block-content collapse in">
							<div class="span12">
								<c:url value="/agenda/agregarReunion.do" var="x"></c:url>
								<form:form method="POST" commandName="reunionForm" action="${x}" cssClass="form-horizontal" >
								
									<fieldset>
										<legend><fmt:message key='reunion.tituloAgregar'/></legend>
										<form:hidden path="idEvento"/>	
										<div class="control-group">
											
											<fmt:message key='evento.label.titulo' var="titulo" />
											<label class="control-label" for="titulo"><fmt:message key="evento.label.titulo" /></label>
											<div class="controls">
												<form:input id="titulo" path="titulo" cssClass="input-xxlarge focused"  placeholder="${titulo}" autofocus="true"  />
												<form:errors path="titulo" cssStyle="color: red" />
											</div>

										</div>
										<div class="control-group">
											<fmt:message key='evento.label.fecha' var="fecha" />
										    <span class="control-label"><fmt:message key="evento.label.fecha" /></span>
										    <div class="controls form-inline">

												<form:input id="fecha" path="fecha" cssClass="input-small "  placeholder="${fecha}"/>
												
									            <fmt:message key='evento.label.horaInicio' var="horaInicio" />
												<label for="horaInicio"><fmt:message key="evento.label.horaInicio" /></label>
												<form:input id="horaInicio" path="horaInicio" cssClass="input-small "  placeholder="${horaInicio}"/>
												
												<fmt:message key='evento.label.horaFin' var="horaFin" />
												<label for="horaFin"><fmt:message key="evento.label.horaFin" /></label>
												<form:input id="horaFin" path="horaFin" cssClass="input-small "  placeholder="${horaInicio}"/>
												
									    	</div>
									  	</div>
									  	<div >
									  		<form:errors path="fecha" cssStyle="color: red" />
									  		<form:errors path="horaInicio" cssStyle="color: red" />
											<form:errors path="horaFin" cssStyle="color: red" />
									  	</div>
									  	
										<div class="control-group">
											<fmt:message key='reunion.label.temario' var="temario" />
											<label class="control-label" for="temario"><fmt:message key="reunion.label.temario" /></label>
											<div class="controls">
												<form:input id="temario" path="temario" cssClass="input-xxlarge "  placeholder="${temario}" />
												<form:errors path="temario" cssStyle="color: red" />
											</div>
										</div>
										
										<div class="control-group">
											<fmt:message key='reunion.label.sala' var="sala" />
											<label class="control-label" for="sala"><fmt:message key="reunion.label.sala" /></label>
											<div class="controls">
												<%-- <form:input id="sala" path="sala" cssClass="input-xxlarge "  placeholder="${sala}" />--%>
												<%-- <form:select path="sala" items="salas"></form:select>--%>
												<td><form:select path="idSala" multiple="false" items="${reunionForm.salas}" itemLabel="descripcion" itemValue="id"/></td>
												<form:errors path="idSala" cssStyle="color: red" />
											</div>
										</div>
										
										<c:if test="${reunionForm.idEvento!=null}">
										<c:if test='${reunionForm.estado=="reunionNoConfirmado"}'>
										<div class="control-group">
											<label class="control-label" ><fmt:message key="reunion.label.estado" /></label> 
											<div class="controls">
											<form:radiobutton path="idEstado" value="1"/>Aceptado 
											<form:radiobutton path="idEstado" value="2"/>Cancelado
											</div>
										</div>
										</c:if>


										<div class="control-group">
											<label class="control-label" ><fmt:message key="reunion.label.invitados" /></label> 
											<div class="controls">
											
												<c:forEach var="invitacion" items="${reunionForm.invitaciones}">
													${invitacion.usuario.nombre} - ${invitacion.aceptado}<br/>
												</c:forEach>
											
											</div>
										</div>
											
										</c:if>
										
										<div class="control-group">
											<fmt:message key='reunion.label.participantes' var="participantes" />
											<label class="control-label" for="invitados"><fmt:message key="reunion.label.participantes" /></label>
											<div class="controls">
												<form:select path="idsInvitados">
										 			<option value="0">deseleccionar</option>
												    <c:forEach items="${reunionForm.usuariosInvitados}" var="usIn" >
												    
												        <c:choose>
												        
												            <c:when test="${usIn.agregado}">
												                <option value="${usIn.usuario.id}"   selected="true">${usIn.usuario.nombre}</option>
												            </c:when>
												            <c:otherwise>
												                <option value="${usIn.usuario.id}"  >${usIn.usuario.nombre}</option>
												            </c:otherwise>
												        </c:choose> 
												    </c:forEach>
												</form:select>
												<td><form:errors path="idsInvitados" /></td>
											</div>
										</div>
										
 										<div class="form-actions">
											<button type="submit" class="btn btn-primary"><fmt:message key="evento.label.guardar" /></button>
											<button type="reset" class="btn"><fmt:message key="evento.label.cancelar" /></button>
										</div>
									</fieldset>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>