package br.ages.crud.bo;

import br.ages.crud.dao.RelatorioDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Relatorio;
import br.ages.crud.model.StatusRelatorio;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.DataValidator;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("LossyEncoding")
public class RelatorioBO {

    private RelatorioDAO relatorioDAO;

    public RelatorioBO() { relatorioDAO = new RelatorioDAO(); }

    public boolean validarRelatorio(Relatorio relatorio) throws NegocioException, ParseException {
        boolean valido = true;
        SimpleDateFormat textFormat = new SimpleDateFormat("dd/MM/yyyy");


        StringBuilder msg = new StringBuilder();
        msg.append(MensagemContantes.MSG_ERR_RELATORIO_DADOS_INVALIDOS.concat("<br/>"));

        DataValidator validator = new DataValidator();

        if(relatorio.getAtividadesPrevistas() == null || relatorio.getAtividadesPrevistas().equals("")){
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?","Atividades Previstas").concat("<br/>"));
        }
        if(relatorio.getAtividadesConcluidas() == null || relatorio.getAtividadesConcluidas().equals("")){
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?","Atividades Concluidas").concat("<br/>"));
        }
        if(relatorio.getLicoesProblemas() == null || relatorio.getLicoesProblemas().equals("")){
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?","Lições Aprendidas e Problemas Encontrados").concat("<br/>"));
        }
        if(relatorio.getProximo() == null || relatorio.getProximo().equals("")){
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?","Proximos Passos").concat("<br/>"));
        }
        Calendar c = Calendar.getInstance();
        c.setTime(textFormat.parse(relatorio.dataEntrega()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek != 2){
            valido = false;
            msg.append(MensagemContantes.MSG_ERR_DATA.concat("<br/>"));
        }
        if(!valido){
            throw new NegocioException(msg.toString());
        }
        return valido;
    }

    public boolean cadastrarRelatorio(Relatorio relatorio) throws NegocioException {
        boolean ok = false;

        try {
            ok = relatorioDAO.cadastrarRelatorio(relatorio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!ok){
            throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_RELATORIO);
        }
        return ok;
    }

    public Relatorio buscaRelatorioId(int id){
        Relatorio relatorio;

        relatorio = relatorioDAO.buscaRelatorioId(id);
        relatorio.setIdRelatorio(id);

        relatorio = relatorioDAO.buscaRespostas(id, relatorio);

        return relatorio;
    }

    public void editarRelatorio(Relatorio relatorio){

        try {
            relatorioDAO.editarRelatorio(relatorio);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Relatorio> listarRelatorios(int idAluno) throws NegocioException {
        List<Relatorio> listRelatorio = null;

        try {
            int idTimeAluno = relatorioDAO.time(idAluno);
            if(idTimeAluno > 0) {
                listRelatorio = relatorioDAO.listarRelatorios(idTimeAluno);
            } else throw new NegocioException(MensagemContantes.MSG_ERR_LISTAR_RELATORIOS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRelatorio;
    }

    public List<Relatorio> listarRelatoriosCoord(int idAluno, String nomeAluno) throws NegocioException {
        List<Relatorio> listRelatorio = null;

        try {
            int idTimeAluno = relatorioDAO.time(idAluno);
            if(idTimeAluno > 0) {
                listRelatorio = relatorioDAO.listarRelatoriosCoord(idTimeAluno,idAluno, nomeAluno);
            } else throw new NegocioException(MensagemContantes.MSG_ERR_LISTAR_RELATORIOS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRelatorio;
    }

    public int validaAluno(Usuario aluno, int idTime) throws NegocioException {
        int idTimeAluno = 0;
        if(aluno != null && idTime > 0){
            idTimeAluno = relatorioDAO.validaAluno(aluno,idTime);
        }
        if(idTimeAluno == 0){
            throw new NegocioException(MensagemContantes.MSG_ERR_RELATORIO_ALUNO_INVALIDO);
        }
        return idTimeAluno;
    }

    public List<Relatorio> listarRelatorios() {
        List<Relatorio> listRelatorio = null;

        try {
            listRelatorio = relatorioDAO.listarRelatorios();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listRelatorio;
    }


    public void aceitar(int idRelatorio) throws SQLException {
        Relatorio relatorio = new Relatorio();
        relatorio.setStatus(StatusRelatorio.VALIDO);
        relatorio.setIdRelatorio(idRelatorio);
        relatorioDAO.validar(relatorio);
    }

    public void recusar(int idRelatorio) throws SQLException {
        Relatorio relatorio = new Relatorio();

        relatorio.setIdRelatorio(idRelatorio);
        relatorio.setStatus(StatusRelatorio.INVALIDO);

        relatorioDAO.validar(relatorio);

    }
}
