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



                StringBuilder sql = new StringBuilder();

                sql.append("INSERT INTO tb_relatorio (ID_TIME_ALUNO, DATA_RESPOSTA, DATA_ABERTURA, STATUS, TIPO_RELATORIO) ");
                sql.append("VALUES (?, ?, ?, ?, ?)");

                java.sql.Date dataInclusao = new java.sql.Date(relatorio.getDtInclusao().getTime());
                java.sql.Date dataAbertura = new java.sql.Date(relatorio.getInicioSemana().getTime());

                PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, relatorio.getIdTimeAluno());
                statement.setDate(2, dataInclusao);
                statement.setDate(3, dataAbertura);
                statement.setString(4, relatorio.getStatus().toString());
                statement.setString(5, relatorio.getTipo().toString());

                statement.executeUpdate();

                ResultSet resultset = statement.getGeneratedKeys();

                if (resultset.first()){
                    idRelatorio = resultset.getInt(1);
                    this.cadastrarResposta(idRelatorio, relatorio);
                    ok = true;
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

    public int time(int idAluno) {
        int idTimeAluno = 0;

        Connection conexao = null;


        try {
            conexao = ConexaoUtil.getConexao();


            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ID_TIME_ALUNO FROM (( ages_e.tb_time_aluno");
            sql.append(" INNER JOIN ages_e.tb_usuario ON ages_e.tb_time_aluno.ID_ALUNO = ages_e.tb_usuario.ID_USUARIO)");
            sql.append(" INNER JOIN ages_e.tb_time ON ages_e.tb_time.ID_TIME = ages_e.tb_time_aluno.ID_TIME) WHERE ages_e.tb_time.STATUS_TIME = ? AND ages_e.tb_usuario.ID_USUARIO = ? ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, "ATIVA");
            statement.setInt(2, idAluno);

            ResultSet resultset = statement.executeQuery();

            while (resultset.next()){
                idTimeAluno = resultset.getInt("ID_TIME_ALUNO");
            }
            conexao.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idTimeAluno;

    }

    public void cadastrarResposta(int idRelatorio, Relatorio relatorio){
        Connection conexao = null;


        try {
            conexao = ConexaoUtil.getConexao();


            for(int i = 1; i <= 4; i++) {
                StringBuilder sql = new StringBuilder();

                sql.append("INSERT INTO tb_resposta (ID_RELATORIO, ID_QUESTAO, RESPOSTA) ");
                sql.append("VALUES (?, ?, ?)");

                PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, idRelatorio);
                statement.setInt(2, i);
                switch (i) {
                    case 1:
                        statement.setString(3, relatorio.getAtividadesPrevistas());
                        break;

                    case 2:
                        statement.setString(3, relatorio.getAtividadesConcluidas());
                        break;

                    case 3:
                        statement.setString(3, relatorio.getLicoesProblemas());
                        break;

                    case 4:
                        statement.setString(3, relatorio.getProximo());
                }

                statement.executeUpdate();

            }
            conexao.close();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Relatorio buscaRelatorioId(int id){
        Connection conexao = null;

        Relatorio relatorio = new Relatorio();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT ID_TIME_ALUNO, DATA_ABERTURA, STATUS ");
            sql.append("FROM tb_relatorio ");
            sql.append("WHERE ID_RELATORIO = ? ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, id);

            ResultSet resultset = statement.executeQuery();

            System.out.println(resultset);

            if(resultset.next()) {
                relatorio.setIdTimeAluno(resultset.getInt("ID_TIME_ALUNO"));
                relatorio.setInicioSemana(resultset.getDate("DATA_ABERTURA"));
                relatorio.setStatus(StatusRelatorio.valueOf(resultset.getString("STATUS")));
            }


            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return relatorio;

    }

    public Relatorio buscaRespostas(int id, Relatorio relatorio){
        Connection conexao = null;

        try{
            conexao = ConexaoUtil.getConexao();

            for(int i = 1; i <= 4; i++) {
                StringBuilder sql = new StringBuilder();

                sql.append("SELECT RESPOSTA ");
                sql.append("FROM tb_resposta ");
                sql.append("WHERE ID_RELATORIO = ? AND ID_QUESTAO = ? ");

                PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1,id);
                statement.setInt(2,i);

                ResultSet resultset = statement.executeQuery();

                if(resultset.next()) {
                    switch (i) {
                        case 1:
                            relatorio.setAtividadesPrevistas(resultset.getString("RESPOSTA"));
                            break;
                        case 2:
                            relatorio.setAtividadesConcluidas(resultset.getString("RESPOSTA"));
                            break;
                        case 3:
                            relatorio.setLicoesProblemas(resultset.getString("RESPOSTA"));
                            break;
                        case 4:
                            relatorio.setProximo(resultset.getString("RESPOSTA"));
                    }
                }

            }

            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return relatorio;
    }

    public void editarRelatorio(Relatorio relatorio) throws SQLException {
        Connection conexao = null;

        try {
            conexao = ConexaoUtil.getConexao();

            for(int i = 1; i<=4; i++) {
                StringBuilder sql = new StringBuilder();

                sql.append("UPDATE tb_resposta SET  RESPOSTA = ? ");
                sql.append("WHERE ID_RELATORIO = ? && ID_QUESTAO = ? ");

                PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                switch (i) {
                    case 1:
                        statement.setString(1, relatorio.getAtividadesPrevistas());
                        break;
                    case 2:
                        statement.setString(1, relatorio.getAtividadesConcluidas());
                        break;
                    case 3:
                        statement.setString(1, relatorio.getLicoesProblemas());
                        break;
                    case 4:
                        statement.setString(1, relatorio.getProximo());
                }
                statement.setInt(2, relatorio.getIdRelatorio());
                statement.setInt(3, i);

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
            sql.append(" SELECT ID_RELATORIO, DATA_ABERTURA, STATUS, DATA_RESPOSTA");
            sql.append(" FROM tb_relatorio ");
            sql.append(" WHERE ID_TIME_ALUNO = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setInt(1, idAlunoTime);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Relatorio relatorio = new Relatorio();

                relatorio.setIdRelatorio(resultSet.getInt("ID_RELATORIO"));
                relatorio.setInicioSemana(resultSet.getDate("DATA_ABERTURA"));
                relatorio.setStatus(StatusRelatorio.valueOf(resultSet.getString("STATUS")));
                relatorio.setDtInclusao(resultSet.getDate("DATA_RESPOSTA"));


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

    public ArrayList<Relatorio> listarRelatoriosCoord(int idAlunoTime, int idAluno, String aluno) throws SQLException {
        Connection conexao = null;
        ArrayList<Relatorio> listaRelatorios = new ArrayList<Relatorio>();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT ID_RELATORIO, DATA_ABERTURA, STATUS, DATA_RESPOSTA");
            sql.append(" FROM tb_relatorio ");
            sql.append(" WHERE ID_TIME_ALUNO = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setInt(1, idAlunoTime);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Relatorio relatorio = new Relatorio();

                relatorio.setIdRelatorio(resultSet.getInt("ID_RELATORIO"));
                relatorio.setInicioSemana(resultSet.getDate("DATA_ABERTURA"));
                relatorio.setStatus(StatusRelatorio.valueOf(resultSet.getString("STATUS")));
                relatorio.setDtInclusao(resultSet.getDate("DATA_RESPOSTA"));
                relatorio.setIdAluno(idAluno);
                relatorio.setAluno(aluno);

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
            sql.append(" FROM tb_relatorio ");

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
            sql.append(" FROM tb_time_aluno");
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

    public void validar(Relatorio relatorio) throws SQLException {
        Connection conexao = null;

        try{
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" UPDATE tb_relatorio SET status = ?");
            sql.append(" WHERE id_relatorio = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setString(1,relatorio.getStatus().toString());
            statement.setInt(2, relatorio.getIdRelatorio());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            conexao.close();
        }
    }

}
