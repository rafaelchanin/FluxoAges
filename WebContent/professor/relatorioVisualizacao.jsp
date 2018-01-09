<%@ page import="br.ages.crud.model.Relatorio" %>
<%@ page import="br.ages.crud.model.StatusRelatorio" %><%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 03/11/17
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="../template/headProfessor.jsp"></jsp:include>

<jsp:include page="../template/modalRelatorioProfessor.jsp"></jsp:include>

<%
    Relatorio relatorio = (Relatorio) request.getAttribute("relatorio");
    String time = (String) request.getAttribute("time");
%>

<div class="panel panel-primary">

    <div class="panel-heading text-center">Relatório Semanal</div>

    <jsp:include page="/template/msg.jsp"></jsp:include>

    <div class="panel-body">

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
                <div class='col-sm-3' id='time1'>
                    <label for="sel1" class="form-label ages">Time:<span
                            class="red">*</span></label>
                    <input type="text" class="form-control" id="time" name="time" value="<%=time%>" readonly/>
                </div>
                <div class="col-sm-1" style="align-self: flex-end">
                    <%if(relatorio.getStatus() == StatusRelatorio.REVISAO){%>
                    <br>
                    <form action="" method="post">
                        <a href="" data-toggle="modal" data-id="<%=relatorio.getIdRelatorio()%>" data-relatorio="<%=relatorio.dataAbertura() + '-' + relatorio.getAluno()%> "
                           data-target="#modalAceitar" title="Aceitar"> <i class="glyphicon glyphicon-check"></i></a>
                    </form>
                    <% }; %>
                </div>
                <div class="col-sm-1" style="align-self: flex-end">
                    <br>
                    <%if(relatorio.getStatus() == StatusRelatorio.REVISAO){%>
                    <form action="" method="post">
                        <a href="" data-toggle="modal" data-id="<%=relatorio.getIdRelatorio()%>" data-relatorio="<%=relatorio.dataAbertura() + '-' + relatorio.getAluno()%>"
                           data-target="#modalRecusar" title="Recusar"> <i class="glyphicon glyphicon-remove"></i></a>
                    </form>
                    <% } %>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <label class="form-label ages">Atividades Previstas:<span class="red">*</span></label>
                    <textarea class="form-control" id="previstas" name="previstas" readonly><%=relatorio.getAtividadesPrevistas()%></textarea>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <label class="form-label ages">Atividades Concluidas:<span class="red">*</span></label>
                    <textarea class="form-control" id="concluidas" name="concluidas" readonly><%=relatorio.getAtividadesConcluidas()%></textarea>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <label class="form-label ages">Lições Aprendidas e Problemas Encontrados:<span class="red">*</span></label>
                    <textarea class="form-control" id="problemas" name="problemas" readonly><%=relatorio.getLicoesProblemas()%></textarea>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <label class="form-label ages">Proximos Passos:<span class="red">*</span> </label>
                    <textarea class="form-control" id="proximos" name="proximos" readonly><%=relatorio.getProximo()%></textarea>
                </div>
            </div>
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
