package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.util.ConexaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipamentoDAO {

    private ArrayList<Equipamento> equipamentos;

    public ArrayList<Equipamento> listarEquipamentos() throws PersistenciaException, SQLException {
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("id_equipamento, ");
            sql.append("nome, ");
            sql.append("codigo, ");
            sql.append("descricao ");
            sql.append("FROM tb_equipamento");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultset = statement.executeQuery();

            equipamentos = new ArrayList<>();
            while (resultset.next()) {
                Equipamento dto = new Equipamento();
                dto.setId(resultset.getInt("id_equipamento"));
                dto.setNome(resultset.getString("nome"));
                dto.setCodigo(resultset.getInt("codigo"));
                dto.setDescricao(resultset.getString("descricao"));

                equipamentos.add(dto);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return equipamentos;
    }

}
