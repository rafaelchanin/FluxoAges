package br.ages.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;

public class TurmaDAO {
	private Turma turma;
	private UsuarioDAO usuarioDAO;
	private Usuario alunoTurma;
	
	public TurmaDAO() {	
	}
	
	public boolean cadastrarTurma(Turma turma) throws PersistenciaException, SQLException, ParseException {
		boolean ok = false;
		Connection conexao = null;
		try {
			Integer idTurma = null;

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_turma (NUMERO, STATUS_TURMA, AGES, SEMESTRE, ANO, DT_INCLUSAO)");
			sql.append("VALUES (?, ?, ?, ?, ?, ?)");

			java.sql.Date dataInclusao = new java.sql.Date(turma.getDtInclusao().getTime());

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, turma.getNumero());
			statement.setString(2, turma.getStatus());
			statement.setInt(3, turma.getAges());
			statement.setInt(4, turma.getSemestre());
			statement.setInt(5, turma.getAno());
			statement.setDate(6, dataInclusao);

			statement.executeUpdate();

			ResultSet resultset = statement.getGeneratedKeys();
			if (resultset.first()) {
				idTurma = resultset.getInt(1);
				turma.setId(idTurma.intValue());
				ok=true;
			}
			
			if (turma.getAlunos() != null) 
				inserirAlunosTurma(conexao, turma);

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
		return ok;
	}
	
	private boolean inserirAlunosTurma(Connection conexao, Turma turma) throws SQLException {

		boolean ok = false;

		ArrayList<Usuario> listaAlunos = new ArrayList<>(turma.getAlunos());

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tb_turma_aluno (ID_ALUNO, MATRICULA, ID_TURMA)");
		sql.append(" VALUES (?, ?, ?)");

		PreparedStatement statement = conexao.prepareStatement(sql.toString());

		for (Usuario usuario : listaAlunos) {

			statement.setInt(1, usuario.getIdUsuario());
			statement.setString(2, usuario.getMatricula());
			statement.setInt(3, turma.getId());

			ok = statement.execute();

		}

		return ok;
	}
	
	public ArrayList<Turma> listarTurmas() throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Turma> listaTurmas = new ArrayList<Turma>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_turma, numero, status_turma, ages, semestre, ano, dt_inclusao");
			sql.append(" from tb_turma ");
			sql.append(" where  status_turma <> 'excluido' ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Turma turma = new Turma();
				turma.setId(resultSet.getInt("id_turma"));
				turma.setNumero(resultSet.getInt("numero"));
				turma.setStatus(resultSet.getString("status_turma"));
				turma.setAges(resultSet.getInt("ages"));
				turma.setSemestre(resultSet.getInt("semestre"));
				turma.setAno(resultSet.getInt("ano"));

				Date dataInclusao = resultSet.getDate("dt_inclusao");
				turma.setDtInclusao(dataInclusao);

				turma.setAlunos(buscarAlunosTurma(conexao, resultSet.getInt("id_turma")));
				
				listaTurmas.add(turma);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaTurmas;
	}
	
	public ArrayList<Turma> listarTurmasAtivas() throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Turma> listaTurmas = new ArrayList<Turma>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_turma, numero, status_turma, ages, semestre, ano, dt_inclusao");
			sql.append(" from tb_turma ");
			sql.append(" where  status_turma = 'ATIVA' ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Turma turma = new Turma();
				turma.setId(resultSet.getInt("id_turma"));
				turma.setNumero(resultSet.getInt("numero"));
				turma.setStatus(resultSet.getString("status_turma"));
				turma.setAges(resultSet.getInt("ages"));
				turma.setSemestre(resultSet.getInt("semestre"));
				turma.setAno(resultSet.getInt("ano"));

				Date dataInclusao = resultSet.getDate("dt_inclusao");
				turma.setDtInclusao(dataInclusao);

				turma.setAlunos(buscarAlunosTurma(conexao, resultSet.getInt("id_turma")));
				
				listaTurmas.add(turma);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaTurmas;
	}
	
	private ArrayList<Usuario> buscarAlunosTurma(Connection conexao, int idTurma) throws PersistenciaException, SQLException {

		List<Usuario> alunosTurma = new ArrayList<Usuario>();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID_ALUNO, MATRICULA ");
			sql.append(" FROM tb_turma_aluno");
			sql.append(" WHERE ID_TURMA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idTurma);

			ResultSet resultset = statement.executeQuery();
			int idAluno = 0;
			usuarioDAO = new UsuarioDAO();
			alunoTurma = new Usuario();

			while (resultset.next()) {
				idAluno = resultset.getInt(1);
				alunoTurma = usuarioDAO.buscaUsuarioId(idAluno);
				alunosTurma.add(alunoTurma);
			}

			Collections.sort(alunosTurma);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (ArrayList<Usuario>) alunosTurma;

	}
	
	
	public void editaTurma(Turma turma) throws PersistenciaException, SQLException, ParseException {
		Connection conexao = null;
		try {

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_turma SET NUMERO = ?, STATUS_TURMA = ?, AGES = ?, "
					+ "SEMESTRE = ?, ANO = ? WHERE ID_TURMA = ?;");
/*
			java.sql.Date dataInicio = new java.sql.Date(projeto.getDataInicio().getTime());

			java.sql.Date dataFim = null;
			if(projeto.getDataFim() != null) dataFim = new java.sql.Date(projeto.getDataFim().getTime());

			java.sql.Date dataFimPrevisto = new java.sql.Date(projeto.getDataFimPrevisto().getTime());
*/
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, turma.getNumero());
			statement.setString(2, turma.getStatus());
			statement.setInt(3, turma.getAges());
			statement.setInt(4, turma.getSemestre());
			statement.setInt(5, turma.getAno());
			statement.setInt(6, turma.getId());

			statement.executeUpdate();
			
			removerAlunosTurma(conexao, turma);
			if (turma.getAlunos() != null)
				inserirAlunosTurma(conexao, turma);

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
	}
	
	private boolean removerAlunosTurma(Connection conexao, Turma turma) throws SQLException {
		boolean ok = false;

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tb_turma_aluno WHERE ID_TURMA = ?");

		PreparedStatement statement = conexao.prepareStatement(sql.toString());
		statement.setInt(1, turma.getId());
		ok = statement.execute();

		return ok;
	}

	public Turma buscaTurma(int idTurma) {
		Connection conexao = null;


		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_turma, numero, status_turma, ages, semestre, ano, dt_inclusao");
			sql.append(" from tb_turma ");
			sql.append(" where  id_turma = ? ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idTurma);
			ResultSet resultSet = statement.executeQuery();
			Turma turma = new Turma();
			while (resultSet.next()) {
				turma.setId(resultSet.getInt("id_turma"));
				turma.setNumero(resultSet.getInt("numero"));
				turma.setStatus(resultSet.getString("status_turma"));
				turma.setAges(resultSet.getInt("ages"));
				turma.setSemestre(resultSet.getInt("semestre"));
				turma.setAno(resultSet.getInt("ano"));

				Date dataInclusao = resultSet.getDate("dt_inclusao");
				turma.setDtInclusao(dataInclusao);

				turma.setAlunos(buscarAlunosTurma(conexao, resultSet.getInt("id_turma")));
				
				
			}
			return turma;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return turma;
	}
}
