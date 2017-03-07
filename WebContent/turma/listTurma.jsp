<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.model.Turma"%>
<%@page import="java.util.List"%>
<jsp:include page="../template/head.jsp"></jsp:include>

<!-- MODAL / POPUP -->
<jsp:include page="../template/modalTurma.jsp"></jsp:include>
 		
<div class="panel panel-primary panel-Turma">
   		
	<div class="panel-heading text-center">Turmas</div>
               
       <div class="panel-body">
       
		<jsp:include page="/template/msg.jsp"></jsp:include>
        <div class="table-responsive">
        
        <table id="listaTurmas" class="table table-responsive table-striped table-hover table-condensed table-bordered">

            <thead>
                <tr>
                    <th style="text-align: center;">Nome</th>
                    <th style="text-align: center;">Status</th>
					<th style="text-align: center;">Alunos</th>
					<th style="text-align: center;"></th>
                </tr>
            </thead>

            <tbody> 
            	<%
					List<Turma> listaTurmas = (List<Turma>) request.getAttribute("listaTurmas");
					for (Turma turma : listaTurmas) {
				%>
				          
            	<tr>
	            	<td align="center" class="col-sm-4"><%=turma.getAno()+" / "+ turma.getSemestre()+" - AGES "+ turma.getAges()+" - "+ turma.getNumero()%></td>
	            	<td align="center" class="col-sm-2"><%=turma.getStatus()%></td>
	            	<td align="center" class="col-sm-6">
					<button data-toggle="collapse" data-target="#usuarios<%=turma.getId()%>"><%=turma.getAlunos().size()%></button>
							<div id="usuarios<%=turma.getId()%>" class="collapse">
								<%
									List<Usuario> listUsuarios = turma.getAlunos();
										for (Usuario usuario : listUsuarios) {
								%>
								<div class="row">
									<div align="left" class="col-sm-10">* <%=usuario.getNome()%></div>
								</div>
								<%
									}
								%>
							</div>
					</td>
	            	<td align="center" class="col-sm-1">
						<form action="" method="post">
            				<a href="" data-toggle="modal" data-id="<%=turma.getId() %>" data-usuario="<%=turma.getAno()+" / "+ turma.getSemestre()+" - AGES "+ turma.getAges()+" - "+ turma.getNumero()%>" 
            				data-target="#modalEditar" title="Editar"> <i class="glyphicon glyphicon-pencil"></i></a>
            			</form>
            		</td>
            		
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

$(document).ready(function(){
	$('#listaTurmas').dataTable({
	    "language": {
            "lengthMenu": "Mostrando _MENU_ registros por p�gina",
            "zeroRecords": "Sem registros - sorry",
            "info": "Mostrando _PAGE_ de _PAGES_ p�ginas",
            "infoEmpty": "Nenhum registros encontrados!",
            "infoFiltered": "(Filtrado _MAX_ do total deregistros)",
            "search":"Busca",
           	"paginate": {
                "first":      "Primeiro",
                "last":       "�ltimo",
                "next":       "Pr�ximo",
                "previous":   "Anterior"
	        },
        }
	});
});;
</script>