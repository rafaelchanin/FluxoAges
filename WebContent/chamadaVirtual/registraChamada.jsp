<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.model.Ponto"%>
<%@page import="br.ages.crud.util.Util"%>
<%@page import="br.ages.crud.util.TimeConverter"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="br.ages.crud.model.Turma"%>
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
					<label for="sel1" class="form-label ages">Turma:<span class="red">*</span></label> 
					<select class="form-control" id="turma" name="turma" required>
					
					<%
						List<Turma> turmasAtivas = (List<Turma>) request.getAttribute("turmasAtivas");
					
						for (Turma turma : turmasAtivas) {
							if (request.getAttribute("nomeTurma").equals(turma.toString())) {
						%>	
					<option data-aulas="<%=turma.getAulasString()%>" value="<%=turma.toString()+"|"+turma.getId()%>" selected><%=turma.toString()%></option>
						<%
							} else {
					%>
					<option data-aulas="<%=turma.getAulasString()%>" value="<%=turma.toString()+"|"+turma.getId()%>"><%=turma.toString()%></option>
					
					<%
							}
						}
					%>
				</select>
				</div>
				<div class='col-sm-2' id='dtFinall'>
					<label for="sel1" class="form-label ages">Mês:<span class="red">*</span></label> 
					<select class="form-control" id="mes" name="mes" required>
						<option id="primeiro" value="primeiro"></option>
						<option id="segundo" value="segundo"></option>
						<option id="terceiro" value="terceiro"></option>
						<option id="quarto" value="quarto"></option>
						<option id="quinto" value="quinto"></option>
					
				</select>
				</div>
			</div>
			<div class="table-responsive">
				<table id="chamada" class="table table-responsive table-striped table-hover table-condensed">
					<thead>
						<tr id="titulo">
							<!--<th style="text-align: center;">Nome Aluno</th>
							<th style="text-align: center;">Data Entrada</th>
							<th style="text-align: center;">Data Saída</th>
							<th style="text-align: center;">Horas/dia</th>
							<th style="text-align: center;">Status</th>
							<th style="text-align: center;"></th>
							<th style="text-align: center;"></th>--> 
							
						
							
						</tr>
					</thead>

					<tbody>
						<tr class="coluna-sh">
							<td align="center">ponto.getAluno().getNome()</td>
							<td align="center">Util.dateTimeToString(ponto.getDataEntrada())</td>
							<td align="center">Util.dateTimeToString(ponto.getDataSaida())</td>
							<td align="center">horasDia</td>
							<td align="center">ponto.getStatus().name()></td>
							<td align="center">teste</td>
							<td align="center">teste</td>
						</tr>

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
		$('#chamada').dataTable({
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

	});
	
	$("#turma").on('change', function(e) {
		alert("troco a turma");
		var aulasString = $('#turma option:selected').attr("data-aulas");
		var aulas = aulasString.split(",");
		var semestre = document.getElementById("turma").value;
		semestre = semestre.substring(7, 8);
		if (semestre == 1) {
			document.getElementById("primeiro").innerHTML = "Março";
			document.getElementById("primeiro").value = "03";
			document.getElementById("segundo").innerHTML = "Abril";
			document.getElementById("segundo").value = "04";
			document.getElementById("terceiro").innerHTML = "Maio";
			document.getElementById("terceiro").value = "05";
			document.getElementById("quarto").innerHTML = "Junho";
			document.getElementById("quarto").value = "06";
			document.getElementById("quinto").innerHTML = "Julho";
			document.getElementById("quinto").value = "07";
		}
		else {
			document.getElementById("primeiro").innerHTML = "Agosto";
			document.getElementById("primeiro").value = "08";
			document.getElementById("segundo").innerHTML = "Setembro";
			document.getElementById("segundo").value = "09";
			document.getElementById("terceiro").innerHTML = "Outubro";
			document.getElementById("terceiro").value = "10";
			document.getElementById("quarto").innerHTML = "Novembro";
			document.getElementById("quarto").value = "11";
			document.getElementById("quinto").innerHTML = "Dezembro";
			document.getElementById("quinto").value = "12";
		}
	});
	
	$("#mes").on('change', function(e) {
		var mes = document.getElementById("mes").value;
		var aulasString = $('#turma option:selected').attr("data-aulas");
		var aulas = aulasString.split(",");
		var tr = document.getElementById('chamada').tHead.children[0];
		
		var i=0;
		tr.insertCell(0).outerHTML = '<th style="text-align: center;">' + 'Nome do Aluno' + '</th>';
		for (i=0; i<aulas.length; i++) {
			if (aulas[i].substring(3,5) == mes) {
				tr.insertCell(1).outerHTML = '<th style="text-align: center;">' + aulas[i].substring(0,2) + '</th>';
			}
		}
	

		
	});
</script>