<%@page import="br.ages.crud.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.model.ResumoPonto"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.util.TimeConverter"%>
<%@page import="java.util.List"%>
<jsp:include page="../template/head.jsp"></jsp:include>


<div class="panel panel-primary">

	<div class="panel-heading text-center">Relatorio Horas</div>

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
		</form>
		<div class="table-responsive">
		
			<table id="listaAlunos" class="table table-responsive table-striped table-hover table-condensed">
				<thead>
					<tr>
						<th style="text-align: center;">Nome</th>
						<th style="text-align: center;">Realizadas até o momento</th>
						<th style="text-align: center;">Necessárias para aprovacao com 75%</th>
						<th style="text-align: center;">Necessárias para aprovacao com 100%</th>
					</tr>
				</thead>

				<tbody>
					<%
						List<ResumoPonto> listaResumoPonto = (List<ResumoPonto>) request.getAttribute("listaPontos");
						for (ResumoPonto usuario : listaResumoPonto) {
							String horasValidas = TimeConverter.ConvertMinuteToHours(usuario.getHoraTotalDiaValido());
							String horasAprov = TimeConverter.ConvertMinuteToHours(5400 - usuario.getHoraTotalDiaValido());
							String horasAprovCem = TimeConverter.ConvertMinuteToHours(7200 - usuario.getHoraTotalDiaValido());
					%>

					<tr class="coluna-sh aluno" id="<%=usuario.getIdAluno()%>">
						<td align="center"><%=usuario.getNomeAluno()%></td>
						<td align="center"><%=horasValidas%></td>
						<td align="center"><%=horasAprov%></td>
						<td align="center"><%=horasAprovCem%></td>
					</tr>
					
					
					<tr class="alunotitulo<%=usuario.getIdAluno()%>" id="alunotitulo<%=usuario.getIdAluno()%>"
								>
						<td style="text-align: center;">Tipo de horas</td>
						<td style="text-align: center; font-style:italic;">Realizadas</td>
						<td style="text-align: center; font-style:italic;">Previstas até o momento</td>
						
						<td style="text-align: center; font-style:italic;">Previstas até o fim do semestre</td>
						
					</tr>	
					
					<tr class="alunoextra<%=usuario.getIdAluno()%>" id="alunoextra<%=usuario.getIdAluno()%>"
								>
						<td style="text-align: center;">Extraclasse</td>
						<td style="text-align: center; font-style:italic;">Bloco</td>
						
						<td style="text-align: center; font-style:italic;">bloco.getNome()</td>
						<td style="text-align: center;">-</td>
					</tr>
					
					<tr class="alunoaula<%=usuario.getIdAluno()%>" id="alunoaula<%=usuario.getIdAluno()%>"
								>
						<td style="text-align: center;">Em aula</td>
						<td style="text-align: center; font-style:italic;">Bloco</td>
						
						<td style="text-align: center; font-style:italic;">bloco.getNome()</td>
						<td style="text-align: center;">-</td>
					</tr>
					
					<tr class="alunototal<%=usuario.getIdAluno()%>" id="alunototal<%=usuario.getIdAluno()%>"
								> <!-- style="display: none;" -->
						<td style="text-align: center;">Total</td>
						<td style="text-align: center; font-style:italic;">Bloco</td>
						
						<td style="text-align: center; font-style:italic;">bloco.getNome()</td>
						<td style="text-align: center;">-</td>
					</tr>
					
					
					<%
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
//	function filtrarData() {
//		/* var entrada = document.getElementById("dtEntrada").value;
//		var saida = document.getElementById("dtSaida").value; */
//		 document.forms[0].action= 'main?acao=listaPontoHora&id_usuario=0';
//		 document.forms[0].submit();
//		 winconsole.log(id);
//	};
</script>
<script>
	
	$(document).ready(function(){
	    $('#listaAlunos').dataTable({
	    	"language": {
	            "lengthMenu": "Mostrando _MENU_ registros por página",
	            "zeroRecords": "Sem registros - sorry",
	            "info": "Mostrando _PAGE_ de _PAGES_ páginas",
	            "infoEmpty": "Nenhum registros encontrados!",
	            "infoFiltered": "(Filtrado _MAX_ do total deregistros)",
	            "search": "Pesquisar",
	            "paginate": {
	                "first":      "Primeiro",
	                "last":       "Último",
	                "next":       "Próximo",
	                "previous":   "Anterior"
	            },
	        },
	        "ordering": false
	    });
	});
</script>
<script type="text/javascript">
	$(function() {
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
		
		//Funcao para expadir a segunda tabela :D
		$(this)
			.on(
				'click',
				'tr.aluno',
				function() {
					
						var idCapitulo = $(this).attr("id");
					
						$(".alunotitulo" + idCapitulo).show();
						$(".alunoextra" + idCapitulo).show();
						$(".alunoaula" + idCapitulo).show();
						$(".alunototal" + idCapitulo).show();
						
				});
		
	});
</script>