<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.model.Turma"%>
<%@page import="java.util.List"%>
<%@ page import="br.ages.crud.model.Grupo" %>
<jsp:include page="../template/head.jsp"></jsp:include>

<!-- MODAL / POPUP -->
<jsp:include page="../template/modalTurma.jsp"></jsp:include>

<div class="panel panel-primary panel-Turma">

    <div class="panel-heading text-center">Grupos</div>

    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>
        <div class="table-responsive">

            <table id="listaGrupos" class="table table-responsive table-striped table-hover table-condensed table-bordered">

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
                    List<Grupo> listaGrupos = (List<Grupo>) request.getAttribute("listaGrupos");
                    for (Grupo grupo : listaGrupos) {
                %>

                <tr>
                    <td align="center" class="col-sm-4"><%=grupo.getAno()+" / "+ grupo.getSemestre()+" - "+ grupo.getProjeto()%></td>
                    <td align="center" class="col-sm-2"><%=grupo.getStatus()%></td>
                    <td align="center" class="col-sm-6">
                        <button data-toggle="collapse" data-target="#usuarios<%=grupo.getId()%>"><%=grupo.getAlunos().size()%></button>
                        <div id="usuarios<%=grupo.getId()%>" class="collapse">
                            <%
                                List<Usuario> listUsuarios = grupo.getAlunos();
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
                            <a href="" data-toggle="modal" data-id="<%=grupo.getId() %>" data-usuario="<%=grupo.getAno()+" / "+ grupo.getSemestre()+" - "+ grupo.getProjeto()%>"
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
        $('#listaGrupos').dataTable({
            "language": {
                "lengthMenu": "Mostrando _MENU_ registros por página",
                "zeroRecords": "Sem registros - sorry",
                "info": "Mostrando _PAGE_ de _PAGES_ páginas",
                "infoEmpty": "Nenhum registros encontrados!",
                "infoFiltered": "(Filtrado _MAX_ do total deregistros)",
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