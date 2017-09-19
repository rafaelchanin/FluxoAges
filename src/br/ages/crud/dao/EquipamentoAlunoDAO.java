package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.EquipamentoAluno;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EquipamentoAlunoDAO {

    private Equipamento equipamento;
    private Usuario aluno;
    private ArrayList<EquipamentoAluno> equipamentoAlunos;
    private EquipamentoAluno equipamentoAluno;

    public ArrayList<EquipamentoAluno> listarEquipamentosAlunos() throws PersistenciaException, SQLException {
        Connection conexao = null;

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ea.ID_EQUIP_ALUNO, e.ID_EQUIPAMENTO, e.NOME, e.CODIGO, u.ID_USUARIO, u.NOME, u.MATRICULA, ea.DATA_RETIRADA, ea.DATA_ENTREGA, ea.OBSERVACAO FROM tb_equipamento e ");
            sql.append("INNER JOIN tb_equipamento_aluno ea ");
            sql.append("ON e.ID_EQUIPAMENTO = ea.ID_EQUIPAMENTO ");
            sql.append("INNER JOIN tb_usuario u ");
            sql.append("ON ea.ID_USUARIO = u.ID_USUARIO ");
            sql.append("ORDER BY ea.ID_EQUIP_ALUNO DESC;");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();

            equipamentoAlunos = new ArrayList<>();
            while (resultSet.next()) {
                equipamento = new Equipamento();
                equipamento.setId(resultSet.getInt("e.ID_EQUIPAMENTO"));
                equipamento.setNome(resultSet.getString("e.NOME"));
                equipamento.setCodigo(resultSet.getString("e.CODIGO"));

                aluno = new Usuario();
                aluno.setIdUsuario(resultSet.getInt("u.ID_USUARIO"));
                aluno.setNome(resultSet.getString("u.NOME"));
                aluno.setMatricula(resultSet.getString("u.MATRICULA"));

                equipamentoAluno = new EquipamentoAluno();
                equipamentoAluno.setEquipamento(equipamento);
                equipamentoAluno.setAluno(aluno);
                equipamentoAluno.setId(resultSet.getInt("ea.ID_EQUIP_ALUNO"));
                equipamentoAluno.setDataRetirada(resultSet.getTimestamp("ea.DATA_RETIRADA"));
                equipamentoAluno.setDataEntrega(resultSet.getTimestamp("ea.DATA_ENTREGA"));
                equipamentoAluno.setObservacao(resultSet.getString("ea.OBSERVACAO"));

                equipamentoAlunos.add(equipamentoAluno);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return equipamentoAlunos;
    }

    public boolean entregarEquipamento(int id, String observacao) throws PersistenciaException {
        boolean ok = false;
        Connection conexao = null;

        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();

            sql.append("UPDATE tb_equipamento_aluno SET");
            sql.append(" data_entrega = NOW(),");
            sql.append(" observacao = '"+observacao+"'");
            sql.append(" WHERE id_equip_aluno = "+id+";");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

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
