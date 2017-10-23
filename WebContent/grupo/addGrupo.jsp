<%--
  Created by IntelliJ IDEA.
  User: gloff
  Date: 05/10/17
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@page import="br.ages.crud.model.Stakeholder"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="br.ages.crud.model.Usuario"%>
<%@page import="br.ages.crud.model.IdNomeUsuarioDTO"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:include page="../template/head.jsp"></jsp:include>

<div class="panel panel-primary panel-addUser">

    <div class="panel-heading text-center">Cadastro de Grupo</div>

    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>

        <form method="post" action="main?acao=adicionaGrupo"> <!-- enctype="multipart/form-data" > -->

            <div class="form-group">

                <div class="row">
                    <div class="col-sm-6">
                        <label class="form-label ages">Ano:<span class="red">*</span></label>
                        <div class='input-group date' id='dataEntrada'>
                            <input type='text' class="form-control" id="ano" name="ano" value="<%=request.getAttribute("ano")%>" required/>
                            <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <label class="form-label ages">Semestre: <span class="red">*</span></label>
                        <select class="form-control" id="semestre" name="semestre"
                                required>
                            <option value="primeiro" <%="primeiro".equals(request.getParameter("semestre")) ? "selected" : ""%>>Primeiro</option>
                            <option value="segundo" <%="segundo".equals(request.getParameter("semestre")) ? "selected" : ""%>>Segundo</option>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <label class="form-label ages">Projeto: <span class="red">*</span></label>
                        <input type="text" class="form-control bfh-number" id="projeto" name="projeto" required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <label class="form-label ages">Status do Grupo: <span class="red">*</span></label>
                        <select class="form-control" id="statusGrupo" name="statusGrupo"
                                required>
                            <option value="ATIVA" <%="ATIVA".equals(request.getParameter("statusGrupo")) ? "selected" : ""%>>Ativa</option>
                            <option value="INATIVA" <%="INATIVA".equals(request.getParameter("statusGrupo")) ? "selected" : ""%>>Inativa</option>
                            <option value="EXCLUIDA" <%="EXCLUIDA".equals(request.getParameter("statusGrupo")) ? "selected" : ""%>>Exclu�da</option>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <select multiple="multiple" size="10" name="alunos" class="alunos">
                            <%
                                List<IdNomeUsuarioDTO> alunos = (List<IdNomeUsuarioDTO>) request.getAttribute("alunos");
                                for (IdNomeUsuarioDTO usuario : alunos) {
                            %>
                            <option value="<%=usuario.getId()%> <%=usuario.getMatricula()%>"><%=usuario.getNome()%></option>
                            <%
                                }
                            %>

                        </select>
                    </div>
                </div>
            </div>

            <hr>

            <p>
                Campos que cont�m <span class="red">*</span> s�o obrigat�rios
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
            locale : 'pt-br',
            sideBySide : true,
            format: "YYYY"
        });

        //	$("#dataEntrada").on("dp.change", function(e) {
        //		$('#dataSaida').data("DateTimePicker").minDate(e.date);
        //		/* alert(document.getElementById('dataSaida').value); */
        //	});

    });
</script>

<script>
    var demo2 = $('.alunos').bootstrapDualListbox({
        nonSelectedListLabel : 'Alunos',
        selectedListLabel : 'Alunos da Turma',
        preserveSelectionOnMove : 'moved',
        moveOnSelect : false,
        nonSelectedFilter : '',
        filterTextClear : 'Mostrar Todos',
        infoTextEmpty : 'Sem alunos '
    });
</script>


<script>
    //P�e cor laranja nos titulos
    $('div[class*="box"]').find('label').css('color', '#F89406');

    //D� espa�amento no grupo usu�rios
    $('div[class*="bootstrap-duallistbox-container"]').eq(1).addClass('margin-top');
    //D� espa�amento no Workspace
    $('label:contains("Workspace")').addClass('margin-top');

    //Remove aparencia de input de texto do input de arquivo
    $('label:contains("Arquivo")').siblings('input').removeClass('form-control');
</script>