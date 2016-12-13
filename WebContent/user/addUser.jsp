<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.model.TipoUsuario"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="../template/head.jsp"></jsp:include>

<div class="panel panel-primary panel-addUser">

	<div class="panel-heading text-center">Cadastro de Usu�rio</div>


	<div class="panel-body">

		<jsp:include page="/template/msg.jsp"></jsp:include>


			<form method="post" action="main?acao=addUser">

				<div class="form-group">
					<label class="form-label ages">Matr�cula: <span class="red">*</span></label> 
					<input class="form-control" id="matricula" name="matricula"	value="${param.matricula}" type="text" maxlength="9" required> 
					<label class="form-label ages">Nome: <span class="red">*</span></label> 
					<input class="form-control" id="nome" name="nome" value="${param.nome}" type="text" maxlength="120" required>
					<div class="row">
						<div class="col-sm-6">
						    <label class="form-label ages">Usu�rio: <span class="red">*</span></label> 
							<input class="form-control" id="usuario" name="usuario" value="${param.usuario}" type="text" maxlength="120" required>
						</div>
						<div class="col-sm-6">
							<label class="form-label ages">Senha: <span class="red">*</span></label> 
							<input class="form-control" id="senha" name="senha" value="${param.senha}" type="text" maxlength="8" required> 
						</div>
					</div>
					<label class="form-label ages">E-Mail: <span class="red">*</span></label> 
					<input class="form-control" id="email" name="email" value="${param.email}" type="text" maxlength="120" required>

					<div class="row">
						<div class="col-sm-6">
							<label class="form-label ages">Perfil de Acesso: <span class="red">*</span></label> <select class="form-control" id="perfilAcesso" name="perfilAcesso"
								required>
								<option value="NAVEGADOR" <%="NAVEGADOR".equals(request.getParameter("perfilAcesso")) ? "selected" : ""%>>Navegador</option>
								<option value="ADMINISTRADOR" <%="ADMINISTRADOR".equals(request.getParameter("perfilAcesso")) ? "selected" : ""%>>Administrador</option>
							</select>
						</div>
	
						<div class="col-sm-6">
							<label class="form-label ages">Status: <span class="red">*</span></label> <select class="form-control" id="statusUsuario" name="statusUsuario" required>
								<option value="ATIVO" <%="ATIVO".equals(request.getParameter("statusUsuario")) ? "selected" : ""%>>Ativo</option>
								<option value="INATIVO" <%="INATIVO".equals(request.getParameter("statusUsuario")) ? "selected" : ""%>>Inativo</option>
							</select>
						</div>
					</div>
					<label class="form-label ages">Tipo de Usu�rio: <span class="red">*</span></label> <select class="form-control" id="tipoUsuario" name="tipoUsuario" required>
						<%
							List<TipoUsuario> listaTipoUsuarios = (List<TipoUsuario>) request.getAttribute("tipoUsuarios");
							for (TipoUsuario tipoUsuario : listaTipoUsuarios) {
						%>
						<option value="<%=tipoUsuario.getIdTipoUsuario()%>"><%=tipoUsuario.getNome()%></option>
						<%
							}
						%>
					</select>
					<div class="row">
						<div class="col-sm-6">
						    <label class="form-label ages">Usu�rio GitLab: <span class="red">*</span></label> 
							<input class="form-control" id="usuarioGit" name="usuario"Git value="${param.usuarioGit}" type="text" maxlength="120" required>
						</div>
						<div class="col-sm-6">
							<input class="btn btn-primary addUser pull-right" type="submit" value="Cria GitLab">
						</div>
					</div>
				</div>
				<p>
					Campos que cont�m <span class="red">*</span> s�o obrigat�rios
				</p>
				<div class="text-center">
					<input class="btn btn-warning limparUser pull-left" type="reset" value="Limpar"> 
					<input class="btn btn-primary addUser pull-right" type="submit" value="Cadastrar">
				</div>
			</form>
	</div>
</div>


<jsp:include page="/template/foot.jsp"></jsp:include>
