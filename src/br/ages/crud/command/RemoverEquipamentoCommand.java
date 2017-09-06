package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class RemoverEquipamentoCommand implements Command {
    private String proximo;

    private EquipamentoBO equipamentoBO;

    @Override
    public String execute(HttpServletRequest request) {
        proximo = "main?acao=listaEquipamentos";
        equipamentoBO = new EquipamentoBO();

        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioSessao");

        try {
            if( !usuario.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);

            Integer id = Integer.parseInt(request.getParameter("id_equipamento"));
            equipamentoBO.removerEquipamento(id);

            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REMOVE_EQUIPAMENTO.replace("?", id.toString()).concat("<br/>"));

        } catch (Exception e) {
            request.setAttribute("msgErro", e.getMessage());
        }

        return proximo;
    }
}
