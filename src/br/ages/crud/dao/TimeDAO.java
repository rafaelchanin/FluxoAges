package br.ages.crud.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Time;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;

public class TimeDAO {

	private UsuarioDAO usuarioDAO;
	private Usuario alunoTime;

	public TimeDAO(){

	}

	public boolean cadastrarTime(Time time) throws PersistenciaException, SQLException, ParseException {
		boolean ok = false;
		Connection conexao = null;
		try {
			Integer idTime = null;


			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_time (id_orientador, status_time, id_projeto, semestre, ano, dt_inclusao, primeiro_dia)");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?)");

			java.sql.Date dataInclusao = new java.sql.Date(time.getDtInclusao().getTime());
			java.sql.Date sqlDate = new java.sql.Date(time.getPrimeiroDia().getTime());

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, time.getOrientador());
			statement.setString(2, time.getStatus());
			statement.setInt(3, time.getProjeto());
			statement.setInt(4, time.getSemestre());
			statement.setInt(5, time.getAno());
			statement.setDate(6, dataInclusao);
			statement.setDate(7, sqlDate);

			statement.executeUpdate();

			ResultSet resultset = statement.getGeneratedKeys();
			if (resultset.first()) {
				idTime = resultset.getInt(1);
				time.setId(idTime.intValue());
				ok=true;
			}

			if (time.getAlunos() != null) 
				inserirAlunosTime(conexao, time);

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
		return ok;
	}
	private boolean inserirAlunosTime(Connection conexao, Time time) throws SQLException{
		boolean ok = false;
		ArrayList<Usuario> listaAlunos = new ArrayList<>(time.getAlunos());

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tb_time_aluno (ID_ALUNO, MATRICULA, ID_TIME)");
		sql.append(" VALUES (?, ?, ?)");

		PreparedStatement statement = conexao.prepareStatement(sql.toString());

		for (Usuario usuario : listaAlunos) {

			statement.setInt(1, usuario.getIdUsuario());
			statement.setString(2, usuario.getMatricula());
			statement.setInt(3, time.getId());

			ok = statement.execute();

		}
		return ok;
	}

	public void editarTime(Time time) throws PersistenciaException, SQLException, ParseException{
		Connection conexao = null;
		try {

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_time SET ID_ORIENTADOR = ?, STATUS_TIME = ?, ID_PROJETO = ?, "
					+ "SEMESTRE = ?, ANO = ? WHERE ID_TIME = ?;");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, time.getOrientador());
			statement.setString(2, time.getStatus());
			statement.setInt(3, time.getProjeto());
			statement.setInt(4, time.getSemestre());
			statement.setInt(5, time.getAno());
			statement.setInt(6, time.getId());

			statement.executeUpdate();

			removerAlunosTime(conexao, time);
			if (time.getAlunos() != null)
				inserirAlunosTime(conexao, time);

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
	}

	public void removerTime(Time time) throws PersistenciaException {
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			//TODO excluido ou "acabou o semstre"/desativado?
			sql.append("UPDATE tb_time SET STATUS_TIME = 'EXCLUIDO'  WHERE ID_TIME = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, time.getId());

			statement.execute();

			//removerUsuariosTime(conexao, projeto);

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
				time.setId(resultSet.getInt("id_time"));
				time.setStatus(resultSet.getString("status_time"));
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
			sql.append(" where  status_time <> 'excluido' ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Time time = new Time();
				time.setId(resultSet.getInt("id_time"));
				time.setStatus(resultSet.getString("status_time"));
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

	public ArrayList<Usuario> buscarAlunosTime(Connection conexao, int idTime) throws PersistenciaException, SQLException {

		List<Usuario> alunosTime = new ArrayList<Usuario>();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID_ALUNO, MATRICULA ");
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
