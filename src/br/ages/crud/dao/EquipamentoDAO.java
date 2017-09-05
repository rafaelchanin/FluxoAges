package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.ConexaoUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class EquipamentoDAO {

    private ArrayList<Equipamento> equipamentos;

    public boolean cadastrarEquipamento(Equipamento equipamento) throws PersistenciaException, SQLException, ParseException {
        boolean ok = false;
        Connection conexao = null;
        try {
            Integer id = null;

            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_equipamento (nome, codigo, descricao, id_tipo_equipamento)");
            sql.append("VALUES (?, ?, ?, ?)");

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, equipamento.getNome());
            statement.setString(2, equipamento.getCodigo());
            statement.setString(3, equipamento.getDescricao());
            statement.setInt(4, equipamento.getTipoEquipamento().getId());

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
            sql.append("t.nome ");
            sql.append("FROM tb_equipamento e ");
            sql.append("INNER JOIN tb_tipo_equipamento t ");
            sql.append("ON t.ID_TIPO_EQUIPAMENTO = e.ID_TIPO_EQUIPAMENTO");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultset = statement.executeQuery();

            equipamentos = new ArrayList<>();
            while (resultset.next()) {
                Equipamento dto = new Equipamento();
                TipoEquipamento tipoEquipamento = new TipoEquipamento();
                dto.setId(resultset.getInt("e.id_equipamento"));
                dto.setNome(resultset.getString("e.nome"));
                dto.setCodigo(resultset.getString("e.codigo"));
                dto.setDescricao(resultset.getString("e.descricao"));
                tipoEquipamento.setNome(resultset.getString("t.nome"));
                dto.setTipoEquipamento(tipoEquipamento);

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
