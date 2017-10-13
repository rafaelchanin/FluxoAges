<%@ page import="java.util.HashMap" %>
<%@ page import="br.ages.crud.model.TimePontoDTO" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 09/10/17
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="../template/headAlunos.jsp"></jsp:include>

<div class="panel panel-primary">

    <div class="panel-heading text-center">Relatório Semanal</div>

    <jsp:include page="/template/msg.jsp"></jsp:include>

    <div class="panel-body">
        <form id="formRelatorioSemanal" method="post" action="main?acao=adicionaRelatorio">
            <div class="form-group">

                <div class="row">
                    <div class="col-sm-3">
                            <label class="form-label ages">Semana:<span class="red">*</span></label>
                            <div class='input-group date' id='dataEntrada'>
                                <input type='text' class="form-control" id="dia" onchange="updateInput()" required/>
                                <span class="input-group-addon">
					    					<span class="glyphicon glyphicon-calendar"></span>
					    				</span>
                            </div>
                    </div>
                    <div class="col-sm-3">
                        <label class="form-label ages"></label>
                        <div class='input-group date' id='dataSaida'>
                            <input type='text' class="form-control" id="fim"readonly/>
                        </div>
                    </div>
                    <div class='col-sm-6' id='time1'>
                        <label for="sel1" class="form-label ages">Time:<span
                                class="red">*</span></label>
                        <select class="form-control" id="time" name="time" required>

                            <%
                                List<TimePontoDTO> listaTimes = (List<TimePontoDTO>) request.getAttribute("listaTimes");
                                for (TimePontoDTO time : listaTimes) {
                            %>
                            <option value="<%=time.toString()%>" id="<%=time.getId()%>"><%=time.toString()%></option>

                            <%
                                }
                            %>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Atividades Previstas:<span class="red">*</span></label>
                        <textarea class="form-control" id="previstas" name="previstas"></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Atividades Concluidas:<span class="red">*</span></label>
                        <textarea class="form-control" id="concluidas" name="concluidas"></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Lições Aprendidas e Problemas Encontrados:<span class="red">*</span></label>
                        <textarea class="form-control" id="problemas" name="problemas"></textarea>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <label class="form-label ages">Proximos Passos:<span class="red">*</span> </label>
                        <textarea class="form-control" id="proximos" name="proximos"></textarea>
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


<script type="text/javascript">
    $(function() {

        $('#dataEntrada').datetimepicker({
            daysOfWeekDisabled: [0,2,3,4,5,6],
            locale : 'pt-br',
            sideBySide : true,
            format: "DD/MM/YYYY"
        });

    });
</script>

<script>
    $('#dataEntrada').datepicker()
        .on('changeDate', function(e) {
            document.getElementById("fim").value = e.value;
        });
</script>


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