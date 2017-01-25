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
						<th style="text-align: right;">Realizadas at� o momento</th>
						<th style="text-align: right;">Devendo para aprova��o com 75% de presen�a</th>
						<th style="text-align: right;">Devendo para aprova��o com 100% de presen�a</th>
					</tr>
				</thead>

				<tbody>
					<%
						List<ResumoPonto> listaResumoPonto = (List<ResumoPonto>) request.getAttribute("listaPontos");
						int id = 0;
						for (ResumoPonto usuario : listaResumoPonto) {
							//3600 s�o o "mock" das 60h de presen�a em aula que o aluno deve ter
							
							//horas ponto previstas at� agora
							//METODO AINDA PRECISA SER FEITO! por enquanto, ser� mockado 60 horas
							int hpPrevistasAteAgora = 3600; //mock
							//horas aula previstas at� agora
							//METODO AINDA PRECISA SER FEITO! por enquanto, ser� mockado 60 horas
							int haPrevistasAteAgora = 3600; //mock
							//horas ponto realizadas at� agora
							int hpAteAgora = usuario.getHoraTotalDiaValido();
							//horas aula realizadas at� agora
							//METODO AINDA PRECISA SER FEITO! por enquanto, ser� mockado 60 horas
							int haAteAgora = 3600; //mock
							//horas ponto previstas at� o fim do semestre (fixo 60)
							int hpPrevistasSemestre = 3600;
							//horas aula previstas at� o fim do semestre (fixo 60)
							int haPrevistasSemestre = 3600;
							
							//5400 s�o o total de horas que o aluno deveria ter (presen�a + ponto) para ser aprovado
							//7200 s�o o total de horas que o aluno deveria ter (presen�a + ponto) para ter 100% de aprova��o
							int horasDevendo75 = 5400 - (3600 + hpAteAgora);
							int horasDevendo100 = 7200 - (3600 + hpAteAgora);
							
					%>

					<tr class="coluna-sh" data-toggle="collapse" data-target="#extraInfo<%=id%>">
						<td align="left"><%=usuario.getNomeAluno()%></td>
						<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpAteAgora + haAteAgora)%></td>
						<td align="right"><%=TimeConverter.ConvertMinuteToHours(horasDevendo75)%></td>
						<td align="right"><%=TimeConverter.ConvertMinuteToHours(horasDevendo100)%></td>
					</tr>
					<tr class="coluna-sh collapse" id="extraInfo<%=id%>">
						<td colspan=12 align="center">
							<table id="extraInfoTable" class="table">
								<thead>
									<tr>
										<th style="text-align: left;">Tipo de horas</th>
										<th style="text-align: right;">Realizadas at� o momento</th>
										<th style="text-align: right;">Previstas at� o momento</th>
										<th style="text-align: right;">Diferen�a</th>
										<th style="text-align: right;">Previstas at� o fim do semestre</th>
										<th style="text-align: right;">Diferen�a</th>
									</tr>
								</thead>

								<tbody>
									<tr class="coluna-sh">
										<td align="left">Extraclasse</td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpPrevistasAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpPrevistasAteAgora - hpAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpPrevistasSemestre)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpPrevistasSemestre - hpAteAgora)%></td>
									</tr>
									<tr class="coluna-sh">
										<td align="left">Em aula</td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(haAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(haPrevistasAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(haPrevistasAteAgora - haAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(haPrevistasSemestre)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(haPrevistasSemestre - haAteAgora)%></td>
									</tr>
									<tr class="coluna-sh">
										<td align="left">Total</td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpAteAgora + haAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpPrevistasAteAgora + haPrevistasAteAgora)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours((hpPrevistasAteAgora + haPrevistasAteAgora) - (hpAteAgora + haAteAgora))%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours(hpPrevistasSemestre + haPrevistasSemestre)%></td>
										<td align="right"><%=TimeConverter.ConvertMinuteToHours((hpPrevistasSemestre + haPrevistasSemestre) - (hpAteAgora + haAteAgora))%></td>
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