package br.ages.crud.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Ponto;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.StatusPonto;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;
import br.ages.crud.util.MensagemContantes;

public class PontoDAO {

	public Integer cadastrarPonto(Ponto ponto) throws NegocioException, SQLException, PersistenciaException {
		Connection conexao = null;
		try {
			Integer idPonto = null;

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("insert into tb_ponto (data_entrada, data_saida, id_usuario_aluno, id_usuario_responsavel, status_ponto)");
			sql.append("values (?, ?, ?, ?, ?);");

			// Cadastra a pessoa e gera e busca id gerado
			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			// converte a data para data Juliana, data que o banco reconhece;
			java.sql.Timestamp dataEntrada = new java.sql.Timestamp(ponto.getDataEntrada().getTime());
			statement.setTimestamp(1, dataEntrada);

			java.sql.Timestamp dataSaida = ponto.getDataSaida() == null ? null : new java.sql.Timestamp(ponto.getDataSaida().getTime());
			statement.setTimestamp(2, dataSaida);
			
			statement.setInt(3, ponto.getAluno().getIdUsuario());
			statement.setInt(4, ponto.getResponsavel().getIdUsuario());
			statement.setString(5, String.valueOf(ponto.getStatus()));

			statement.executeUpdate();

			ResultSet resultset = statement.getGeneratedKeys();
			if (resultset.first()) {
				idPonto = resultset.getInt(1);

			}

			System.out.println(ponto);

			return idPonto;

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
			throw new PersistenciaException(MensagemContantes.MSG_ERR_PONTO_JA_EXISTENTE.replace("?", ponto.getAluno().getNome()));

		} finally {
			conexao.close();
		}

	}

	
	public boolean removePonto(int idPonto) throws NegocioException, SQLException, PersistenciaException {
		Connection conexao = null;
		boolean ok = false;
		try {

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM tb_ponto ");
			sql.append("WHERE id_ponto = ?;");
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idPonto);
			ok = statement.execute();
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		} finally {
			conexao.close();
			return ok;
		}
		
		
	}

	
	
	public ArrayList<ResumoPonto> listaPontoAlunos(int idUsuario, Date dataEntrada, Date dataSaida) throws SQLException {
		ArrayList<ResumoPonto> listaPontos = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("select u.nome, p.status_ponto, timestampdiff(minute,p.data_entrada,p.data_saida) minutoTotal "); //tirei o sum
			sql.append("FROM tb_ponto p, tb_usuario u ");
			sql.append("where p.id_usuario_aluno = u.id_usuario ");
			sql.append("and p.data_saida is not null ");
			sql.append("and p.data_entrada between ? and ? and p.data_saida between ? and ? "); //sql.append("and p.data_entrada between(?, ?) and p.data_saida between(?, ?) "); 
					
			PreparedStatement statement;
			
			if (idUsuario == 0) {
				// sql.append(" and p.id_usuario_aluno = ?; ");
				statement = conexao.prepareStatement(sql.toString());
				// statement.setInt(1, -1);
			} else {
				sql.append("and p.id_usuario_aluno = ? ");
				statement = conexao.prepareStatement(sql.toString());
				statement.setInt(5, idUsuario);
			}
			
			java.sql.Timestamp dataEntradaSql = new java.sql.Timestamp(dataEntrada.getTime());
			statement.setTimestamp(1, dataEntradaSql);
			
			java.sql.Timestamp dataSaidaSql = new java.sql.Timestamp(dataSaida.getTime());
			statement.setTimestamp(2, dataSaidaSql);
			
			statement.setTimestamp(3, dataEntradaSql);

			statement.setTimestamp(4, dataSaidaSql);

			sql.append("group by  u.nome, p.status_ponto ");
			sql.append("order by u.nome, reverse(status_ponto);");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String nome = resultSet.getString("u.nome");
				ResumoPonto jaExiste = null;
				int minutoTotal = resultSet.getInt("minutoTotal");
				String status_ponto = resultSet.getString("p.status_ponto");
				
				for (ResumoPonto resumoPontoDoAluno : listaPontos) {
					if (nome.equals(resumoPontoDoAluno.getNomeAluno())) {
						jaExiste = resumoPontoDoAluno;
						break;
					}
				}
				
				if(jaExiste == null){
					ResumoPonto ponto = new ResumoPonto();
					
					if(status_ponto.equals("VALIDO")) ponto.setHoraTotalDiaValido(minutoTotal);
					else ponto.setHoraTotalDiaInvalido(minutoTotal);
						
					ponto.setNomeAluno(nome);
					ponto.setHoraTotalDia(ponto.getHoraTotalDiaInvalido() + ponto.getHoraTotalDiaValido());
					listaPontos.add(ponto);
				}
				else{
					
					if(status_ponto.equals("VALIDO")) jaExiste.setHoraTotalDiaValido(jaExiste.getHoraTotalDiaValido() + minutoTotal); // 
					else jaExiste.setHoraTotalDiaInvalido(jaExiste.getHoraTotalDiaInvalido() + minutoTotal);
					jaExiste.setHoraTotalDia(jaExiste.getHoraTotalDiaInvalido() + jaExiste.getHoraTotalDiaValido());
				}
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		} finally {
			conexao.close();
		}

		return listaPontos;
	}
	
	public ArrayList<ResumoPonto> listaPontoInvalidoAlunos(int idUsuario) throws SQLException {
		ArrayList<ResumoPonto> listaPontos = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("select p.id_ponto, u.nome, p.data_entrada, timestampdiff(minute,p.data_entrada,p.data_saida)  horas ");
			sql.append("FROM tb_ponto p, tb_usuario u ");
			sql.append("where p.id_usuario_aluno = u.id_usuario ");
			sql.append("and p.status_ponto ='" + StatusPonto.INVALIDO + "'");

			PreparedStatement statement;
			if (idUsuario == 0) {
				// sql.append(" and p.id_usuario_aluno = ?; ");
				statement = conexao.prepareStatement(sql.toString());
				// statement.setInt(1, -1);
			} else {
				sql.append(" and p.id_usuario_aluno = ?; ");
				statement = conexao.prepareStatement(sql.toString());
				statement.setInt(1, idUsuario);
			}

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ResumoPonto ponto = new ResumoPonto();
				ponto.setIdPonto(resultSet.getInt("ID_PONTO"));
				ponto.setNomeAluno(resultSet.getString("NOME"));
				ponto.setDataEtrada(resultSet.getDate("DATA_ENTRADA"));
				ponto.setHoraTotalDia(resultSet.getInt("HORAS"));
				// " + horaTotal);

				listaPontos.add(ponto);
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		} finally {
			conexao.close();
		}

		return listaPontos;
	}

	public ArrayList<ResumoPonto> listaPontoInvalidoAlunos() throws SQLException {
		ArrayList<ResumoPonto> listaPontos = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("select p.id_ponto, u.nome, p.data_entrada, timediff(p.hora_saida,p.hora_entrada) horas ");
			sql.append("FROM tb_ponto p, tb_usuario u ");
			sql.append("where p.id_usuario_aluno = u.id_usuario ");
			sql.append("and p.status_ponto ='" + StatusPonto.INVALIDO + "'");

			PreparedStatement statement;

			statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ResumoPonto ponto = new ResumoPonto();
				ponto.setIdPonto(resultSet.getInt("ID_PONTO"));
				ponto.setNomeAluno(resultSet.getString("NOME"));
				ponto.setDataEtrada(resultSet.getDate("DATA_ENTRADA"));
				ponto.setHoraTotalDia(resultSet.getInt("HORAS"));

				listaPontos.add(ponto);
			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		} finally {
			conexao.close();
		}

		return listaPontos;
	}

	public Ponto buscaPontoId(int idPonto) throws PersistenciaException, SQLException {

		Ponto ponto = new Ponto();

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		try (Connection conexao = ConexaoUtil.getConexao()) {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("ID_PONTO, ");
			sql.append("DATA_ENTRADA, ");
			sql.append("DATA_SAIDA, ");
			sql.append("ID_USUARIO_ALUNO, ");
			sql.append("ID_USUARIO_RESPONSAVEL, ");
			sql.append("STATUS_PONTO ");
			sql.append("FROM tb_ponto ");
			sql.append("WHERE ID_PONTO = ?");
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idPonto);
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				ponto.setIdPonto(resultset.getInt("ID_PONTO"));
				ponto.setDataEntrada(resultset.getTimestamp("DATA_ENTRADA"));
				ponto.setDataSaida(resultset.getTimestamp("DATA_SAIDA"));

				Usuario usuarioAluno = usuarioDAO.buscaUsuarioId(resultset.getInt("ID_USUARIO_ALUNO"));
				ponto.setAluno(usuarioAluno);
				
				Usuario usuarioResponsavel = usuarioDAO.buscaUsuarioId(resultset.getInt("ID_USUARIO_RESPONSAVEL"));
				ponto.setResponsavel(usuarioResponsavel);

				String status = resultset.getString("STATUS_PONTO");
				StatusPonto statusPonto = StatusPonto.valueOf(status);
				ponto.setStatus(statusPonto);
			}

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		}

		return ponto;

	}

	public boolean editaPonto(Ponto ponto) throws NegocioException, SQLException, PersistenciaException {
		boolean ok = false;
		Connection conexao = null;
		try {
			int idPonto = ponto.getIdPonto();
			conexao = ConexaoUtil.getConexao();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE tb_ponto SET ");
			sql.append("DATA_ENTRADA = ?, ");
			//sql.append("HORA_ENTRADA = ?, ");
			sql.append("DATA_SAIDA = ?, ");
			//sql.append("HORA_SAIDA = ?, ");
			sql.append("ID_USUARIO_ALUNO = ?, ");
			sql.append("ID_USUARIO_RESPONSAVEL = ?, ");
			sql.append("STATUS_PONTO = ? ");
			sql.append("WHERE ID_PONTO = ?;");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			java.sql.Timestamp dataEntrada = new java.sql.Timestamp(ponto.getDataEntrada().getTime());
			statement.setTimestamp(1, dataEntrada);

			//java.sql.Time horaEntrada = new java.sql.Time(ponto.getDataEntrada().getTime());
			//statement.setTime(2, horaEntrada);

			java.sql.Timestamp dataSaida = ponto.getDataSaida() == null ? null : new java.sql.Timestamp(ponto.getDataSaida().getTime());
			statement.setTimestamp(2, dataSaida);//3

			//java.sql.Time horaSaida = ponto.getDataSaida() == null ? null : new java.sql.Time(ponto.getDataSaida().getTime());
			//statement.setTime(4, horaSaida);

			statement.setInt(3, ponto.getAluno().getIdUsuario());
			statement.setInt(4, ponto.getResponsavel().getIdUsuario());
			statement.setString(5, String.valueOf(ponto.getStatus()));
			statement.setInt(6, idPonto);
			statement.executeUpdate();

			/*ResultSet resultset = statement.getGeneratedKeys();
			if (resultset.first()) {
				idPonto = resultset.getInt(1);

			}*/

			System.out.println(ponto);
			ok = true;
			return ok;

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
			throw new PersistenciaException(e);

		} finally {
			conexao.close();
		}

	}

	public ArrayList<Ponto> listaAlunos(Date dataEntrada, Date dataSaida) throws SQLException {
		UsuarioDAO alunoDAO = new UsuarioDAO();
		UsuarioDAO responsavelDAO = new UsuarioDAO();
		ArrayList<Ponto> listaAlunos = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("select p.id_ponto, id_usuario_aluno, id_usuario_responsavel, timestampdiff(minute,p.data_entrada,p.data_saida)  horas,");
			sql.append("p.data_entrada, p.hora_entrada, p.data_saida, p.hora_saida, status_ponto ");
			sql.append("from tb_ponto p ");
			sql.append("where p.data_entrada between ? and ? ;");

			PreparedStatement statement;

			statement = conexao.prepareStatement(sql.toString());
			
			java.sql.Timestamp dataEntradaSql = new java.sql.Timestamp(dataEntrada.getTime());
			statement.setTimestamp(1, dataEntradaSql);
			
			java.sql.Timestamp dataSaidaSql = new java.sql.Timestamp(dataSaida.getTime());
			statement.setTimestamp(2, dataSaidaSql);
			
		//	statement.setTimestamp(3, dataEntradaSql);
			
		//	statement.setTimestamp(4, dataSaidaSql);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Ponto ponto = new Ponto();
				ponto.setIdPonto(resultSet.getInt("id_ponto"));
				Usuario aluno = alunoDAO.buscaUsuarioId(resultSet.getInt("id_usuario_aluno"));
				ponto.setAluno(aluno);
				Usuario responsavel = responsavelDAO.buscaUsuarioId(resultSet.getInt("id_usuario_responsavel"));
				ponto.setResponsavel(responsavel);
				ponto.setDataEntrada(resultSet.getTimestamp("data_entrada"));
				ponto.setDataSaida(resultSet.getTimestamp("data_saida"));
				ponto.setStatus(StatusPonto.valueOf(resultSet.getString("status_ponto")));
				ponto.setHoraTotalDia(resultSet.getInt("horas"));
				listaAlunos.add(ponto);
			}
		} catch (ClassNotFoundException | SQLException | PersistenciaException e) {
			e.printStackTrace();
		} finally {
			conexao.close();
		}

		return listaAlunos;
	}
}
