package br.ages.crud.bo;

import br.ages.crud.dao.GrupoDAO;
import br.ages.crud.dao.TurmaDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Grupo;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.DataValidator;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class GrupoBO {

    private GrupoDAO grupoDAO;

    public GrupoBO() {
        grupoDAO = new GrupoDAO();
    }

    public boolean validarGrupo(Grupo grupo) throws NegocioException {
        boolean valido = true;
        StringBuilder msg = new StringBuilder();
        msg.append(MensagemContantes.MSG_ERR_GRUPO_DADOS_INVALIDOS.concat("<br/>"));

        DataValidator validator = new DataValidator();

        if (grupo.getStatus() == null || grupo.getStatus().equals("")) {
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_CAMPO_INVALIDO.replace("?", "Status ").concat("<br/>"));
        }
        if (grupo.getProjeto() == null || grupo.getProjeto().equals("")) {
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Projeto ").concat("<br/>"));
        }
        if (grupo.getAno() == 0) {
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Ano ").concat("<br/>"));
        }

        if (!valido) {
            throw new NegocioException(msg.toString());
        }
        return valido;
    }

    public boolean cadastrarGrupo(Grupo grupo) throws SQLException, ParseException, NegocioException, PersistenciaException {
        boolean ok = false;
        ok = grupoDAO.cadastrarGrupo(grupo);
        if (ok == false)
            throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_GRUPO);
        return ok;
    }

    public boolean inserirAlunosGrupo(int idGrupo, List<Usuario> alunos) throws SQLException, ParseException, NegocioException, PersistenciaException {
        boolean ok = false;
        ok = grupoDAO.inserirAlunosGrupo(idGrupo, alunos);
        if (ok == false)
            throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_GRUPO);
        return ok;
    }

    public boolean removerAlunosGrupo(int idGrupo, List<Usuario> alunos) throws SQLException, ParseException, NegocioException, PersistenciaException {
        boolean ok = false;
        ok = grupoDAO.removerAlunosGrupo(idGrupo, alunos);
        if (ok == false)
            throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_GRUPO);
        return ok;
    }

    public void editaGrupo(Grupo grupo) throws NegocioException {

        try {
            grupoDAO.editaGrupo(grupo);
        } catch (PersistenciaException | SQLException | ParseException e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }

    }

    public List<Grupo> listarGrupos() throws NegocioException {
        List<Grupo> listGrupos = null;

        try {
            listGrupos = grupoDAO.listarGrupos();
        } catch (PersistenciaException | SQLException e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }

        return listGrupos;
    }

    public List<Grupo> listarGruposAtivos() throws NegocioException {
        List<Grupo> listGrupo = null;

        try {
            listGrupo = grupoDAO.listarGruposAtivos();
        } catch (PersistenciaException | SQLException e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }

        return listGrupo;
    }

    public Grupo buscarGrupo(int idGrupo) throws NegocioException, PersistenciaException, SQLException {
        Grupo grupo= grupoDAO.buscaGrupo(idGrupo);
        return grupo;
    }

    public void setGrupoDAO(GrupoDAO grupoDAO) {
        this.grupoDAO = grupoDAO;
    }

}
