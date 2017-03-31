package br.ages.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Aula;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;
import br.ages.crud.util.Util;

public class AulaDAO {
	private Aula aula;
	private Presenca presenca;
	private Turma turma;
	private UsuarioDAO usuarioDAO;
	private Usuario alunoTurma;
	
	public AulaDAO() {	
	}
	
	public LocalDate primeiroDia(int semestre, int ano) throws PersistenciaException, SQLException {
		Connection conexao = null;
		java.sql.Date data = null;
		try {
			conexao = ConexaoUtil.getConexao();
			LocalDate dataInicioSemestre = Util.getDataInicialSemestre(semestre, ano).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;

			java.sql.Date dataInicio = java.sql.Date.valueOf(dataInicioSemestre);
			

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT min(DATA) date ");
			sql.append(" FROM tb_aula");
			sql.append(" WHERE DATA > ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setDate(1, dataInicio);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				data = resultset.getDate("date");
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return data.toLocalDate();

	}


	
	public boolean cadastrarDiasAulas(List<Aula> aulas) throws SQLException, PersistenciaException {

		boolean ok = false;
		Connection conexao = null;
		//ArrayList<Aula> aulas = turma.getAulas();
		try {
			Integer idAula = null;
			conexao = ConexaoUtil.getConexao();
			
			//removerAulasTurma(conexao, turma.getId());
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_aula (ID_TURMA, DT_INCLUSAO, OBSERVACAO, STATUS, DATA)");
			sql.append(" VALUES (?, ?, ?, ?, ?)");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			for (Aula aula : aulas) {

				java.sql.Date dataInclusao = new java.sql.Date(aula.getDtInclusao().getTime());
				java.sql.Date data = new java.sql.Date(aula.getData().getTime());

				statement.setInt(1, aula.getIdTurma());
				statement.setDate(2, dataInclusao);
				statement.setString(3, aula.getObservacao());
				statement.setString(4, aula.getStatus());
				statement.setDate(5, data);

				ok = statement.execute();

				ResultSet resultset = statement.getGeneratedKeys();
				if (resultset.first()) {
					idAula = resultset.getInt(1);
					aula.setId(idAula.intValue());
					ok=true;
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {				
			conexao.close();
		}
			return ok;
		}

	public boolean removerDiasAulas(List<Aula> aulas) throws SQLException {

		boolean ok = false;
		Connection conexao = null;
		
		try {
			conexao = ConexaoUtil.getConexao();
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM tb_aula WHERE ID_AULA = ? AND ID_TURMA = ? ");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			for (Aula aula : aulas) {
				statement.setInt(1, aula.getId());
				statement.setInt(2, aula.getIdTurma());
				ok = statement.execute();
				
			}
			ok=true;
		} catch (ClassNotFoundException | SQLException e) {
			
		} finally {				conexao.close();
		}
			return ok;
		}
	
	public boolean cadastrarPresencasAula(Aula aula) throws SQLException, PersistenciaException {

		boolean ok = false;
		Connection conexao = null;
		ArrayList<Presenca> presencas = aula.getPresencas();
		try {
			conexao = ConexaoUtil.getConexao();
	

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_presenca (ID_AULA, ID_ALUNO, MATRICULA, ID_TURMA, STATUS)");
			sql.append(" VALUES (?, ?, ?, ?, ?)");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			for (Presenca presenca : presencas) {
				
				
				
				statement.setInt(1, aula.getId());
				statement.setInt(2, presenca.getAluno().getIdUsuario());
				statement.setString(3, presenca.getAluno().getMatricula());
				statement.setInt(4, presenca.getIdTurma());
				statement.setString(5, presenca.getStatus());

				ok = statement.execute();
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {				
			conexao.close();
		}
			return ok;
		}
	
	public boolean cadastrarPresencas(List<Presenca> presencas) throws SQLException, PersistenciaException {

		boolean ok = false;
		Connection conexao = null;
		//ArrayList<Presenca> presencas = aula.getPresencas();
		try {
			conexao = ConexaoUtil.getConexao();
	

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_presenca (ID_AULA, ID_ALUNO, MATRICULA, ID_TURMA, STATUS)");
			sql.append(" VALUES (?, ?, ?, ?, ?)");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			for (Presenca presenca : presencas) {
				
				
				statement.setInt(1, presenca.getIdAula());
				statement.setInt(2, presenca.getAluno().getIdUsuario());
				statement.setString(3, presenca.getAluno().getMatricula());
				statement.setInt(4, presenca.getIdTurma());
				statement.setString(5, presenca.getStatus());

				ok = statement.execute();
				
			}
			ok=true;
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {				
			conexao.close();
		}
			return ok;
		}
	
	public boolean excluirPresencas(List<Presenca> presencas) throws SQLException, PersistenciaException {

		boolean ok = false;
		Connection conexao = null;
		//ArrayList<Presenca> presencas = aula.getPresencas();
		try {
			conexao = ConexaoUtil.getConexao();
	

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM tb_presenca WHERE ID_AULA = ? AND ID_ALUNO = ? AND MATRICULA = ? AND ID_TURMA = ? AND STATUS = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			for (Presenca presenca : presencas) {
				statement.setInt(1, presenca.getIdAula());
				statement.setInt(2, presenca.getAluno().getIdUsuario());
				statement.setString(3, presenca.getAluno().getMatricula());
				statement.setInt(4, presenca.getIdTurma());
				statement.setString(5, presenca.getStatus());

				ok = statement.execute();
				
			}
			ok=true;
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {				conexao.close();
		}
			return ok;
		}
	
	public ArrayList<Aula> listarDiasAulasTurma(int idTurma) throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Aula> aulas = new ArrayList<Aula>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_aula, data, status, observacao, dt_inclusao");
			sql.append(" from tb_aula ");
			sql.append(" where id_turma = ? ");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idTurma);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Aula aula = new Aula();
				aula.setId(resultSet.getInt("id_aula"));
				Date data = resultSet.getDate("data");
				aula.setData(data);
				aula.setStatus(resultSet.getString("status"));
				aula.setObservacao(resultSet.getString("observacao"));
				Date dataInclusao = resultSet.getDate("dt_inclusao");
				aula.setDtInclusao(dataInclusao);
				aula.setIdTurma(idTurma);
				aulas.add(aula);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}

		return aulas;
	}
	
	public ArrayList<Presenca> listarPresencasAula(int idAula) throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Presenca> presencas = new ArrayList<Presenca>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_aluno, matricula, id_turma, status");
			sql.append(" from tb_presenca ");
			sql.append(" where id_aula = ? ");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idAula);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Presenca presenca = new Presenca();
				Usuario aluno = new Usuario();
				aluno.setIdUsuario(resultSet.getInt("id_aluno"));
				aluno.setMatricula(resultSet.getString("matricula"));
				presenca.setAluno(aluno);
				presenca.setIdTurma(resultSet.getInt("id_turma"));
				presenca.setStatus(resultSet.getString("status"));
				presenca.setIdAula(idAula);
				presencas.add(presenca);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}

		return presencas;
	}
	

	public ArrayList<Presenca> listarPresencasTurma(int idTurma) throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Presenca> presencas = new ArrayList<Presenca>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_aluno, matricula, id_turma, status, id_aula");
			sql.append(" from tb_presenca ");
			sql.append(" where id_turma = ? ");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idTurma);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Presenca presenca = new Presenca();
				Usuario aluno = new Usuario();
				aluno.setIdUsuario(resultSet.getInt("id_aluno"));
				aluno.setMatricula(resultSet.getString("matricula"));
				presenca.setAluno(aluno);
				presenca.setIdTurma(idTurma);
				presenca.setStatus(resultSet.getString("status"));
				presenca.setIdAula(resultSet.getInt("id_aula"));
				presencas.add(presenca);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}

