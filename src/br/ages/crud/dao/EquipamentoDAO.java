package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.Status;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.ConexaoUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EquipamentoDAO {

    private ArrayList<Equipamento> equipamentos;

    public boolean cadastrarEquipamento(Equipamento equipamento) throws PersistenciaException, SQLException, ParseException {
        boolean ok = false;
        Connection conexao = null;

        java.sql.Date dateSql = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            Integer id = null;

            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_equipamento (nome, codigo, descricao, id_tipo_equipamento, data_movimentacao, status)");
            sql.append("VALUES (?, ?, ?, ?, ?, ?)");

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, equipamento.getNome());
            statement.setString(2, equipamento.getCodigo());
            statement.setString(3, equipamento.getDescricao());
            statement.setInt(4, equipamento.getTipoEquipamento().getId());
            statement.setDate(5, dateSql);
            statement.setString(6, String.valueOf(Status.ATIVO));

            statement.executeUpdate();

            ResultSet resultset = statement.getGeneratedKeys();
            if (resultset.first()) {
                id = resultset.getInt(1);
                equipamento.setId(id);
                ok=true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return ok;
    }

    public ArrayList<Equipamento> listarEquipamentos() throws PersistenciaException, SQLException {
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("e.id_equipamento, ");
            sql.append("e.nome, ");
            sql.append("e.codigo, ");
            sql.append("e.descricao, ");
            sql.append("e.data_movimentacao, ");
            sql.append("e.status, ");
            sql.append("t.nome ");
            sql.append("FROM tb_equipamento e ");
            sql.append("INNER JOIN tb_tipo_equipamento t ");
            sql.append("ON t.ID_TIPO_EQUIPAMENTO = e.ID_TIPO_EQUIPAMENTO");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();

            equipamentos = new ArrayList<>();
            while (resultSet.next()) {
                Equipamento equipamento = new Equipamento();
                TipoEquipamento tipoEquipamento = new TipoEquipamento();
                equipamento.setId(resultSet.getInt("e.id_equipamento"));
                equipamento.setNome(resultSet.getString("e.nome"));
                equipamento.setCodigo(resultSet.getString("e.codigo"));
                equipamento.setDescricao(resultSet.getString("e.descricao"));
                equipamento.setDataMovimentacao(resultSet.getTimestamp("e.data_movimentacao"));
                equipamento.setStatus(Status.valueOf(resultSet.getString("e.status")));
                tipoEquipamento.setNome(resultSet.getString("t.nome"));
                equipamento.setTipoEquipamento(tipoEquipamento);

                equipamentos.add(equipamento);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return equipamentos;
    }

    public Equipamento buscaEquipamentoPorId(int id) throws PersistenciaException, SQLException {

        Equipamento equipamento = new Equipamento();

        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("e.id_equipamento, ");
            sql.append("e.nome, ");
            sql.append("e.codigo, ");
            sql.append("e.descricao, ");
            sql.append("e.data_movimentacao, ");
            sql.append("e.status, ");
            sql.append("t.nome ");
            sql.append("FROM tb_equipamento e ");
            sql.append("INNER JOIN tb_tipo_equipamento t ");
            sql.append("ON t.ID_TIPO_EQUIPAMENTO = e.ID_TIPO_EQUIPAMENTO ");
            sql.append("WHERE e.id_equipamento = ?;");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            TipoEquipamento tipoEquipamento;
            while (resultSet.next()) {
                tipoEquipamento = new TipoEquipamento();
                equipamento.setId(resultSet.getInt("e.id_equipamento"));
                equipamento.setNome(resultSet.getString("e.nome"));
                equipamento.setCodigo(resultSet.getString("e.codigo"));
                equipamento.setDescricao(resultSet.getString("e.descricao"));
                equipamento.setDataMovimentacao(resultSet.getTimestamp("e.data_movimentacao"));
                equipamento.setStatus(Status.valueOf(resultSet.getString("e.status")));
                tipoEquipamento.setNome(resultSet.getString("t.nome"));
                equipamento.setTipoEquipamento(tipoEquipamento);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return equipamento;

    }

    public boolean editaEquipamento(Equipamento equipamento) throws PersistenciaException {
        boolean ok = false;
        boolean mudou = false;
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            int id = equipamento.getId();

            sql.append("UPDATE tb_equipamento SET");
            sql.append(" nome = ?,");
            sql.append(" codigo = ?,");
            sql.append(" descricao = ?,");
            sql.append(" id_tipo_equipamento = ?,");
            sql.append(" status = ?");

            Equipamento eqp = new Equipamento();
            eqp = buscaEquipamentoPorId(id);

            if(!eqp.getStatus().equals(equipamento.getStatus())){
                sql.append(", data_movimentacao = ?");
                mudou = true;
            }

            sql.append(" WHERE id_equipamento = "+id+";");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setString(1, equipamento.getNome());
            statement.setString(2, equipamento.getCodigo());
            statement.setString(3, equipamento.getDescricao());
            statement.setInt(4, equipamento.getTipoEquipamento().getId());
            statement.setString(5, String.valueOf(equipamento.getStatus()));

            if (mudou){
                java.sql.Date dateSql = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                statement.setDate(6, dateSql);
            }

            ok = statement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ok;
    }

    public boolean removerEquipamento(Integer id) throws PersistenciaException {
        boolean removidoOK = false;
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE tb_equipamento SET STATUS = ?, data_movimentacao = ? where id_equipamento= ? ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            java.sql.Date dateSql = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            statement.setString(1, String.valueOf(Status.INATIVO));
            statement.setDate(2, dateSql);
            statement.setInt(3, id);

            removidoOK = statement.execute();

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return removidoOK;
    }
}
