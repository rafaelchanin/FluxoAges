package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoBO;
import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class AddEquipamentoCommand implements Command {

    private String proxima;
    private EquipamentoBO equipamentoBO;
    private TipoEquipamentoBO tipoEquipamentoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        equipamentoBO = new EquipamentoBO();
        tipoEquipamentoBO = new TipoEquipamentoBO();
        proxima = "main?acao=telaEquipamento";

        String nome = request.getParameter("nome");
        String codigo = request.getParameter("codigo");
        String descricao = request.getParameter("descricao");

        //O que vem aqui?
        int tipo = Integer.parseInt(request.getParameter("tipo"));

        try{
            Equipamento equipamento = new Equipamento();
            equipamento.setNome(nome);
            equipamento.setCodigo(codigo);
            equipamento.setDescricao(descricao);

            TipoEquipamento tipoEquipamento = new TipoEquipamento();
            tipoEquipamento = tipoEquipamentoBO.buscarEquipamentoPorId(tipo);
            equipamento.setTipoEquipamento(tipoEquipamento);

            equipamentoBO.cadastrarEquipamento(equipamento);
            proxima = "main?acao=listaEquipamentos";

            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_EQUIPAMENTO.replace("?", equipamento.getNome()));

        } catch (Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
