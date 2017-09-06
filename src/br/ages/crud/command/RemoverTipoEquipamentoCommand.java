package br.ages.crud.command;


import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;

public class RemoverTipoEquipamentoCommand implements Command{

    private String proximo;

    private TipoEquipamentoBO tipoEquipamentoBO;

    @Override
    public String execute(HttpServletRequest request) {
        proximo = "main?acao=listaEquipamentos";
        tipoEquipamentoBO = new TipoEquipamentoBO();

        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioSessao");

        try {
            if( !usuario.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);

            Integer id = Integer.parseInt(request.getParameter("id_tipo_equipamento"));
            tipoEquipamentoBO.removerTipoEquipamento(id);

            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REMOVE_EQUIPAMENTO.replace("?", id.toString()).concat("<br/>"));

        } catch (Exception e) {
            request.setAttribute("msgErro", e.getMessage());
        }

        return proximo;
    }
}
