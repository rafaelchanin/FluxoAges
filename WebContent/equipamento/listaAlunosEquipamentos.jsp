<%@ page import="br.ages.crud.model.EquipamentoAluno" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<jsp:include page="../template/head.jsp"></jsp:include>

<!-- MODAL / POPUP -->
<jsp:include page="../template/modalEquipamentoAluno.jsp"></jsp:include>

<div class="panel panel-primary">

    <div class="panel-heading text-center">Lista de Equipamentos</div>

    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>

        <div class="table-responsive">

            <table id="listaAlunos" class="table table-responsive table-striped table-hover table-condensed table-bordered table2excel">

                <thead>
                <tr>
                    <th style="text-align: center;">ID</th>
                    <th style="text-align: center;">Equipamento</th>
                    <th style="text-align: center;">Código</th>
                    <th style="text-align: center;">Nome do Aluno</th>
                    <th style="text-align: center;">Matrícula</th>
                    <th style="text-align: center;">Data Retirada</th>
                    <th style="text-align: center;">Data Entrega</th>
                    <th style="text-align: center;"></th>
                </tr>
                </thead>

                <tbody>
                <%
                    ArrayList<EquipamentoAluno> equipamentoAlunos = (ArrayList<EquipamentoAluno>) request.getAttribute("lista");
                    for (EquipamentoAluno equipamentoAluno : equipamentoAlunos) {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String dataVazia = String.valueOf(equipamentoAluno.getDataEntrega());
                %>
                <tr>
                    <td align="center"><%=equipamentoAluno.getId()%></td>
                    <td align="center"><%=equipamentoAluno.getEquipamento().getNome()%></td>
                    <td align="center"><%=equipamentoAluno.getEquipamento().getCodigo()%></td>
                    <td align="center"><%=equipamentoAluno.getAluno().getNome()%></td>
                    <td align="center"><%=equipamentoAluno.getAluno().getMatricula()%></td>
                    <td align="center"><%=dateFormat.format(equipamentoAluno.getDataRetirada().getTime())%></td>
                    <td align="center"><%=dataVazia.equals("null") ? "" : dateFormat.format(equipamentoAluno.getDataEntrega().getTime())%></td>
                    <td align="center">
                        <form action="" method="post">
                            <%
                                if(dataVazia.equals("null")){
                            %>
                            <a href="" data-toggle="modal" data-id="<%=equipamentoAluno.getId() %>" data-nome="<%=equipamentoAluno.getEquipamento().getNome()%>"
                               data-target="#modalEditar" title="Entregar equipamento"> <i class="glyphicon glyphicon-ok"></i></a>
                            <%
                                }
                            %>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>

            </table>
            <form action="downloadRelatorioEmprestimo"
                    method="get" id="formDownload">
                <a class="btn btn-warning" href="#" title="Exportar Excel"
                   onclick="document.getElementById('formDownload').submit();">
                    Exportar Excel
                </a>
            </form>
        </div>
    </div>
</div>
<jsp:include page="../template/foot.jsp"></jsp:include>
<script>

    $(document).ready(function(){
        $('#listaAlunos').dataTable({
            "order" : [],
            "columnDefs" : [ {
                "targets" : 'no-sort',
                "orderable" : false,
            } ],
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