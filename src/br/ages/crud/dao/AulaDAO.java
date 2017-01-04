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
import br.ages.crud.model.Aula;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;

public class AulaDAO {
	private Aula aula;
	private Presenca presenca;
	private Turma turma;
	private UsuarioDAO usuarioDAO;
	private Usuario alunoTurma;
	
	public AulaDAO() {	
	}
	
	public boolean cadastrarDiasAulasTurma(Turma turma) throws SQLException, PersistenciaException {

		boolean ok = false;
		Connection conexao = null;
		ArrayList<Aula> aulas = turma.getAulas();
		try {
			Integer idAula = null;
			conexao = ConexaoUtil.getConexao();
	

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tb_aula (ID_TURMA, DT_INCLUSAO, OBSERVACAO, STATUS, DATA)");
			sql.append(" VALUES (?, ?, ?, ?, ?)");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			for (Aula aula : aulas) {
				
				java.sql.Date dataInclusao = new java.sql.Date(aula.getDtInclusao().getTime());
				java.sql.Date data = new java.sql.Date(aula.getData().getTime());
				
				statement.setInt(1, turma.getId());
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
				statement.setInt(2, presenca.getIdAluno());
				statement.setString(3, presenca.getMatriculaAluno());
				statement.setInt(4, presenca.getIdTurma());
				statement.setString(5, presenca.getStatus());

				ok = statement.execute();
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {				conexao.close();
		}
			return ok;
		}
	
	public ArrayList<Aula> listarDiasAulasTurma(Turma turma) throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Aula> aulas = new ArrayList<Aula>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_aula, data, status, observacao, dt_inclusao");
			sql.append(" from tb_aula ");
			sql.append(" where id_turma = ? ");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, turma.getId());
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Aula aula = new Aula();
				aula.setId(resultSet.getInt("id_aula"));
				Date data = resultSet.getDate("data");
				aula.setData(data);
				aula.setStatus(resultSet.getString("status"));
				aula.setObservacao(resultSet.getString("observacao"));
				Date dataInclusao = resultSet.getDate("dt_inclusao");
				turma.setDtInclusao(dataInclusao);
				aula.setIdTurma(turma.getId());
				aulas.add(aula);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aulas;
	}
	
	public ArrayList<Presenca> listarPresencasAula(Aula aula) throws PersistenciaException, SQLException {
		Connection conexao = null;
		ArrayList<Presenca> presencas = new ArrayList<Presenca>();

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append(" select id_aluno, matricula, id_turma, status");
			sql.append(" from tb_presenca ");
			sql.append(" where id_aula = ? ");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, aula.getId());
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Presenca presenca = new Presenca();
				presenca.setIdAluno(resultSet.getInt("id_aluno"));
				presenca.setMatriculaAluno(resultSet.getString("matricula"));
				presenca.setIdTurma(resultSet.getInt("id_turma"));
				presenca.setStatus(resultSet.getString("status"));
				presenca.setIdAula(aula.getId());
				presencas.add(presenca);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return presencas;
	}
	
}
