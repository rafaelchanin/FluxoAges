package br.ages.crud.dao;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.Status;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.ConexaoUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class TipoEquipamentoDAO {
    private ArrayList<TipoEquipamento> tipoequipamentos;

    public ArrayList<TipoEquipamento> listarTipoEquipamentos() throws PersistenciaException, SQLException {
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("id_tipo_equipamento, ");
            sql.append("nome, ");
            sql.append("status ");
            sql.append("FROM tb_tipo_equipamento");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultset = statement.executeQuery();

            tipoequipamentos = new ArrayList<>();
            while (resultset.next()) {
                TipoEquipamento dto = new TipoEquipamento();
                dto.setId(resultset.getInt("id_tipo_equipamento"));
                dto.setNome(resultset.getString("nome"));
                dto.setStatus(Status.valueOf(resultset.getString("status")));

                tipoequipamentos.add(dto);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return tipoequipamentos;
    }

    public TipoEquipamento buscarEquipamentoPorId(int id) {
        Connection conexao = null;
        TipoEquipamento tipoEquipamento = null;

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT nome");
            sql.append(" FROM tb_tipo_equipamento");
            sql.append(" WHERE id_tipo_equipamento = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setInt(1, id);

            ResultSet resultset = statement.executeQuery();
            tipoEquipamento = new TipoEquipamento();
            while (resultset.next()) {
                tipoEquipamento.setId(id);
                tipoEquipamento.setNome(resultset.getString("nome"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tipoEquipamento;
    }

    public boolean removerTipoEquipamento(Integer id) throws PersistenciaException {
        boolean removidoOK = false;
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE tb_equipamento SET STATUS = ?, data_movimentacao = ? where id_equipamento= ? ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setString(1, String.valueOf(Status.INATIVO));
            statement.setInt(2, id);

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

    public boolean cadastrarTipoEquipamento(TipoEquipamento tipoEquipamento) throws PersistenciaException, SQLException {
        boolean ok = false;
        Connection conexao = null;

        try {
            Integer id = null;

            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_tipo_equipamento (nome, status) ");
            sql.append("VALUES (?,?)");

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tipoEquipamento.getNome());
            statement.setString(2, String.valueOf(Status.ATIVO));

            statement.executeUpdate();

            ResultSet resultset = statement.getGeneratedKeys();
            if (resultset.first()) {
                id = resultset.getInt(1);
                tipoEquipamento.setId(id);
                ok=true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return ok;
    }

    public boolean editarTipoEquipamento(TipoEquipamento tipoEquipamento) throws PersistenciaException {
        boolean ok = false;
        boolean mudou = false;
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            int id = tipoEquipamento.getId();

            sql.append("UPDATE tb_equipamento SET");
            sql.append(" nome = ?,");
            sql.append(" status = ?");
            sql.append(" WHERE id_tipo_equipamento = "+id+";");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            statement.setString(1, tipoEquipamento.getNome());
            statement.setString(2, String.valueOf(tipoEquipamento.getStatus()));

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
    }

