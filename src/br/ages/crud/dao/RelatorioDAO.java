package br.ages.crud.dao;

import br.ages.crud.model.Relatorio;
import br.ages.crud.model.StatusRelatorio;
import br.ages.crud.util.ConexaoUtil;
import com.mysql.jdbc.Statement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public RelatorioDAO() {
    }

    public boolean cadastrarRelatorio(Relatorio relatorio) throws SQLException {
        boolean ok = false;
        Connection conexao = null;
        try {
            Integer idRelatorio = null;

            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO tb_relatorio (ID_ALUNO,ID_TIME, ATIVIDADES_PREVISTAS, ATIVIDADES_CONCLUIDAS, LICOESPROBLEMAS,PROXIMO, INICIO_SEMANA,FIM_SEMANA, STATUS, DT_INCLUSAO)");
            sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            java.sql.Date dataInclusao = new java.sql.Date(relatorio.getDtInclusao().getTime());

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1,relatorio.getIdAluno());
            statement.setInt(2,relatorio.getIdTime());
            statement.setString(3,relatorio.getAtividadesPrevistas());
            statement.setString(4,relatorio.getAtividadesConcluidas());
            statement.setString(5,relatorio.getLicoesProblemas());
            statement.setString(6,relatorio.getProximo());
            statement.setDate(7, Date.valueOf(relatorio.getInicioSemana().toString()));
            statement.setDate(8,Date.valueOf(relatorio.getFimSemana().toString()));
            statement.setString(9,relatorio.getStatus().toString());
            statement.setDate(10, dataInclusao);

            statement.executeUpdate();

            ResultSet resultset = statement.getGeneratedKeys();

            if (resultset.first()) {
                idRelatorio = resultset.getInt(1);
                relatorio.setIdRelatorio(idRelatorio.intValue());
                ok=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            conexao.close();
        }

        return ok;
    }

    public void editarRelatorio(Relatorio relatorio) throws SQLException {
        Connection conexao = null;

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE tb_relatorio  ATIVIDADES_PREVISTAS = ?, ATIVIDADES_CONCLUIDAS = ?, "
                    + "LICOESPROBLEMAS = ?, PROXIMO = ? WHERE ID_RELATORIO = ? AND STATUS = REVISAO;");


            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setString(1, relatorio.getAtividadesPrevistas());
            statement.setString(2, relatorio.getAtividadesConcluidas());
            statement.setString(3, relatorio.getLicoesProblemas());
            statement.setString(4,relatorio.getProximo());
            statement.setInt(5, relatorio.getIdRelatorio());
            statement.setString(6, StatusRelatorio.REVISAO.toString());

            statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            conexao.close();
        }
    }

    public ArrayList<Relatorio> listarRelatorios(int idAluno) throws SQLException {
        Connection conexao = null;
        ArrayList<Relatorio> listaRelatorios = new ArrayList<Relatorio>();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ID_RELATORIO, ID_ALUNO, ID_TIME, ATIVIDADES_PREVISTAS, ATIVIDADES_CONCLUIDAS, LICOESPROBLEMAS, PROXIMO, INICIO_SEMANA, FIM_SEMANA, STATUS, DT_INCLUSAO");
            sql.append(" FROM TB_RELATORIO ");
            sql.append(" WHERE ID_ALUNO = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setInt(1, idAluno);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Relatorio relatorio = new Relatorio();

                relatorio.setIdRelatorio(resultSet.getInt("ID_RELATORIO"));
                relatorio.setIdAluno(resultSet.getInt("ID_ALUNO"));
                relatorio.setIdTime(resultSet.getInt("ID_TIME"));
                relatorio.setAtividadesPrevistas(resultSet.getString("ATIVIDADES_PREVISTAS"));
                relatorio.setAtividadesConcluidas(resultSet.getString("ATIVIDADES_CONCLUIDAS"));
                relatorio.setLicoesProblemas(resultSet.getString("LICOESPROBLEMAS"));
                relatorio.setProximo(resultSet.getString("PROXIMO"));
                relatorio.setInicioSemana(resultSet.getDate("INICIO_SEMANA"));
                relatorio.setFimSemana(resultSet.getDate("FIM_SEMANA"));
                relatorio.setStatus(StatusRelatorio.valueOf(resultSet.getString("STATUS")));
                relatorio.setDtInclusao(resultSet.getDate("DT_INCLUSAO"));

                listaRelatorios.add(relatorio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            conexao.close();
        }

        return listaRelatorios;
    }

    public ArrayList<Relatorio> listarRelatoriosCoord(int idTime) throws SQLException {
        Connection conexao = null;
        ArrayList<Relatorio> listaRelatorios = new ArrayList<Relatorio>();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ID_RELATORIO, ID_ALUNO, ID_TIME, ATIVIDADES_PREVISTAS, ATIVIDADES_CONCLUIDAS, LICOESPROBLEMAS, PROXIMO, INICIO_SEMANA, FIM_SEMANA, STATUS, DT_INCLUSAO");
            sql.append(" FROM TB_RELATORIO ");
            sql.append(" WHERE ID_TIME = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setInt(1, idTime);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Relatorio relatorio = new Relatorio();

                relatorio.setIdRelatorio(resultSet.getInt("ID_RELATORIO"));
                relatorio.setIdAluno(resultSet.getInt("ID_ALUNO"));
                relatorio.setIdTime(resultSet.getInt("ID_TIME"));
                relatorio.setAtividadesPrevistas(resultSet.getString("ATIVIDADES_PREVISTAS"));
                relatorio.setAtividadesConcluidas(resultSet.getString("ATIVIDADES_CONCLUIDAS"));
                relatorio.setLicoesProblemas(resultSet.getString("LICOESPROBLEMAS"));
                relatorio.setProximo(resultSet.getString("PROXIMO"));
                relatorio.setInicioSemana(resultSet.getDate("INICIO_SEMANA"));
                relatorio.setFimSemana(resultSet.getDate("FIM_SEMANA"));
                relatorio.setStatus(StatusRelatorio.valueOf(resultSet.getString("STATUS")));
                relatorio.setDtInclusao(resultSet.getDate("DT_INCLUSAO"));

                listaRelatorios.add(relatorio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            conexao.close();
        }

        return listaRelatorios;
    }

    public ArrayList<Relatorio> listarRelatorios() throws SQLException {
        Connection conexao = null;
        ArrayList<Relatorio> listaRelatorios = new ArrayList<Relatorio>();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ID_RELATORIO, ID_ALUNO, ID_TIME, ATIVIDADES_PREVISTAS, ATIVIDADES_CONCLUIDAS, LICOESPROBLEMAS, PROXIMO, INICIO_SEMANA, FIM_SEMANA, STATUS, DT_INCLUSAO");
            sql.append(" FROM TB_RELATORIO ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Relatorio relatorio = new Relatorio();

                relatorio.setIdRelatorio(resultSet.getInt("ID_RELATORIO"));
                relatorio.setIdAluno(resultSet.getInt("ID_ALUNO"));
                relatorio.setIdTime(resultSet.getInt("ID_TIME"));
                relatorio.setAtividadesPrevistas(resultSet.getString("ATIVIDADES_PREVISTAS"));
                relatorio.setAtividadesConcluidas(resultSet.getString("ATIVIDADES_CONCLUIDAS"));
                relatorio.setLicoesProblemas(resultSet.getString("LICOESPROBLEMAS"));
                relatorio.setProximo(resultSet.getString("PROXIMO"));
                relatorio.setInicioSemana(resultSet.getDate("INICIO_SEMANA"));
                relatorio.setFimSemana(resultSet.getDate("FIM_SEMANA"));
                relatorio.setStatus(StatusRelatorio.valueOf(resultSet.getString("STATUS")));
                relatorio.setDtInclusao(resultSet.getDate("DT_INCLUSAO"));

                listaRelatorios.add(relatorio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            conexao.close();
        }

        return listaRelatorios;
    }
}
