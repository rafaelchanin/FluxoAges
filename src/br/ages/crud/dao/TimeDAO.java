package br.ages.crud.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Time;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;

public class TimeDAO {
	
	private UsuarioDAO usuarioDAO;
	private Usuario alunoTime;
	
	public TimeDAO(){
		
	}
	public boolean cadastrarTime(Time time) throws PersistenciaException, SQLException, ParseException {
		// TODO uih
		return false;
	}
	private boolean inserirAlunosTime(){
		boolean ok = false;
		//TODO ddd
		return ok;
	}
	public void editarTime(Time time) throws PersistenciaException, SQLException, ParseException{
		// TODO 
		
	}
	
	public Time buscaTime(int idTime){
		Connection conexao = null;
		Time time = new Time();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_time, id_orientador, status_time, id_projeto, semestre, ano, dt_inclusao");
			sql.append(" from tb_time ");
			sql.append(" where  id_time = ? ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idTime);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) { //precisa de while, supostamente só existe um time/turma com um id
				time.setId(resultSet.getInt("id_turma"));
				time.setStatus(resultSet.getString("status_turma"));
				time.setSemestre(resultSet.getInt("semestre"));
				time.setAno(resultSet.getInt("ano"));				
				time.setDtInclusao(resultSet.getDate("dt_inclusao"));
				time.setOrientador(resultSet.getInt("id_orientador"));
				time.setProjeto(resultSet.getInt("id_projeto"));
				time.setAlunos(buscarAlunosTime(conexao, resultSet.getInt("id_time")));
			}
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return time;
	}
	public boolean removerAlunosTime(Connection conexao, Time time) throws SQLException{
		boolean ok = false;

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tb_time_aluno WHERE ID_TIME = ?");

		PreparedStatement statement = conexao.prepareStatement(sql.toString());
		statement.setInt(1, time.getId());
		ok = statement.execute();

		return ok;
		
	}
	public ArrayList<Time> listarTimes() throws PersistenciaException, SQLException{
		Connection conexao = null;
		ArrayList<Time> listaTimes = new ArrayList<Time>();
		
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_time, id_orientador, status_time, id_projeto, semestre, ano, dt_inclusao");
			sql.append(" from tb_time ");
			sql.append(" where  status_turma <> 'excluido' ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Time time = new Time();
				time.setId(resultSet.getInt("id_turma"));
				time.setStatus(resultSet.getString("status_turma"));
				time.setSemestre(resultSet.getInt("semestre"));
				time.setAno(resultSet.getInt("ano"));				
				time.setDtInclusao(resultSet.getDate("dt_inclusao"));
				time.setOrientador(resultSet.getInt("id_orientador"));
				time.setProjeto(resultSet.getInt("id_projeto"));
				time.setAlunos(buscarAlunosTime(conexao, resultSet.getInt("id_time")));
				
				listaTimes.add(time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		return listaTimes;
	}
	
	private ArrayList<Usuario> buscarAlunosTime(Connection conexao, int idTime) throws PersistenciaException, SQLException {

		List<Usuario> alunosTime = new ArrayList<Usuario>();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID_USUARIO, MATRICULA ");
			sql.append(" FROM tb_time_aluno");
			sql.append(" WHERE ID_TIME = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idTime);

			ResultSet resultset = statement.executeQuery();
			usuarioDAO = new UsuarioDAO();
			alunoTime = new Usuario();

			while (resultset.next()) {
				alunoTime = usuarioDAO.buscaUsuarioId(resultset.getInt(1));
				alunosTime.add(alunoTime);
			}

			Collections.sort(alunosTime);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (ArrayList<Usuario>) alunosTime;

	}

}
