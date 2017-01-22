<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.model.Stakeholder"%>
<%@page import="br.ages.crud.model.Time"%>
<%@page import="java.util.List"%>
<%@page import="br.ages.crud.bo.ProjetoBO"%>
<%@page import="br.ages.crud.bo.StakeholderBO"%>
<jsp:include page="../template/head.jsp"></jsp:include>

<!-- MODAL / POPUP -->
<jsp:include page="../template/modalTime.jsp"></jsp:include>
 		
<div class="panel panel-primary panel-Time">
   		
	<div class="panel-heading text-center">Times</div>
               
       <div class="panel-body">
       
		<jsp:include page="/template/msg.jsp"></jsp:include>
        <div class="table-responsive">
        
        <table id="listaTimes" class="table table-responsive table-striped table-hover table-condensed table-bordered">

            <thead>
                <tr>
                    <th style="text-align: center;">Ano/Semestre</th>
                    <th style="text-align: center;">Projeto</th>
                    <th style="text-align: center;">Orientador</th>                    
                    <th style="text-align: center;">Status</th>
					<th style="text-align: center;">Alunos</th>
					<th style="text-align: center;"></th>
                </tr>
            </thead>

            <tbody> 
            	<%	
            		ProjetoBO proj = new ProjetoBO();
            		StakeholderBO stake = new StakeholderBO();
					List<Time> listaTimes = (List<Time>) request.getAttribute("listaTimes");
					for (Time time : listaTimes) {
						
				%>
				          
            	<tr>
	            	<td align="center" class="col-sm-4"><%=time.getAno()+" / "+ time.getSemestre()+"%></td>
	            	<td align="center" class="col-sm-1"><%=proj.buscarProjeto(time.getProjeto()).getNomeProjeto()%></td>
	            	
	            	<!-- stakeholder não funcionam sempre, pq?? -->
	            	<!-- StakeholderBO.buscaStakeholderId(time.getOrientador()).getNomeStakeholder() -->
	            	<td align="center" class="col-sm-3"><%=stake.buscaStakeholderId(time.getOrientador()).getNomeStakeholder()%></td>
	            	
	            	<td align="center" class="col-sm-2"><%=time.getStatus()%></td>
	            	<td align="center" class="col-sm-6">
					<button data-toggle="collapse" data-target="#usuarios<%=time.getId()%>"><%=time.getAlunos().size()%></button>
							<div id="usuarios<%=time.getId()%>" class="collapse">
								<%
									List<Usuario> listUsuarios = time.getAlunos();
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
            				<a href="" data-toggle="modal" data-id="<%=time.getId() %>" data-usuario="<%=time.getAno()+" / "+ time.getSemestre()+" - AGES "%>" 
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
	$('#listaTimes').dataTable({
	    "language": {
            "lengthMenu": "Mostrando _MENU_ registros por página",
            "zeroRecords": "Nenhum time cadastrado.",
            "info": "Mostrando _PAGE_ de _PAGES_ páginas",
            "infoEmpty": "Nenhum registro encontrado!",
            "infoFiltered": "(Filtrado _MAX_ do total de registros)",
            "search":"Busca",
           	"paginate": {
                "first":      "Primeiro",
                "last":       "Último",
                "next":       "Próximo",
                "previous":   "Anterior"
	        },
        }
	});
});;
</script>