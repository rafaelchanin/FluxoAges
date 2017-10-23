package br.ages.crud.dao;

import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Grupo;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.ConexaoUtil;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GrupoDAO {

    private Grupo grupo;
    private UsuarioDAO usuarioDAO;
    private Usuario alunoGrupo;

    public GrupoDAO() {
    }

    public boolean cadastrarGrupo(Grupo grupo) throws PersistenciaException, SQLException, ParseException {
        boolean ok = false;
        Connection conexao = null;
        try {
            Integer idGrupo = null;

            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_grupo (STATUS_GRUPO, PROJETO, SEMESTRE, ANO, DT_INCLUSAO)");
            sql.append("VALUES (?, ?, ?, ?, ?)");

            java.sql.Date dataInclusao = new java.sql.Date(grupo.getDtInclusao().getTime());

            PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, grupo.getStatus());
            statement.setString(2, grupo.getProjeto());
            statement.setInt(3, grupo.getSemestre());
            statement.setInt(4, grupo.getAno());
            statement.setDate(5, dataInclusao);

            statement.executeUpdate();

            ResultSet resultset = statement.getGeneratedKeys();
            if (resultset.first()) {
                idGrupo = resultset.getInt(1);
                grupo.setId(idGrupo.intValue());
                ok=true;
            }

            if (grupo.getAlunos() != null)
                inserirAlunosGrupoCadastro(conexao, grupo);

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
        return ok;
    }

    private boolean inserirAlunosGrupoCadastro(Connection conexao, Grupo grupo) throws SQLException {

        boolean ok = false;

        ArrayList<Usuario> listaAlunos = new ArrayList<>(grupo.getAlunos());
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_grupo_aluno (ID_ALUNO, MATRICULA, ID_GRUPO)");
            sql.append(" VALUES (?, ?, ?)");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            for (Usuario usuario : listaAlunos) {

                statement.setInt(1, usuario.getIdUsuario());
                statement.setString(2, usuario.getMatricula());
                statement.setInt(3, grupo.getId());

                ok = statement.execute();

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ok;
    }

    public boolean inserirAlunosGrupo(int idGrupo, List<Usuario> alunos) throws SQLException {
        boolean ok = false;
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_grupo_aluno (ID_GRUPO, ID_ALUNO, MATRICULA)");
            sql.append(" VALUES (?, ?, ?)");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            for (Usuario usuario : alunos) {

                statement.setInt(1, idGrupo);
                statement.setInt(2, usuario.getIdUsuario());
                statement.setString(3, usuario.getMatricula());

                ok = statement.execute();

            }
            ok=true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexao.close();
        }

        return ok;
    }

    public ArrayList<Grupo> listarGrupos() throws PersistenciaException, SQLException {
        Connection conexao = null;
        ArrayList<Grupo> listaGrupos = new ArrayList<Grupo>();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" select id_grupo, status_grupo, projeto, semestre, ano, dt_inclusao");
            sql.append(" from tb_grupo ");
            sql.append(" where  status_grupo <> 'excluido' ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Grupo grupo = new Grupo();
                grupo.setId(resultSet.getInt("id_grupo"));
                grupo.setStatus(resultSet.getString("status_grupo"));
                grupo.setProjeto(resultSet.getString("projeto"));
                grupo.setSemestre(resultSet.getInt("semestre"));
                grupo.setAno(resultSet.getInt("ano"));

                Date dataInclusao = resultSet.getDate("dt_inclusao");
                grupo.setDtInclusao(dataInclusao);

                grupo.setAlunos(buscarAlunosGrupo(conexao, resultSet.getInt("id_grupo")));

                listaGrupos.add(grupo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conexao.close();
        }

        return listaGrupos;
    }

    public ArrayList<Grupo> listarGruposAtivos() throws PersistenciaException, SQLException {
        Connection conexao = null;
        ArrayList<Grupo> listaGrupos = new ArrayList<Grupo>();

        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" select id_grupo, status_grupo, projeto, semestre, ano, dt_inclusao");
            sql.append(" from tb_grupo ");
            sql.append(" where  status_grupo = 'ATIVO' ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Grupo grupo = new Grupo();
                grupo.setId(resultSet.getInt("id_grupo"));
                grupo.setStatus(resultSet.getString("status_grupo"));
                grupo.setProjeto(resultSet.getString("projeto"));
                grupo.setSemestre(resultSet.getInt("semestre"));
                grupo.setAno(resultSet.getInt("ano"));

                Date dataInclusao = resultSet.getDate("dt_inclusao");
                grupo.setDtInclusao(dataInclusao);

                grupo.setAlunos(buscarAlunosGrupo(conexao, resultSet.getInt("id_grupo")));

                listaGrupos.add(grupo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conexao.close();
        }

        return listaGrupos;
    }

    public ArrayList<Usuario> buscarAlunosGrupo(Connection conexao, int idGrupo) throws PersistenciaException, SQLException {

        List<Usuario> alunosGrupo = new ArrayList<Usuario>();

        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ID_ALUNO, MATRICULA ");
            sql.append(" FROM tb_grupo_aluno");
            sql.append(" WHERE ID_GRUPO = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setInt(1, idGrupo);

            ResultSet resultset = statement.executeQuery();
            int idAluno = 0;
            usuarioDAO = new UsuarioDAO();
            alunoGrupo = new Usuario();

            while (resultset.next()) {
                idAluno = resultset.getInt(1);
                alunoGrupo = usuarioDAO.buscaUsuarioId(idAluno);
                alunosGrupo.add(alunoGrupo);
            }

            Collections.sort(alunosGrupo);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return (ArrayList<Usuario>) alunosGrupo;

    }

    public void editaGrupo(Grupo grupo) throws PersistenciaException, SQLException, ParseException {
        Connection conexao = null;
        try {

            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_grupo  STATUS_GRUPO = ?, PROJETO = ?, "
                    + "SEMESTRE = ?, ANO = ? WHERE ID_GRUPO = ?;");
/*
			java.sql.Date dataInicio = new java.sql.Date(projeto.getDataInicio().getTime());

			java.sql.Date dataFim = null;
			if(projeto.getDataFim() != null) dataFim = new java.sql.Date(projeto.getDataFim().getTime());

			java.sql.Date dataFimPrevisto = new java.sql.Date(projeto.getDataFimPrevisto().getTime());
*/
            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setString(1, grupo.getStatus());
            statement.setString(2, grupo.getProjeto());
            statement.setInt(3, grupo.getSemestre());
            statement.setInt(4, grupo.getAno());
            statement.setInt(5, grupo.getId());

            statement.executeUpdate();

            //removerAlunosTurma(conexao, turma);
            //if (turma.getAluno() != null)
            //	inserirAlunosTurma(conexao, turma);

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(e);
        } finally {
            conexao.close();
        }
    }

    public boolean removerAlunosGrupo(int idGrupo, List<Usuario> alunos) throws SQLException {
        boolean ok = false;
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM tb_grupo_aluno WHERE ID_GRUPO = ? AND ID_ALUNO = ?");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());

            for (Usuario usuario : alunos) {

                statement.setInt(1, idGrupo);
                statement.setInt(2, usuario.getIdUsuario());


                ok = statement.execute();

            }
            ok=true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexao.close();
        }

        return ok;
    }

    public Grupo buscaGrupo(int idGrupo) throws SQLException {
        Connection conexao = null;


        try {
            conexao = ConexaoUtil.getConexao();

            StringBuilder sql = new StringBuilder();
            sql.append(" select id_grupo, status_grupo, projeto, semestre, ano, dt_inclusao");
            sql.append(" from tb_grupo ");
            sql.append(" where  id_tgrupo = ? ");

            PreparedStatement statement = conexao.prepareStatement(sql.toString());
            statement.setInt(1, idGrupo);
            ResultSet resultSet = statement.executeQuery();
            Grupo grupo = new Grupo();
            while (resultSet.next()) {
                grupo.setId(resultSet.getInt("id_grupo"));
                grupo.setStatus(resultSet.getString("status_grupo"));
                grupo.setProjeto(resultSet.getString("projeto"));
                grupo.setSemestre(resultSet.getInt("semestre"));
                grupo.setAno(resultSet.getInt("ano"));

                Date dataInclusao = resultSet.getDate("dt_inclusao");
                grupo.setDtInclusao(dataInclusao);

                grupo.setAlunos(buscarAlunosGrupo(conexao, resultSet.getInt("id_grupo")));


            }
            return grupo;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return grupo;
    }
}
