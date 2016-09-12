<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.model.Ponto"%>
<%@page import="br.ages.crud.util.Util"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="java.util.List"%>
<jsp:include page="../template/head.jsp"></jsp:include>

<!-- MODAL / POPUP -->
<jsp:include page="../template/modalAluno.jsp"></jsp:include>
<div class="panel panel-primary">

	<div class="panel-heading text-center">Hora Ponto Alunos</div>

	<jsp:include page="/template/msg.jsp"></jsp:include>

	<div class="panel-body">
		<form id="formListAluno" method="post">
			<div class="table-responsive">
				<table id="listaAlunos" class="table table-responsive table-striped table-hover table-condensed">
					<thead>
						<tr>
							<th style="text-align: center;">Nome Aluno</th>
							<th style="text-align: center;">Data Entrada</th>
							<th style="text-align: center;">Data Sa�da</th>
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
								String horasDia = ponto.getHoraTotalDia()/60 + ":" + ponto.getHoraTotalDia()%60;
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
	function listar() {
		var id = document.getElementById("idAluno").value;
		document.forms[0].action = 'main?acao=listaPontoHora&id_usuario=' + id;
		document.forms[0].submit();
		winconsole.log(id);
	};
</script>
<script>
	$(document).ready(function() {
		$('#listaAlunos').dataTable({
			"language" : {
				"lengthMenu" : "Mostrando _MENU_ registros por p�gina",
				"zeroRecords" : "Sem registros - sorry",
				"info" : "Mostrando _PAGE_ de _PAGES_ p�ginas",
				"infoEmpty" : "Nenhum registros encontrados!",
				"infoFiltered" : "(Filtrado _MAX_ do total deregistros)",
				"search" : "Pesquisar",
				"paginate" : {
					"first" : "Primeiro",
					"last" : "�ltimo",
					"next" : "Pr�ximo",
					"previous" : "Anterior"
				},
			}
		});
	});
</script>