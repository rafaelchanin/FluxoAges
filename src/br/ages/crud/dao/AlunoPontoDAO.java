package br.ages.crud.dao;

import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.model.AlunoPonto;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;
import br.ages.crud.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlunoPontoDAO {

    private UsuarioDAO usuarioDAO;
    private PontoBO pontoBO;
    private ProjetoBO projetoBO;
    private Usuario alunoTime;
    private TimeDAO timeDAO;

    public AlunoPontoDAO(){

    }

    public ArrayList<AlunoPonto> listarPonto(int idAluno) throws SQLException {
        Connection conexao = null;
        ArrayList<AlunoPonto> pontoAluno = new ArrayList<AlunoPonto>();
        projetoBO = new ProjetoBO();
        timeDAO = new TimeDAO();
        pontoBO = new PontoBO();
        usuarioDAO = new UsuarioDAO();
        try{
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" select tb1.id_time, tb1.id_projeto, tb1.id_orientador, tb1.status_time, tb1.semestre, tb1.ano, tb1.dt_inclusao, tb1.primeiro_dia");
            sql.append(" from (tb_time tb1 inner join tb_time_aluno tb2 on tb1.ID_TIME = tb2.ID_TIME) ");
            sql.append(" where tb2.ID_ALUNO = ? and tb1.status_time = 'ativa' ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setInt(1,idAluno);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AlunoPonto aluno = new AlunoPonto();
                aluno.setProjeto(projetoBO.buscarProjeto(resultSet.getInt("tb1.id_projeto")));
                ArrayList<ResumoPonto> pontos = new ArrayList<ResumoPonto>();
                ArrayList<ResumoPonto> temp = pontoBO.listaPontoAlunos(idAluno, Util.getDataInicialSemestre(resultSet.getInt("tb1.semestre"), resultSet.getInt("tb1.ano")), Util.getDataFinalSemestre(resultSet.getInt("tb1.semestre"), resultSet.getInt("tb1.ano")));
                if (temp.size() > 0)
                    pontos.add(temp.get(0));
                else { //caso o aluno nao tenha ponto cadastrado :D
                    ResumoPonto ponto = new ResumoPonto();
                    Usuario al = new Usuario();
                    ponto.setIdAluno(idAluno);
                    al = usuarioDAO.buscaUsuarioId(idAluno);
                    ponto.setNomeAluno(al.getNome());
                    ponto.setHoraTotalDia(0);
                    ponto.setHoraTotalDiaValido(0);
                    ponto.setHoraTotalDiaInvalido(0);
                    ponto.setIdPonto(0);
                    pontos.add(ponto);
                    }
                aluno.setPontos(pontos);
                aluno.setPrimeiraAula(resultSet.getDate("tb1.primeiro_dia").toLocalDate());
                aluno.setId(0);
                aluno.setAno(resultSet.getInt("tb1.ano"));
                aluno.setSemestre(resultSet.getInt("tb1.semestre"));
                pontoAluno.add(aluno);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return pontoAluno;
    }
}
