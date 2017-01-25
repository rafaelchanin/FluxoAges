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
		<form id="formListAluno" name="formListAluno" method="post" action="main?acao=adicionaChamada">
			<div class="form-group row">
				<div class='col-sm-2' id='dtInicial'>
					<label for="sel1" class="form-label ages">Turma:<span class="red">*</span></label> 
					<select class="form-control" id="turma" name="turma" required>
					
					<%
						List<Turma> turmasAtivas = (List<Turma>) request.getAttribute("turmasAtivas");
					
						for (Turma turma : turmasAtivas) {
							if (request.getAttribute("nomeTurma").equals(turma.toString())) {
						%>	
					<option data-aulasMarcadas="<%=turma.getPresencas()%>" data-aulas="<%=turma.getAulasString()%>" data-alunos="<%=turma.getAlunosString()%>" value="<%=turma.toString()+"|"+turma.getId()%>" selected><%=turma.toString()%></option>
						<%
							} else {
					%>
					<option data-aulasMarcadas="<%=turma.getPresencas()%>" data-aulas="<%=turma.getAulasString()%>" data-alunos="<%=turma.getAlunosString()%>" value="<%=turma.toString()+"|"+turma.getId()%>"><%=turma.toString()%></option>
					
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
						
					</thead>

					<tbody> 
					</tbody>

				</table>
			</div>
			
			<div class="col-md-2 " style="float:right;">
				
					<input class="btn btn-primary btnHoras" type="button" onclick="funcSubmit()" value="Salvar">
					<!--  <br> -->
					<input type="hidden" name="resultado" id="resultado" value="">
				
			</div>
			
		</form>
	</div>

</div>
<jsp:include page="../template/foot.jsp"></jsp:include>

<script>
	$(document).ready(function() {
		defineMeses();
		montaTabela();
	});
	
	$("#turma").on('change', function(e) {
		defineMeses();
		montaTabela();
	});
	
	function defineMeses() {
		var aulasString = $('#turma option:selected').attr("data-aulas");
		if (aulasString != null)
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
	}
	
	function clickBotao(loc) {
		if (loc.value == 'P') {
			loc.value = 'F';  
			loc.className = 'btnFalta';
		} else {
			loc.value = 'P';
			loc.className = 'btnPresenca';
		}
	}
	
	$("#mes").on('change', function(e) {
		montaTabela();
	});
	function montaTabela() {
		var mes = document.getElementById("mes").value;
		var aulasString = $('#turma option:selected').attr("data-aulas");
		var presen = $('#turma option:selected').attr("data-aulasMarcadas");
		var alunosString = $('#turma option:selected').attr("data-alunos");
		if (aulasString != null)
			var aulas = aulasString.split(",");
		var aulasMes = [];
		if (alunosString != null)
			var alunos = alunosString.split(",");
		$('#chamada').empty()
		var titulo = "";
		titulo += '<tr id="titulo"><th style="text-align: center;">' + 'Nome do Aluno' + '</th>';
		var i=0;
		var j=0;
		for (i=0; i<aulas.length; i++) {
			var temp = aulas[i].split(":");
			if (temp[1].substring(3,5) == mes) {
				aulasMes.push(temp[0]);
				titulo +='<th style="text-align: center;">' + temp[1].substring(0,2) + '</th>';
			}
		}
		titulo +='</tr>';
		$('#chamada').append(titulo);
		var linha = "";
		var ArrayAlunoAulas = presen.split(";"); //aluno: aulas
		for (i=0;i<alunos.length;i++) {
			
			linha += '<tr class="coluna-sh">';
			var ArrayIdAluno = alunos[i].split(":");
			linha += '<td align="center">' + ArrayIdAluno[1] + '</td>';
			
			for (j=0; j<aulasMes.length; j++) {
				var z=0;
				var verif=0;
				for (z=0;z<ArrayAlunoAulas.length;z++) {
					if  (ArrayAlunoAulas[z] != "") {
						var ArrayIdAulas = ArrayAlunoAulas[z].split(":");
						var idNomeVerificado = ArrayIdAulas[0];
						var idAulasVerificado = ArrayIdAulas[1];
						var ArrayidAulasVerificados = idAulasVerificado.split(",");
						if (idNomeVerificado == ArrayIdAluno[0] && jQuery.inArray(aulasMes[j], ArrayidAulasVerificados) != -1) {
							linha += '<td align="center">' + '<input type="button" id="' + ArrayIdAluno[0] + ':' + aulasMes[j] + '" value="P" onclick="clickBotao(this);" class="btnPresenca" />' + '</td>';
							verif=1;
							break;
						}
					}
				}
				if (verif==0)
					linha += '<td align="center">' + '<input type="button" id="' + ArrayIdAluno[0] + ':' + aulasMes[j] + '" value="F" onclick="clickBotao(this);" class="btnFalta" />' + '</td>';
			}
			linha += '</tr>';
		}
		$('#chamada').append(linha);
	}
	
	function funcSubmit() {
	 	var aulas = [];
		var form = document.getElementById("formListAluno");
		var inputTypes = [];
		$('input[name!=""][value="P"][id!=""]').each(function() {
		    inputTypes.push($(this).prop('id'));
		});
		document.getElementById("resultado").value = inputTypes;
		form.submit();
	}
</script>