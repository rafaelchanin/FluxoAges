<%@ page import="br.ages.crud.model.TimePontoDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="br.ages.crud.model.Relatorio" %><%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 20/10/17
  Time: 09:39
  To change this template use File | Settings | File Templates.
--%>

<jsp:include page="../template/headAlunos.jsp"></jsp:include>

<%
    Relatorio relatorio = (Relatorio) request.getAttribute("relatorio");
    String time = (String) request.getAttribute("time");
%>

<div class="panel panel-primary">

    <div class="panel-heading text-center">Relatório Semanal</div>

    <jsp:include page="/template/msg.jsp"></jsp:include>

    <div class="panel-body">
        <form id="formRelatorioSemanal" method="post" action="main?acao=editaRelatorio">
            <input class="form-control" type="hidden" id="idRelatorio" name="idRelatorio" value="<%=relatorio.getIdRelatorio()%>">
            <div class="form-group">

                <div class="row">
                    <div class="col-sm-3">
                        <label class="form-label ages">Semana:<span class="red">*</span></label>
                        <input type="text" class="form-control" id="dia" name="dia" value="<%=relatorio.dataAbertura()%>" readonly/>
                    </div>
                    <%--<div class="col-sm-3">
                        <label class="form-label ages"></label>
                        <div class='input-group date' id='dataSaida'>
                            <input type='text' class="form-control" id="fim"readonly/>
                        </div>
                    </div>--%>
                    <div class='col-sm-6' id='time1'>
                        <label for="sel1" class="form-label ages">Time:<span
                                class="red">*</span></label>
                        <input type="text" class="form-control" id="time" name="time" value="<%=time%>" readonly/>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Atividades Previstas:<span class="red">*</span></label>
                        <textarea class="form-control" id="previstas" name="previstas" required><%=relatorio.getAtividadesPrevistas()%></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Atividades Concluidas:<span class="red">*</span></label>
                        <textarea class="form-control" id="concluidas" name="concluidas" required><%=relatorio.getAtividadesConcluidas()%></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Lições Aprendidas e Problemas Encontrados:<span class="red">*</span></label>
                        <textarea class="form-control" id="problemas" name="problemas" required><%=relatorio.getLicoesProblemas()%></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Proximos Passos:<span class="red">*</span> </label>
                        <textarea class="form-control" id="proximos" name="proximos" required><%=relatorio.getProximo()%></textarea>
                    </div>
                </div>
            </div>
            <hr>
            <p>
                Campos que contém <span class="red">*</span> são obrigatórios
            </p>

            <div class="text-center">
                <input class="btn btn-warning limparUser pull-left" type="reset" value="Limpar"> <input class="btn btn-primary addUser pull-right" type="submit"
                                                                                                        value="Cadastrar">
            </div>
        </form>

    </div>

</div>
<jsp:include page="/template/foot.jsp"></jsp:include>


<script>
    //Põe cor laranja nos titulos
    $('div[class*="box"]').find('label').css('color', '#F89406');

    //Dá espaçamento no grupo usuários
    $('div[class*="bootstrap-duallistbox-container"]').eq(1).addClass('margin-top');
    //Dá espaçamento no Workspace
    $('label:contains("Workspace")').addClass('margin-top');

    //Remove aparencia de input de texto do input de arquivo
    $('label:contains("Arquivo")').siblings('input').removeClass('form-control');
</script>