		return presencas;
	}
	
	public ArrayList<Presenca> listarPresencasTurmaMes(int idTurma, int mes) throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Presenca> presencas = new ArrayList<Presenca>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_aluno, matricula, id_turma, status, id_aula");
			sql.append(" from tb_presenca ");
			sql.append(" where id_turma = ? AND ID_AULA IN");
			sql.append(" (select id_aula");
			sql.append(" from tb_aula");
			sql.append(" where MONTH(data) = ?)");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idTurma);
			statement.setInt(2, mes);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Presenca presenca = new Presenca();
				Usuario aluno = new Usuario();
				aluno.setIdUsuario(resultSet.getInt("id_aluno"));
				aluno.setMatricula(resultSet.getString("matricula"));
				presenca.setAluno(aluno);
				presenca.setIdTurma(idTurma);
				presenca.setStatus(resultSet.getString("status"));
				presenca.setIdAula(resultSet.getInt("id_aula"));
				presencas.add(presenca);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			conexao.close();
		}

		return presencas;
	}


public ArrayList<Presenca> listarPresencasAlunoTurma(int idAluno, int idTurma) throws PersistenciaException, SQLException {
	Connection conexao = null;
	ArrayList<Presenca> presencas = new ArrayList<Presenca>();

	try {
		conexao = ConexaoUtil.getConexao();

		StringBuilder sql = new StringBuilder();
		sql.append(" select id_aluno, matricula, id_turma, status, id_aula");
		sql.append(" from tb_presenca ");
		sql.append(" where id_aluno = ? and id_turma = ? and status = 'PRESENTE' ");
		
		PreparedStatement statement = conexao.prepareStatement(sql.toString());
		statement.setInt(1, idAluno);
		statement.setInt(2, idTurma);
		
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Presenca presenca = new Presenca();
			Usuario aluno = new Usuario();
			aluno.setIdUsuario(idAluno);
			aluno.setMatricula(resultSet.getString("matricula"));
			presenca.setAluno(aluno);
			presenca.setIdTurma(idTurma);
			presenca.setStatus(resultSet.getString("status"));
			presenca.setIdAula(resultSet.getInt("id_aula"));
			presencas.add(presenca);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		conexao.close();
	}

	return presencas;
}

}