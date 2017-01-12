<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.model.Ponto"%>
<%@page import="br.ages.crud.util.Util"%>
<%@page import="br.ages.crud.util.TimeConverter"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="java.util.List"%>
<jsp:include page="../template/head.jsp"></jsp:include>

<!-- MODAL / POPUP -->
<jsp:include page="../template/modalAluno.jsp"></jsp:include>
<div class="panel panel-primary">

	<div class="panel-heading text-center">Lista Ponto</div>

	<jsp:include page="/template/msg.jsp"></jsp:include>

	<div class="panel-body">
		<form id="formListAluno" method="post">
			<div class="form-group row">
				<div class='col-sm-2' id='dtInicial'>
					<label for="sel1" class="form-label ages">Data Inicial:<span class="red">*</span></label> 
					<div class='input-group date' id='dataEntrada'>
						<input type='text' class="form-control" id='dtEntrada' name="dtEntrada" value="<%=request.getAttribute("dtEntrada")%>"/>
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
				<div class='col-sm-2' id='dtFinall'>
					<label for="sel1" class="form-label ages">Data Final:<span class="red">*</span></label> 
					<div class='input-group date' id='dataSaida'>
						<input type='text' class="form-control" id="dtSaida" name="dtSaida" value="<%=request.getAttribute("dtSaida")%>"/> 
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
				<div class='col-sm-2 rowMargin' id='dtFinall'>
					<button class="btn btn-primary addUser center-block" onclick="filtrarData();"> Buscar </button>
				</div>
			</div>
			<div class="table-responsive">
				<table id="listaAlunos" class="table table-responsive table-striped table-hover table-condensed">
					<thead>
						<tr>
							<th style="text-align: center;">Nome Aluno</th>
							<th style="text-align: center;">Data Entrada</th>
							<th style="text-align: center;">Data Saída</th>
							<th style="text-align: center;">Horas/dia</th>
							<th style="text-align: center;">Status</th>
							<th style="text-align: center;"></th>
							<th style="text-align: center;"></th>
						</tr>
					</thead>

					<tbody>
						<%
							ArrayList<Ponto> Pontos = (ArrayList<Ponto>) request.getAttribute("listaAlunos");
							for (Ponto ponto : Pontos) {
								String horasDia = TimeConverter.ConvertMinuteToHours(ponto.getHoraTotalDia());//ponto.getHoraTotalDia()/60 + ":" + ponto.getHoraTotalDia()%60;
						%>

						<tr class="coluna-sh">
							<td align="center"><%=ponto.getAluno().getNome()%></td>
							<td align="center"><%=Util.dateTimeToString(ponto.getDataEntrada())%></td>
							<td align="center"><%=Util.dateTimeToString(ponto.getDataSaida())%></td>
							<td align="center"><%=horasDia%></td>
							<td align="center"><%=ponto.getStatus().name()%></td>
							<td align="center">
								<form action="" method="post">
									<a href="" data-toggle="modal" data-id="<%=ponto.getIdPonto()%>" data-usuario="<%=ponto.getAluno().getNome()%>" data-target="#modalEditar"
										title="Editar"> <i class="glyphicon glyphicon-pencil"></i></a>
								</form>
							</td>

							<td align="center">
								<form action="" method="post">
									<a href="" data-toggle="modal" data-id="<%=ponto.getIdPonto()%>" data-usuario="<%=ponto.getAluno().getNome()%>" data-target="#modalExcluir"
										title="Deletar"> <i class="glyphicon glyphicon-trash"></i></a>
								</form>
							</td>
						</tr>

						<%
							}
						%>
					</tbody>

				</table>

			</div>

		</form>
	</div>

</div>
<jsp:include page="../template/foot.jsp"></jsp:include>
<script>
	function filtrarData() {
		/* var entrada = document.getElementById("dtEntrada").value;
		var saida = document.getElementById("dtSaida").value; */
		
		 form = document.getElementById("formListAluno");
		form.action= "main?acao=listaAluno";
		form.forms.submit();
		
	};
</script>
<script>
	$(document).ready(function() {
		$('#listaAlunos').dataTable({
			"language" : {
				"lengthMenu" : "Mostrando _MENU_ registros por página",
				"zeroRecords" : "Sem registros - sorry",
				"info" : "Mostrando _PAGE_ de _PAGES_ páginas",
				"infoEmpty" : "Nenhum registros encontrados!",
				"infoFiltered" : "(Filtrado _MAX_ do total deregistros)",
				"search" : "Pesquisar",
				"paginate" : {
					"first" : "Primeiro",
					"last" : "Último",
					"next" : "Próximo",
					"previous" : "Anterior"
				},
			}
		});
		
		$('#dataEntrada').datetimepicker({
			locale : 'pt-br',
			format : "DD/MM/YYYY",
			sideBySide : true
		});

		$('#dataSaida').datetimepicker({
			useCurrent : false, 
			locale : 'pt-br',
			format : "DD/MM/YYYY",
			sideBySide : true,
			showTodayButton: true
		});

		$("#dataEntrada").on("dp.change", function(e) {
			$('#dataSaida').data("DateTimePicker").minDate(e.date);
			/* alert(document.getElementById('dataSaida').value); */
		});

		$("#dataSaida").on("dp.change", function(e) {
			$('#dataEntrada').data("DateTimePicker").maxDate(e.date);
			/* alert(document.getElementById('dataEntrada').value); */
		});
	});
</script>