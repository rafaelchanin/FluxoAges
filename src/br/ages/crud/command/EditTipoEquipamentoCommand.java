package br.ages.crud.command;

import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Status;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class EditTipoEquipamentoCommand implements Command {

    private String proxima;
    private TipoEquipamentoBO tipoEquipamentoBO;


    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        tipoEquipamentoBO = new TipoEquipamentoBO();

        int id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String statusRq = request.getParameter("status");

        TipoEquipamento tipoEquipamento;
        try{
            Status status = Status.valueOf(statusRq);

            tipoEquipamento = new TipoEquipamento();

            tipoEquipamento.setId(id);
            tipoEquipamento.setNome(nome);
            tipoEquipamento.setStatus(status);

            tipoEquipamentoBO.editarTipoEquipamento(tipoEquipamento);


            request.setAttribute("tipo", tipoEquipamento);

            proxima = "main?acao=listaTiposEquipamentos";
            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDICAO_EQUIPAMENTO.replace("?", nome));

        } catch(Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }
        return proxima;
    }
}
