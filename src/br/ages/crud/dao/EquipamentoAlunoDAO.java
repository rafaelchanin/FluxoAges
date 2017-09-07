package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.*;
import br.ages.crud.util.ConexaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
            sql.append("SELECT ea.ID_EQUIP_ALUNO, e.ID_EQUIPAMENTO, e.NOME, e.CODIGO, u.ID_USUARIO, u.NOME, u.MATRICULA, ea.DATA_RETIRADA, ea.DATA_ENTREGA FROM tb_equipamento e ");
            sql.append("INNER JOIN tb_equipamento_aluno ea ");
            sql.append("ON e.ID_EQUIPAMENTO = ea.ID_EQUIPAMENTO ");
            sql.append("INNER JOIN tb_usuario u ");
            sql.append("ON ea.ID_USUARIO = u.ID_USUARIO;");

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

                equipamentoAlunos.add(equipamentoAluno);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return equipamentoAlunos;
    }
}
