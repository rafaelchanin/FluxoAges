<%@ page import="java.util.ArrayList" %>
<%@ page import="br.ages.crud.model.Equipamento" %>
<%@ page import="br.ages.crud.model.Usuario" %>
<jsp:include page="../template/head.jsp"></jsp:include>

<!-- MODAL / POPUP -->
<jsp:include page="../template/modal.jsp"></jsp:include>

<div class="panel panel-primary">

    <div class="panel-heading text-center">Lista de Equipamentos</div>

    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>
        <div class="table-responsive">

            <table id="listaAlunos" class="table table-responsive table-striped table-hover table-condensed table-bordered">

                <thead>
                <tr>
                    <th style="text-align: center;">ID</th>
                    <th style="text-align: center;">Nome</th>
                    <th style="text-align: center;">Código</th>
                    <th style="text-align: center;">Descrição</th>
                    <th style="text-align: center;"></th>
                    <th style="text-align: center;"></th>
                </tr>
                </thead>

                <tbody>
                <%
                    ArrayList<Equipamento> equipamentos = (ArrayList<Equipamento>) request.getAttribute("listaEquipamentos");
                    for (Equipamento equipamento : equipamentos) {
                %>

                <tr>
                    <td align="center"><%=equipamento.getId()%></td>
                    <td align="center"><%=equipamento.getNome()%></td>
                    <td align="center"><%=equipamento.getCodigo()%></td>
                    <td align="center"><%=equipamento.getDescricao()%></td>
                    <td align="center">
                        <form action="" method="post">
                            <a href="" data-toggle="modal" data-id="<%=equipamento.getId() %>" data-usuario="<%=equipamento.getNome()%>"
                               data-target="#modalEditar" title="Editar"> <i class="glyphicon glyphicon-pencil"></i></a>
                        </form>
                    </td>

                    <td align="center">
                        <form action="" method="post">
                            <a href="" data-toggle="modal" data-id="<%=equipamento.getId() %>" data-usuario="<%=equipamento.getNome()%>"
                               data-target="#modalExcluir" title="Deletar"> <i class="glyphicon glyphicon-trash"></i></a>
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
        $('#listaAlunos').dataTable({
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