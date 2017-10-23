<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.LinkedHashSet"%>
<%@page import="br.ages.crud.util.Util" %>
<%@ page import="br.ages.crud.model.*" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>


<%Grupo grupo = (Grupo) request.getAttribute("grupo"); %>

<%request.setAttribute("id", Integer.valueOf(grupo.getId())); %>

<jsp:include page="../template/head.jsp"></jsp:include>
<script src="../js/cadastro-projeto.js"></script>

<div class="panel panel-primary panel-addUser">

    <div class="panel-heading text-center">Editar Turma</div>

    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>



        <form method="post" action="main?acao=editaGrupo"> <!-- enctype="multipart/form-data" > -->

            <input class="form-control" type="hidden" id="idGrupo" name="idGrupo" value="<%=grupo.getId()%>">

            <div class="form-group">

                <div class="row">
                    <div class="col-sm-6">
                        <label class="form-label ages">Ano:<span class="red">*</span></label>
                        <div class='input-group date' id='dataEntrada'>
                            <input type='text' class="form-control" id="ano" name="ano" value="<%=grupo.getAno()%>" required/>
                            <span class="input-group-addon">
										<span class="glyphicon glyphicon-calendar"></span>
									</span>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <label class="form-label ages">Semestre: <span class="red">*</span></label>
                        <select class="form-control" id="semestre" name="semestre"
                                required>
                            <option value="primeiro" <%=1 == grupo.getSemestre() ? "selected" : ""%>>Primeiro</option>
                            <option value="segundo" <%=2 == grupo.getSemestre() ? "selected" : ""%>>Segundo</option>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <label class="form-label ages">Projeto: <span class="red">*</span></label>
                        <input type="text" class="form-control bfh-number" id="projeto" value="<%=grupo.getProjeto()%>" name="projeto" required>
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

                <!-- USUARIOS -->
                <!-- http://www.virtuosoft.eu/code/bootstrap-duallistbox/ -->
                <div class="row">
                    <div class="col-md-12">
                        <select multiple="multiple" size="10" name="alunos" class="alunos">
                            <%
                                List<IdNomeUsuarioDTO> alunos = (List<IdNomeUsuarioDTO>) request.getAttribute("alunos");
                                List<IdNomeUsuarioDTO> alunosProjeto = (List<IdNomeUsuarioDTO>) request.getAttribute("alunosProjeto");
                                for (IdNomeUsuarioDTO usuario : alunos) {
                            %>
                            <option value="<%=usuario.getId()%> <%=usuario.getMatricula()%>"><%=usuario.getNome()%></option>
                            <%
                                }
                            %>
                            <%
                                for (IdNomeUsuarioDTO alunoDisponivel : alunosProjeto) {
                            %>
                            <option value="<%=alunoDisponivel.getId()%> <%=alunoDisponivel.getMatricula()%>" selected="selected"><%=alunoDisponivel.getNome()%></option>
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

<!-- USUARIOS -->
<!-- http://www.virtuosoft.eu/code/bootstrap-duallistbox/ -->
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
        selectedListLabel : 'Alunos do Grupo',
        preserveSelectionOnMove : 'moved',
        moveOnSelect : false,
        nonSelectedFilter : '',
        filterTextClear : 'Mostrar Todos',
        infoTextEmpty : 'Sem alunos'
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