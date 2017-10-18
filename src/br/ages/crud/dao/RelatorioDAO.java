package br.ages.crud.dao;

import br.ages.crud.model.Relatorio;
import br.ages.crud.model.StatusRelatorio;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;
import com.mysql.jdbc.Statement;

import java.sql.*;
import java.util.ArrayList;

public class RelatorioDAO {

    public RelatorioDAO() {
    }

    public boolean cadastrarRelatorio(Relatorio relatorio) throws SQLException {
        boolean ok = false;
        Connection conexao = null;
        try {
            Integer idRelatorio = null;

            conexao = ConexaoUtil.getConexao();


            for(int i = 1; i<=4; i++) {
                StringBuilder sql = new StringBuilder();
                ok = false;

                sql.append("INSERT INTO tb_relatorio (ID_TIME_ALUNO, ID_QUESTAO, RESPOSTA, DATA_RESPOSTA, DATA_ABERTURA, STATUS, TIPO_RELATORIO) ");
                sql.append("VALUES (?, ?, ?, ?, ?, ?, ?)");

                java.sql.Date dataInclusao = new java.sql.Date(relatorio.getDtInclusao().getTime());
                java.sql.Date dataAbertura = new java.sql.Date(relatorio.getInicioSemana().getTime());

                PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, relatorio.getIdTimeAluno());
                statement.setInt(2, i);
                switch (i) {
                    case 1 : statement.setString(3,relatorio.getAtividadesPrevistas());
                    break;
                    case 2 : statement.setString(3,relatorio.getAtividadesConcluidas());
                    break;
                    case 3 : statement.setString(3,relatorio.getLicoesProblemas());
                    break;
                    case 4 : statement.setString(3,relatorio.getProximo());
                }
                statement.setDate(4, dataInclusao);
                statement.setDate(5, dataAbertura);
                statement.setString(6, relatorio.getStatus().toString());
                statement.setString(7, relatorio.getTipo().toString());

                statement.executeUpdate();

                ResultSet resultset = statement.getGeneratedKeys();

                if (resultset.first()){
                    ok = true;
                }
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

            for(int i = 1; i<=4; i++) {
                StringBuilder sql = new StringBuilder();

                sql.append("UPDATE tb_relatorio SET ID_QUESTAO = ? , RESPOSTA = ? , DATA_RESPOSTA = ? ");
                sql.append("WHERE ID_RELATORIO = ? ");

                java.sql.Date dataInclusao = new java.sql.Date(relatorio.getDtInclusao().getTime());

                PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, i);
                switch (i) {
                    case 1:
                        statement.setString(2, relatorio.getAtividadesPrevistas());
                        break;
                    case 2:
                        statement.setString(2, relatorio.getAtividadesConcluidas());
                        break;
                    case 3:
                        statement.setString(2, relatorio.getLicoesProblemas());
                        break;
                    case 4:
                        statement.setString(2, relatorio.getProximo());
                }
                statement.setDate(3, dataInclusao);
                statement.setInt(4, (relatorio.getIdRelatorio()-1)+i);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            conexao.close();
        }
    }

    public ArrayList<Relatorio> listarRelatorios(int idAlunoTime) throws SQLException {
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
                relatorio.setAtividadesPrevistas(resultSet.getString("ATIVIDADES_PREVISTAS"));
                relatorio.setAtividadesConcluidas(resultSet.getString("ATIVIDADES_CONCLUIDAS"));
                relatorio.setLicoesProblemas(resultSet.getString("LICOESPROBLEMAS"));
                relatorio.setProximo(resultSet.getString("PROXIMO"));
                relatorio.setInicioSemana(resultSet.getDate("INICIO_SEMANA"));
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
                relatorio.setAtividadesPrevistas(resultSet.getString("ATIVIDADES_PREVISTAS"));
                relatorio.setAtividadesConcluidas(resultSet.getString("ATIVIDADES_CONCLUIDAS"));
                relatorio.setLicoesProblemas(resultSet.getString("LICOESPROBLEMAS"));
                relatorio.setProximo(resultSet.getString("PROXIMO"));
                relatorio.setInicioSemana(resultSet.getDate("INICIO_SEMANA"));
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
                relatorio.setAtividadesPrevistas(resultSet.getString("ATIVIDADES_PREVISTAS"));
                relatorio.setAtividadesConcluidas(resultSet.getString("ATIVIDADES_CONCLUIDAS"));
                relatorio.setLicoesProblemas(resultSet.getString("LICOESPROBLEMAS"));
                relatorio.setProximo(resultSet.getString("PROXIMO"));
                relatorio.setInicioSemana(resultSet.getDate("INICIO_SEMANA"));
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

    public int validaAluno(Usuario aluno, int idTime){
        Connection conexao = null;
        int idTimeAluno = 0;

        try{
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ID_TIME_ALUNO");
            sql.append(" FROM TB_TIME_ALUNO");
            sql.append(" WHERE ID_TIME = ? AND ID_ALUNO = ? ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setInt(1,idTime);
            statement.setInt(2, aluno.getIdUsuario());

            ResultSet resultset = statement.executeQuery();

            if(resultset.next()){
                idTimeAluno = resultset.getInt("ID_TIME_ALUNO");
            }else{
                idTimeAluno = 0;
            }

            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return idTimeAluno;
    }
}
