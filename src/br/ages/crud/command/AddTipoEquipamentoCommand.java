package br.ages.crud.command;

import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class AddTipoEquipamentoCommand implements Command {
    private String proxima;
    private TipoEquipamentoBO tipoEquipamentoBO;
    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        tipoEquipamentoBO = new TipoEquipamentoBO();
        proxima = "main?acao=telaTipoEquipamento";

        String nome = request.getParameter("nome");
        try{
            TipoEquipamento tipoEquipamento = new TipoEquipamento();
            tipoEquipamento.setNome(nome);
            proxima = "main?acao=listaTiposEquipamentos";

            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_EQUIPAMENTO.replace("?", tipoEquipamento.getNome()));

        } catch (Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
