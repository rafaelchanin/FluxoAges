<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="../template/head.jsp"></jsp:include>
	
	<div class="panel panel-primary panel-addPonto">

		<div class="panel-heading text-center">Cadastro Ponto</div>
		<div class="panel-body">
		
		<jsp:include page="/template/msg.jsp"></jsp:include>

			<!-- <div class="table-responsive"> -->
				<form method="post" action="main?acao=adicionaPonto" >
					<div class="form-group">
						<div class='' id='nomeAluno'>
							<label  for="sel1" class="form-label ages">Aluno:<span class="red">*</span></label> 
							<select class="form-control" id="idAluno" name="idAluno" >
							 	<%
									List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("usuarios");
									for (Usuario u : listaUsuarios) {
							  	 %>
								<option value="<%=u.getIdUsuario()%>" <%=Integer.toString(u.getIdUsuario()).equals(request.getParameter("idAluno")) ? "selected" : ""%>><%=u.getNome()%></option>
								<%
									}
								%>
					        </select>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-sm-8">
							<label class="form-label ages">Entrada:<span class="red">*</span></label> 
								<div class='input-group date' id='dataEntrada'>
									<input type='text' class="form-control" id='dtEntradaRegistro' name="dtEntradaRegistro" value="${param.dtEntradaRegistro}"/>
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
							<div class="col-sm-4">
								<label class="form-label ages">Saída:<span class="red">*</span></label> 
								<div class='input-group date' id='dataSaida'>
									<input type='text' class="form-control" id="dtSaidaRegistro" name="dtSaidaRegistro" value="${param.dtSaidaRegistro}"/> 
									<span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>
					</div>					
					<hr>
                    
                    <p>Campos que contém <span class="red">*</span> são obrigatórios</p>
                    
                    
                    <div class="text-center">
			           	<input class="btn btn-warning limparUser pull-left" type="reset" value="Limpar">
			           	<input class="btn btn-primary addUser pull-right" type="submit" value="Salvar">
			        </div>
				</form>
			</div>
		</div>
	<!-- </div> -->


<!-- Initialize the plugin: -->

<script type="text/javascript">
	$(function() {
		var dateDefault = new Date();
		if (dateDefault.getDay() >= 2 && dateDefault.getDay() <= 6) {
			dateDefault.setTime(dateDefault.getTime() - 86400000);
		} else if (dateDefault.getDay() == 1) {
			dateDefault.setTime(dateDefault.getTime() - (86400000 * 3));
		} else if (dateDefault.getDay() == 0) {
			dateDefault.setTime(dateDefault.getTime() - (86400000 * 2));
		}
		dateDefault.setHours(0);
		dateDefault.setMinutes(0);
		$('#dataEntrada').datetimepicker({
			locale : 'pt-br',
			sideBySide : true,
			showTodayButton: true,
			defaultDate: dateDefault
		});

		$('#dataSaida').datetimepicker({
			useCurrent : false, //Important! See issue #1075
			locale : 'pt-br',
			sideBySide : true,
			format: 'HH:mm',
		});
		
		funcPreencheSaida();
		
		$("#dataEntrada").on("dp.change", function(e) {
			funcPreencheSaida();
		});

		$("#dataSaida").on("dp.change", function(e) {
			$('#dataEntrada').data("DateTimePicker").maxDate(e.date);
			/* alert(document.getElementById('dataEntrada').value); */
		});
	});
	
	function funcPreencheSaida() {
		var campoEntrada = document.getElementById('dtEntradaRegistro').value;
		var tempCampoEntrada = campoEntrada.split(" ");
		$('#dataSaida').data("DateTimePicker").minDate(tempCampoEntrada[1]);
		document.getElementById('dtSaidaRegistro').value = tempCampoEntrada[1];
	}
</script>
<jsp:include page="/template/foot.jsp"></jsp:include>