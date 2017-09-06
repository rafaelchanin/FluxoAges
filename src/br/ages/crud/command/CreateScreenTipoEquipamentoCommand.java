package br.ages.crud.command;

import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateScreenTipoEquipamentoCommand implements Command {

    private String proxima;

    private TipoEquipamentoBO tipoequipamentoBO;

    //private StakeholderBO stakeholderBO;

    public String execute(HttpServletRequest request) throws SQLException {
        proxima = "main?acao=listUser";
        Usuario currentUser = (Usuario) request.getSession().getAttribute("usuarioSessao");

        try {
            if (!currentUser.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR))
                throw new NegocioException(MensagemContantes.MSG_INF_DENY);
            // Verifica se abre tela edição de pessoa ou de adição de pessoa.

            String isEdit = request.getParameter("isEdit");

            TipoEquipamentoBO tipoEquipamentoBO = new TipoEquipamentoBO();
            ArrayList<TipoEquipamento> tipoUsuarios = new ArrayList<TipoEquipamento>();
            tipoUsuarios = tipoEquipamentoBO.listarTipoEquipamentos();
            request.setAttribute("addTipoEquipamento", tipoUsuarios);

            if (isEdit != null && !"".equals(isEdit)) {

            } else { // Adiciona um novo usuário
                proxima = "equipamento/addTipoEquipamento.jsp";
            }

        } catch (Exception e) {
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
