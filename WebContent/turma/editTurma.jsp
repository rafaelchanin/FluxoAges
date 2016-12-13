<%@page import="br.ages.crud.model.Stakeholder"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.LinkedHashSet"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.model.Projeto"%>
<%@page import="br.ages.crud.util.Util" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%Projeto projeto = (Projeto) request.getAttribute("projeto"); %>


<jsp:include page="../template/head.jsp"></jsp:include>
<script src="../js/cadastro-projeto.js"></script>

<div class="panel panel-primary panel-addUser">

	<div class="panel-heading text-center">Editar Projeto</div>

	<div class="panel-body">

		<jsp:include page="/template/msg.jsp"></jsp:include>

		<div class="table-responsive">

			<form method="post" action="main?acao=editaProjeto"> <!-- enctype="multipart/form-data" > -->

				<input class="form-control" type="hidden" id="idProjeto" name="idProjeto" value="<%=projeto.getIdProjeto()%>">

				<div class="form-group">
					<label class="form-label ages">Nome: <span class="red">*</span></label> 
					<input class="form-control" id="nomeProjeto" name="nomeProjeto" value="<%=projeto.getNomeProjeto()%>" type="text"	maxlength="120" required>
				</div>

				<div class="form-group">
					<label class="form-label ages">Status do Projeto: <span class="red">*</span></label> 
					<select class="form-control" id="statusProjeto" name="statusProjeto"
						required>
						<option value="ATIVO" <%="ATIVO".equals(projeto.getStatusProjeto()) ? "selected" : ""%>>Ativo</option>
						<option value="INATIVO" <%="INATIVO".equals(projeto.getStatusProjeto()) ? "selected" : ""%>>Inativo</option>
						<option value="CONCLUIDO" <%="CONCLUIDO".equals(projeto.getStatusProjeto()) ? "selected" : ""%>>Conclu�do</option>
					</select>
				</div>
				
				<div class="form-group integrante ">
										
					<!-- STAKEHOLDER -->
					<!-- segue o link do antigo componente utilizado na cria��o da sele��o de stakeholders -->
					<!-- http://davidstutz.github.io/bootstrap-multiselect/#faq -->					
					
					<div class="col-md-12">
						<select multiple="multiple" size="10" name="listaStakeholders" id="listaStakeholders" class="listaStakeholders" >
						<%
							List<Stakeholder> listaStakeholders = (List<Stakeholder>) request.getAttribute("listaStakeholders");
							List<Stakeholder> listaStakeholdersProjeto = (List<Stakeholder>) projeto.getStakeholders();
						
							for (Stakeholder stakeholder : listaStakeholders) {
						%>
							<option value="<%=stakeholder.getIdStakeholder()%>"><%=stakeholder.getNomeStakeholder()%></option>
						<%
							}
						%>
						
						<%
							for (Stakeholder stakeholder : listaStakeholdersProjeto) {
						%>
							<option value="<%=stakeholder.getIdStakeholder()%>" selected="selected"><%=stakeholder.getNomeStakeholder()%></option>
						<%
							}
						%>
						
						
						</select>
					</div>
				</div>
				
				<div class="form-group integrante">					
					<!-- USUARIOS -->
					<!-- http://www.virtuosoft.eu/code/bootstrap-duallistbox/ -->
					<div class="col-md-12">
						<select multiple="multiple" size="10" name="listaUsuarios" id="listaUsuarios" class="listaUsuarios" >
						<%
							
							List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
							List<Usuario> listaUsuariosProjeto = (List<Usuario>) projeto.getUsuarios();
							
							
							
							for (Usuario usuario : listaUsuarios) {
						%>
							<option value="<%=usuario.getIdUsuario()%>"><%=usuario.getNome()%></option>	
						<%
							}
						%>
						
						<%	
							for (Usuario usuarioProjeto : listaUsuariosProjeto) {
						%>
							<option value="<%=usuarioProjeto.getIdUsuario()%>" selected="selected"><%=usuarioProjeto.getNome()%></option>
						<%
							}
						%>
						  
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="form-label ages">Workspace: <span class="red">*</span></label> 
					<input class="form-control" id="workspace" name="workspace" value="<%=projeto.getWorkspace()%>" type="text"
						maxlength="120" required>
				</div>

				<div class="form-group">
					<label class="form-label ages">Data de In�cio: <span class="red">*</span></label> <input class="form-control" id="dataInicio" name="dataInicio" value="<%=Util.dateToString(projeto.getDataInicio()) %>"
						type="text" maxlength="10" placeholder="DD/MM/AAAA" required>
				</div>

				<div class="form-group">
					<label class="form-label ages">Data de Fim Previsto: <span class="red">*</span></label> <input class="form-control" id="dataFimPrevisto" name="dataFimPrevisto"
						value="<%=Util.dateToString(projeto.getDataFimPrevisto())%>" type="text" maxlength="10" placeholder="DD/MM/AAAA" required>
				</div>

				<div class="form-group">
					<label class="form-label ages">Data de Fim:</label> <input class="form-control" id="dataFim" name="dataFim" 
						value="<%=projeto.getDataFim() == null ? "" : Util.dateToString(projeto.getDataFim()) %>" type="text" maxlength="10" placeholder="DD/MM/AAAA">
				</div>

				<!-- <div class="form-group">
					<label class="form-label ages">Arquivo: <span class="red">*</span></label> <input class="form-control" id="arquivo" name="arquivo" value=""
						type="file" >
				</div> -->

				<hr>

				<p>
					Campos que cont�m <span class="red">*</span> s�o obrigat�rios
				


				<div class="text-center">
					<input class="btn btn-warning limparUser pull-left" type="reset" value="Limpar"> 
					<input class="btn btn-primary addUser pull-right" type="submit" value="Salvar">
				</div>
			</form>
		</div>
	</div>
</div>


<jsp:include page="/template/foot.jsp"></jsp:include>

<!-- USUARIOS -->
<!-- http://www.virtuosoft.eu/code/bootstrap-duallistbox/ -->
<script>
	var demo2 = $('.listaUsuarios').bootstrapDualListbox({
		nonSelectedListLabel : 'Usu�rios',
		selectedListLabel : 'Usu�rios do Projeto',
		preserveSelectionOnMove : 'moved',
		moveOnSelect : false,
		nonSelectedFilter : '',
		filterTextClear : 'Mostrar Todos',
		infoTextEmpty : 'Sem usuarios '
	});
</script>

<script>
	var demo2 = $('.listaStakeholders').bootstrapDualListbox({
		nonSelectedListLabel : 'Stakeholders',
		selectedListLabel : 'Stakeholders do Projeto',
		preserveSelectionOnMove : 'moved',
		moveOnSelect : false,
		nonSelectedFilter : '',
		filterTextClear : 'Mostrar Todos',
		infoTextEmpty : 'Sem stakeholders ',
	});
</script>


<script>
	//P�e cor laranja nos titulos
	$('div[class*="box"]').find('label').css('color', '#F89406');
	
	//D� espa�amento no grupo usu�rios
	$('div[class*="bootstrap-duallistbox-container"]').eq(1).addClass('margin-top');
	//D� espa�amento no Workspace
	$('label:contains("Workspace")').addClass('margin-top');
	
	//Remove aparencia de input de texto do input de arquivo
	$('label:contains("Arquivo")').siblings('input').removeClass('form-control');
</script>