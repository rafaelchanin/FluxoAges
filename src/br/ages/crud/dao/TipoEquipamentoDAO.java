package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.ConexaoUtil;

import java.sql.*;
import java.util.ArrayList;

public class TipoEquipamentoDAO {
    private ArrayList<TipoEquipamento> tipoequipamentos;

    public ArrayList<TipoEquipamento> listarTipoEquipamentos() throws PersistenciaException, SQLException {
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("id_tipo_equipamento, ");
            sql.append("nome ");
            sql.append("FROM tb_tipo_equipamento");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultset = statement.executeQuery();

            tipoequipamentos = new ArrayList<>();
            while (resultset.next()) {
                TipoEquipamento dto = new TipoEquipamento();
                dto.setId(resultset.getInt("id_tipo_equipamento"));
                dto.setNome(resultset.getString("nome"));

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

    public boolean cadastrarTipoEquipamento(TipoEquipamento tipoEquipamento) throws PersistenciaException, SQLException {
        boolean ok = false;
        Connection conexao = null;

        try {
            Integer id = null;

            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_tipo_equipamento (nome) ");
            sql.append("VALUES (?)");

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tipoEquipamento.getNome());

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
}
