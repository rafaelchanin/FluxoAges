<%@page import="br.ages.crud.model.TipoEquipamento"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%TipoEquipamento tipoEquipamento = (TipoEquipamento) request.getAttribute("tipoEquipamento"); %>

<jsp:include page="../template/head.jsp"></jsp:include>

<div class="panel panel-primary panel-addUser">

    <div class="panel-heading text-center">
        Editar Equipamento
    </div>


    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>

        <div class="table-responsive">

            <form method="post" action="main?acao=editTipoEquipamento">
                <input class="form-control" type="hidden" id="id" name="id" value="<%=tipoEquipamento.getId()%>">
                <div class="form-group">
                    <label class="form-label ages">Nome: <span class="red">*</span></label>
                    <input class="form-control" id="nome" name="nome" value="<%=tipoEquipamento.getNome() %>" type="text" maxlength="120" required>

                    <label class="form-label ages">Status: <span class="red">*</span></label>
                    <select class="form-control" id="status" name="status" required>
                        <option value="ATIVO" <%= "ATIVO".equals(tipoEquipamento.getStatus().toString()) ? "selected" : "" %>>Ativo</option>
                        <option value="INATIVO" <%= "INATIVO".equals(tipoEquipamento.getStatus().toString()) ? "selected" : "" %>>Inativo</option>
                    </select>
                </div>

                <hr>

                <p>Campos que contém <span class="red">*</span> são obrigatórios</p>


                <div class="text-center">
                    <input class="btn btn-warning limparUser pull-left" type="reset" value="Limpar">
                    <input class="btn btn-primary addUser pull-right" type="submit" value="Salvar">
                </div>

            </form>
        </div>
    </div>
</div>

<jsp:include page="/template/foot.jsp"></jsp:include>