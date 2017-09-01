package br.ages.crud.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.Time;
import br.ages.crud.model.TimePontoDTO;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;
import br.ages.crud.util.Util;
import org.mockito.cglib.core.Local;

public class TimePontoDTODAO {

	private UsuarioDAO usuarioDAO;
	private PontoBO pontoBO;
	private ProjetoBO projetoBO;
	private Usuario alunoTime;
	private TimeDAO timeDAO;

	public TimePontoDTODAO(){

	}

	
	public ArrayList<TimePontoDTO> listarTimes() throws PersistenciaException, SQLException{
		Connection conexao = null;
		ArrayList<TimePontoDTO> listaTimes = new ArrayList<TimePontoDTO>();
		projetoBO = new ProjetoBO();
		timeDAO = new TimeDAO();
		pontoBO = new PontoBO();
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_time, id_orientador, status_time, id_projeto, semestre, ano, dt_inclusao, primeiro_dia");
			sql.append(" from tb_time ");
			sql.append(" where  status_time = 'ativa' ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				TimePontoDTO time = new TimePontoDTO();
				time.setId(resultSet.getInt("id_time"));
				time.setStatus(resultSet.getString("status_time"));
				time.setSemestre(resultSet.getInt("semestre"));
				time.setAno(resultSet.getInt("ano"));				
				time.setOrientador(resultSet.getInt("id_orientador"));
				int proj = resultSet.getInt("id_projeto");
				time.setPrimeiraAula(resultSet.getDate("primeiro_dia").toLocalDate());
				time.setProjeto(projetoBO.buscarProjeto(proj));
				ArrayList<ResumoPonto> pontos = new ArrayList<ResumoPonto>();
				//time.setPontos(usuarioDAO.li(conexao, resultSet.getInt("id_time")));
				for (Usuario aluno : timeDAO.buscarAlunosTime(conexao, time.getId())) {
					ArrayList<ResumoPonto> temp = pontoBO.listaPontoAlunos(aluno.getIdUsuario(), Util.getDataInicialSemestre(time.getSemestre(), time.getAno()), Util.getDataFinalSemestre(time.getSemestre(), time.getAno()));
						if (temp.size() > 0)
							pontos.add(temp.get(0));
						else { //caso o aluno nao tenha ponto cadastrado :D
							ResumoPonto ponto = new ResumoPonto();
							ponto.setIdAluno(aluno.getIdUsuario());
							ponto.setNomeAluno(aluno.getNome());
							ponto.setHoraTotalDia(0);
							ponto.setHoraTotalDiaValido(0);
							ponto.setHoraTotalDiaInvalido(0);
							ponto.setIdPonto(0);
							pontos.add(ponto);
						}
				}
				time.setPontos(pontos);
								
				listaTimes.add(time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



		return listaTimes;
	}
}
