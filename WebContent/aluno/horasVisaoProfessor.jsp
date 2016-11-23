<%@page import="br.ages.crud.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.model.ResumoPonto"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.util.TimeConverter"%>
<%@page import="java.util.List"%>
<jsp:include page="../template/head.jsp"></jsp:include>


<div class="panel panel-primary">

	<div class="panel-heading text-center">Resumo de Horas</div>

	<jsp:include page="/template/msg.jsp"></jsp:include>

	<div class="panel-body">
		<div class="table-responsive">
			<table id="listaAlunos" class="table table-responsive table-striped table-hover">
				<thead>
					<tr>
						<th style="text-align: left;">Nome</th>
						<th style="text-align: left;">Realizadas at� o momento</th>
						<th style="text-align: left;">Devendo para aprova��o com 75%</th>
						<th style="text-align: left;">Devendo para aprova��o com 100%</th>
					</tr>
				</thead>

				<tbody>
					<%
						List<ResumoPonto> listaResumoPonto = (List<ResumoPonto>) request.getAttribute("listaPontos");
						int id = 0;
						for (ResumoPonto usuario : listaResumoPonto) {
							
							int horasPontoValidas = usuario.getHoraTotalDiaValido();
							//3600 s�o o "mock" das 60h de presen�a em aula que o aluno deve ter
							//5400 s�o o total de horas que o aluno deveria ter (presen�a + ponto) para ser aprovado
							//7200 s�o o total de horas que o aluno deveria ter (presen�a + ponto) para ter 100% de aprova��o
							String horasAteOMomento = TimeConverter.ConvertMinuteToHours(3600 + horasPontoValidas);
							String horasDevendo75 = TimeConverter.ConvertMinuteToHours(5400 - (3600 + horasPontoValidas));
							String horasDevendo100 = TimeConverter.ConvertMinuteToHours(7200 - (3600 + horasPontoValidas));
					%>

					<tr class="coluna-sh" data-toggle="collapse" data-target="#extraInfo<%=id%>">
						<td align="left"><%=usuario.getNomeAluno()%></td>
						<td align="left"><%=horasAteOMomento%></td>
						<td align="left"><%=horasDevendo75%></td>
						<td align="left"><%=horasDevendo100%></td>
					</tr>
					<tr class="coluna-sh collapse" id="extraInfo<%=id%>">
						<td colspan=12 align="center">
							<table id="extraInfoTable" class="table">
								<thead>
									<tr>
										<th style="text-align: center;">Tipo de horas</th>
										<th style="text-align: center;">Realizadas at� o momento</th>
										<th style="text-align: center;">Previstas at� o momento</th>
										<th style="text-align: center;">Diferen�a<th></th>
										<th style="text-align: center;">Previstas at� o fim do semestre</th>
										<th style="text-align: center;">Diferen�a<th></th>
									</tr>
								</thead>

								<tbody>
									<tr class="coluna-sh">
										<td align="center">Extraclasse</td>
										<td align="center">realizadasAteOMomento</td>
										<td align="center">previstasAteOMomento</td>
										<td align="center">previstasAteOMomento - realizadasAteOMomento</td>
										<td align="center">previstasAteOFimDoSemestre</td>
										<td align="center">previstasAteOFimDoSemestre - realizadasAteOMomento</td>
									</tr>
									<tr class="coluna-sh">
										<td align="center">Em aula</td>
										<td align="center">realizadasAteOMomento</td>
										<td align="center">previstasAteOMomento</td>
										<td align="center">previstasAteOMomento - realizadasAteOMomento</td>
										<td align="center">previstasAteOFimDoSemestre</td>
										<td align="center">previstasAteOFimDoSemestre - realizadasAteOMomento</td>
									</tr>
									<tr class="coluna-sh">
										<td align="center">Total</td>
										<td align="center">realizadasAteOMomento</td>
										<td align="center">previstasAteOMomento</td>
										<td align="center">previstasAteOMomento - realizadasAteOMomento</td>
										<td align="center">previstasAteOFimDoSemestre</td>
										<td align="center">previstasAteOFimDoSemestre - realizadasAteOMomento</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<%
						id++;
						}
					%>
				</tbody>

			</table>

		</div>

	</div>

</div>

<jsp:include page="../template/foot.jsp"></jsp:include>

<script>
	function listar() {
			var id =  document.getElementById("idAluno").value;
			
			 document.forms[0].action= 'main?acao=listaPontoHora&id_usuario=' + id;
			 document.forms[0].submit();
			 winconsole.log(id);
		};
	
	$(document).ready(function(){
	    $('#listaAlunos').dataTable({
	    	"language": {
	            "lengthMenu": "Mostrando _MENU_ registros por p�gina",
	            "zeroRecords": "Sem registros - sorry",
	            "info": "Mostrando _PAGE_ de _PAGES_ p�ginas",
	            "infoEmpty": "Nenhum registros encontrados!",
	            "infoFiltered": "(Filtrado _MAX_ do total deregistros)",
	            "search": "Pesquisar",
	            "paginate": {
	                "first":      "Primeiro",
	                "last":       "�ltimo",
	                "next":       "Pr�ximo",
	                "previous":   "Anterior"
	            },
	        }
	    });
	});
</script>