<%@ page import="br.ages.crud.model.Relatorio" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 18/10/17
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="../template/headAlunos.jsp"></jsp:include>

<jsp:include page="../template/modal.jsp"></jsp:include>

<div class="panel panel-primary">

    <div class="panel-heading text-center">Relatorios Semanais</div>

    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>

        <div class="table-responsive">

            <table id="listaRelatorios" class="table table-responsive table-striped table-hover table-condensed table-bordered">

                <thead>
                <tr>
                    <th style="text-align: center;">Data de Abertura</th>
                    <th style="text-align: center;">Status</th>
                    <th style="text-align: center;">Data de Entrega</th>
                    <th style="text-align: center;"></th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<Relatorio> listaRelatorios = (List<Relatorio>) request.getAttribute("listaRelatorios");
                    for (Relatorio relatorio : listaRelatorios) {
                %>

                <tr>
                    <td align="center"><%=relatorio.getInicioSemana()%></td>
                    <td align="center"><%=relatorio.getStatus().toString()%></td>
                    <td align="center"><%=relatorio.getDtInclusao()%></td>
                    <td align="center">
                        <form action="" method="post">
                            <a href="" data-toggle="modal" data-id="<%=relatorio.getIdRelatorio()%>"
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
        $('#listaRelatorios').dataTable({
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