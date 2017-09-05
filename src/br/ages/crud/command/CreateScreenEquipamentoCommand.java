package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoBO;
import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class CreateScreenEquipamentoCommand implements Command {

    private String proxima;
    private EquipamentoBO equipamentoBO;
    private TipoEquipamentoBO tipoEquipamentoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        proxima = "main?acao=listaEquipamentos";
        Usuario currentUser = (Usuario)request.getSession().getAttribute("usuarioSessao");

        try{

            if( !currentUser.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);
            String isEdit = request.getParameter("isEdit");

            if (isEdit != null && !"".equals(isEdit)) {
                //Criar depois
            } else {
                proxima = "equipamento/addEquipamento.jsp";

                tipoEquipamentoBO = new TipoEquipamentoBO();

                ArrayList<TipoEquipamento> tipoEquipamentos = tipoEquipamentoBO.listarTipoEquipamentos();

                request.setAttribute("tipos", tipoEquipamentos);
            }
        } catch(Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
