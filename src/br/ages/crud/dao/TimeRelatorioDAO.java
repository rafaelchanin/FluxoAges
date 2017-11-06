package br.ages.crud.dao;

import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Relatorio;
import br.ages.crud.model.TimeRelatorio;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeRelatorioDAO {

    private UsuarioDAO usuarioDAO;
    private RelatorioBO relatorioBO;
    private ProjetoBO projetoBO;
    private Usuario alunoTime;
    private TimeDAO timeDAO;

    public TimeRelatorioDAO() {
    }

    public ArrayList<TimeRelatorio> listarTimes() throws PersistenciaException, SQLException, NegocioException {
        Connection conexao = null;
        ArrayList<TimeRelatorio> listaTimes = new ArrayList<TimeRelatorio>();
        projetoBO = new ProjetoBO();
        timeDAO = new TimeDAO();
        relatorioBO = new RelatorioBO();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" select id_time, id_orientador, status_time, id_projeto, semestre, ano, dt_inclusao, primeiro_dia");
            sql.append(" from tb_time ");
            sql.append(" where status_time = 'ativa' ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TimeRelatorio time = new TimeRelatorio();
                time.setId(resultSet.getInt("id_time"));
                time.setStatus(resultSet.getString("status_time"));
                time.setSemestre(resultSet.getInt("semestre"));
                time.setAno(resultSet.getInt("ano"));
                time.setOrientador(resultSet.getInt("id_orientador"));
                int proj = resultSet.getInt("id_projeto");
                time.setProjeto(projetoBO.buscarProjeto(proj));
                ArrayList<Relatorio> relatorios = new ArrayList<Relatorio>();
                for (Usuario aluno : timeDAO.buscarAlunosTime(conexao, time.getId())) {
                    List<Relatorio> temp = relatorioBO.listarRelatoriosCoord(aluno.getIdUsuario(),aluno.getNome());
                    if (temp.size() > 0)
                        relatorios.addAll(temp);
                }

                    time.setRelatorio(relatorios);
                    listaTimes.add(time);
            }


        } catch(Exception e) {
            e.printStackTrace();
        }



        return listaTimes;
    }
}
