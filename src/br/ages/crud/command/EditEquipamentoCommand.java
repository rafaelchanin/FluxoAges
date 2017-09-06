package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoBO;
import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.Status;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.model.TipoUsuario;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class EditEquipamentoCommand implements Command {

    private String proxima;

    private EquipamentoBO equipamentoBO;
    private TipoEquipamentoBO tipoEquipamentoBO;



    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        equipamentoBO = new EquipamentoBO();
        tipoEquipamentoBO = new TipoEquipamentoBO();
        Equipamento equipamento;
        proxima = "equipamento/editEquipamento.jsp";

        Integer id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String codigo = request.getParameter("codigo");
        String descricao = request.getParameter("descricao");
        String data = request.getParameter("data");
        Integer tipo = Integer.valueOf(request.getParameter("tipo"));
        String statusRq = request.getParameter("status");


        try{
            Status status = Status.valueOf(statusRq);

            equipamento = new Equipamento();

            equipamento.setId(id);
            equipamento.setNome(nome);
            equipamento.setCodigo(codigo);
            equipamento.setDescricao(descricao);
//            equipamento.setDataMovimentacao(data); Calma
            equipamento.setStatus(status);

            TipoEquipamento tipoEquipamento = new TipoEquipamento();
            tipoEquipamento = tipoEquipamentoBO.buscarEquipamentoPorId(tipo);

            equipamento.setTipoEquipamento(tipoEquipamento);

            request.setAttribute("equipamento", equipamento);

            equipamentoBO.editaEquipamento(equipamento);

            proxima = "main?acao=listaEquipamentos";
            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDICAO_EQUIPAMENTO.replace("?", nome));

        } catch(Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }
        return proxima;
    }

}