package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.ConexaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
