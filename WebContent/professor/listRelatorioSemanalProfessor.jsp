<%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 25/10/17
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="br.ages.crud.model.Relatorio" %>
<%@ page import="java.util.List" %>
<%@ page import="br.ages.crud.model.StatusRelatorio" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="br.ages.crud.model.TimePontoDTO" %>
<%@ page import="br.ages.crud.model.TimeRelatorio" %>

<jsp:include page="../template/headProfessor.jsp"></jsp:include>

<jsp:include page="../template/modalRelatorioProfessor.jsp"></jsp:include>

<div class="panel panel-primary">

    <div class="panel-heading text-center">Relatorios Semanais</div>

    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>

        <form id="formListAluno" method="post">
            <div class="form-group row">
                <div class='col-sm-6' id='time1'>
                    <label for="sel1" class="form-label ages">Time:<span
                            class="red">*</span></label>
                    <select class="form-control" id="time" name="time" required>

                        <%
                            List<TimeRelatorio> listaTimes = (List<TimeRelatorio>) request.getAttribute("listaRelatorio");
                            for (TimeRelatorio time : listaTimes) {
                        %>
                        <option value="<%=time.toString()%>" id="<%=time.getId()%>"><%=time.toString()%></option>

                        <%
                            };
                        %>
                    </select>
                </div>
            </div>
        </form>
        <div class="table-responsive">
            <%for (TimeRelatorio time : listaTimes) {	 %>
            <table id="listaRelatorio<%=time.getId()%>" class="listaRelatorio<%=time.getId()%> table table-responsive table-striped table-hover table-condensed table-bordered" style="display: none;">

                <thead>
                <tr>
                    <th style="text-align: center;">Aluno</th>
                    <th style="text-align: center;">Status</th>
                    <th style="text-align: center;">Data de Abertura</th>
                    <th style="text-align: center;">Data de Entrega</th>
                    <th style="text-align: center"></th>
                </tr>
                </thead>

                <tbody>
                <%
                    List<Relatorio> listaRelatorios = time.getRelatorio();
                    for (Relatorio relatorio : listaRelatorios) {
                %>

                <tr>
                    <td align="center"><%=relatorio.getAluno()%></td>
                    <td align="center"><%=relatorio.getStatus().toStringFormal()%></td>
                    <td align="center"><%=relatorio.dataAbertura()%></td>
                    <td align="center"><%=relatorio.dataEntrega()%></td>
                    <td align="center">
                        <form action="main?acao=visualizarRelatorio&id_relatorio=<%=relatorio.getIdRelatorio()%>" method="post" id="formVisualizar">
                            <button type="submit" class="btn-link"><i class="glyphicon glyphicon-eye-open"></i></button>
                        </form>
                    </td>
                </tr>
                <%
                    };
            };
                %>
                </tbody>


            </table>

        </div>
    </div>
</div>


<jsp:include page="../template/foot.jsp"></jsp:include>


<script>
    var timeSelecionado = 0;
    $(document).ready(function(){
        //ATENCAO, DPS TEM QUE OTIMIZAR PRA UMA FUNCAO
        var id = $(this).find('option:selected').attr('id');
        $(".listaRelatorio" + id).toggle();
        timeSelecionado=id;
        //
        $('#listaRelatorio' + id).dataTable({
            "language": {
                "lengthMenu": "Mostrando _MENU_ registros por página",
                "zeroRecords": "Sem registros",
                "info": "Mostrando _PAGE_ de _PAGES_ páginas, _TOTAL_ registros",
                "infoEmpty": "Nenhum registro encontrado!",
                "infoFiltered": "( _TOTAL_ de _MAX_ registros)",
                "search": "Pesquisar",
                "paginate": {
                    "first":      "Primeiro",
                    "last":       "Último",
                    "next":       "Próximo",
                    "previous":   "Anterior"
                },
            },
            "destroy": true,
            "ordering": true
        });
    });

    $("#time").change(function() {
        //JUNTAR COM UMA FUNCAO
        $(".listaRelatorio" + timeSelecionado).toggle();
        var table = $('#listaRelatorio' + timeSelecionado).DataTable();
        table.destroy();
        var id = $(this).find('option:selected').attr('id');
        $(".listaRelatorio" + id).toggle();
        timeSelecionado=id;

        $('#listaRelatorio' + id).dataTable({
            "language": {
                "lengthMenu": "Mostrando _MENU_ registros por página",
                "zeroRecords": "Sem registros",
                "info": "Mostrando _PAGE_ de _PAGES_ páginas, _TOTAL_ registros",
                "infoEmpty": "Nenhum registro encontrado!",
                "infoFiltered": "( _TOTAL_ de _MAX_ registro)",
                "search": "Pesquisar",
                "paginate": {
                    "first":      "Primeiro",
                    "last":       "Último",
                    "next":       "Próximo",
                    "previous":   "Anterior"
                },
            },
            "destroy": true,
            "ordering": true
        });
        //
    });
</script>