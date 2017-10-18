package br.ages.crud.command;

import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Relatorio;
import br.ages.crud.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ListarRelatorioSemanalCommand implements Command {

    private String proxima;
    private RelatorioBO relatorioBO;
    private List<Relatorio> listaRelatorio;

    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        relatorioBO = new RelatorioBO();
        proxima = "aluno/listRelatorio.jsp";

        try{
            Usuario aluno = (Usuario) request.getSession().getAttribute("usuarioSessao");
            listaRelatorio = relatorioBO.listarRelatorios(aluno.getIdUsuario());
        }catch (Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }

        request.setAttribute("listaRelatorios", listaRelatorio);
        return proxima;
    }
}
