<%@page import="java.util.ArrayList"%>
<%@page import="br.ages.crud.model.ResumoPonto"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="java.util.List"%>
<jsp:include page="../template/head.jsp"></jsp:include>


<div class="panel panel-primary">

	<div class="panel-heading text-center">Hora Ponto Alunos</div>

	<jsp:include page="/template/msg.jsp"></jsp:include>

	<div class="panel-body">
		<form id="formListAluno" method="post">
		<div class="form-group row">
		<div class='col-sm-6' id='dtInicial'>
			<label for="sel1" class="form-label ages">Data Inicia:<span class="red">*</span></label> 
			<input>
		</div>
		<div class='col-sm-6' id='dtFinall'>
			<label for="sel1" class="form-label ages">Data Inicia:<span class="red">*</span></label> 
			<input>
		</div>
		<button class="btn btn-primary"> Buscar </button>
		<div class='col-sm-6' id='nomeAluno'>
			<label for="sel1" class="form-label ages">Aluno:<span class="red">*</span></label> 
		</div>
		</div>
		</form>
		<div class="table-responsive">
		
			<table id="listaAlunos" class="table table-responsive table-striped table-hover table-condensed">
				<thead>
					<tr>
						<th style="text-align: center;">Nome</th>
						<th style="text-align: center;">Total Horas Válidas</th>
						<th style="text-align: center;">Total Horas Inválidas</th>
						<th style="text-align: center;">Total Horas</th>
					</tr>
				</thead>

				<tbody>
					<%
						List<ResumoPonto> listaResumoPonto = (List<ResumoPonto>) request.getAttribute("listaPontos");
						for (ResumoPonto usuario : listaResumoPonto) {
							String horasValidas;
							String horasInvalidas = (String) request.getAttribute("totalHorasInvalidoAluno");
							String horasTotais;
					%>

					<tr class="coluna-sh">
						<td align="center"><%=usuario.getNomeAluno()%></td>
						<td align="center"><%=usuario.getHoraTotalDiaValido()%></td>
						<td align="center"><%=usuario.getHoraTotalDiaInvalido()%></td>
						<td align="center"><%=usuario.getHoraTotalDia()%></td>
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
	        }
	    });
	});
</script>