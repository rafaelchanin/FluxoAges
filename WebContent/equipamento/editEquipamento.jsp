<%@page import="br.ages.crud.model.Equipamento"%>
<%@page import="br.ages.crud.model.TipoEquipamento"%>
<%@page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%Equipamento equipamento = (Equipamento) request.getAttribute("equipamento"); %>

<jsp:include page="../template/head.jsp"></jsp:include>

<div class="panel panel-primary panel-addUser">

    <div class="panel-heading text-center">
        Editar Equipamento
    </div>


    <div class="panel-body">

        <jsp:include page="/template/msg.jsp"></jsp:include>

        <div class="table-responsive">

            <form method="post" action="main?acao=editEquipamento">
                <input class="form-control" type="hidden" id="id" name="id" value="<%=equipamento.getId()%>">
                <div class="form-group">
                    <label class="form-label ages">Nome: <span class="red">*</span></label>
                    <input class="form-control" id="nome" name="nome" value="<%=equipamento.getNome() %>" type="text" maxlength="120" required>

                    <label class="form-label ages">Número: <span class="red">*</span></label>
                    <input class="form-control" id="numero" name="numero" value="<%=equipamento.getNumero() %>" type="text" maxlength="120" required>

                    <label class="form-label ages">Código: <span class="red">*</span></label>
                    <input class="form-control" id="codigo" name="codigo" value="<%=equipamento.getCodigo() %>" type="text" maxlength="120" required>

                    <label class="form-label ages">Descrição: <span class="red"></span></label>
                    <input class="form-control" id="descricao" name="descricao" value="<%=equipamento.getDescricao()%>" type="text" maxlength="100">

                    <%
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    %>

                    <label class="form-label ages">Data: <span class="red"></span></label>
                    <input class="form-control" value="<%=dateFormat.format(equipamento.getDataMovimentacao())%>" type="text" readonly>
                    <input class="form-control" type="hidden" id="data" name="data" value="<%=equipamento.getDataMovimentacao()%>">

                    <label class="form-label ages">Tipo: <span class="red">*</span></label>
                    <select class="form-control" id="tipo" name="tipo" required>
                        <%
                            ArrayList<TipoEquipamento> tipos = (ArrayList<TipoEquipamento>) request.getAttribute("tipos");
                            for (TipoEquipamento tipoEquipamento : tipos) {
                        %>
                        <option value="<%=tipoEquipamento.getId()%>"<%=tipoEquipamento.getId() == equipamento.getTipoEquipamento().getId() ? "selected" : "" %>><%=tipoEquipamento.getNome()%></option>
                        <%
                            }
                        %>
                    </select>

                    <label class="form-label ages">Status: <span class="red">*</span></label>
                    <select class="form-control" id="status" name="status" required>
                        <option value="ATIVO" <%= "ATIVO".equals(equipamento.getStatus().toString()) ? "selected" : "" %>>Ativo</option>
                        <option value="INATIVO" <%= "INATIVO".equals(equipamento.getStatus().toString()) ? "selected" : "" %>>Inativo</option>
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