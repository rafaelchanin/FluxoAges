package br.ages.crud.command;

import br.ages.crud.bo.ArquivoBO;
import br.ages.crud.bo.GrupoBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.model.Grupo;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditaGrupoCommand implements Command {

    private GrupoBO grupoBO;

    private UsuarioBO usuarioBO;

    private Usuario usuario;

    private ArquivoBO arquivoBO;

    private String proxima;

    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        grupoBO =  new GrupoBO();
        String idGrupo = request.getParameter("idGrupo");
        proxima = "main?acao=telaGrupo&id_grupo=" + idGrupo + "&isEdit=true";

        String ano = request.getParameter("ano");
        String[] alunos = request.getParameterValues("alunos");
        String semestre = request.getParameter("semestre");
        String projeto = request.getParameter("projeto");
        String statusGrupo = request.getParameter("statusGrupo");

        int id = Integer.parseInt(idGrupo);

        int numSemestre=0;

        if (semestre.equals("primeiro"))
            numSemestre=1;
        if (semestre.equals("segundo"))
            numSemestre=2;

        Grupo grupo = new Grupo();

        try{
            Grupo existente = grupoBO.buscarGrupo(id);
            List<Usuario> usuariosAdicionar = new ArrayList<>();
            List<Usuario> usuariosRemover = new ArrayList<>();
            ArrayList<Usuario> alunosTela = new ArrayList<>();
            if (alunos != null) {
                for (String s : alunos) {
                    String[] temp = s.split(" ");
                    usuario = new Usuario();
                    usuario.setIdUsuario(Integer.valueOf(temp[0]));
                    usuario.setMatricula(temp[1]);
                    boolean ver=false;
                    alunosTela.add(usuario);
                    for (Usuario aluno : existente.getAlunos()) {
                        if(s.equals(aluno.getIdUsuario()+" "+aluno.getMatricula())) {
                            ver=true;
                            break;
                        }
                    }
                    if (ver==false)
                        usuariosAdicionar.add(usuario);
                }
                //turma.setAluno(alunosTela);
            }


            for (Usuario alunoBanco : existente.getAlunos()) {
                boolean tes=false;
                for (Usuario alunoTela : alunosTela) {
                    if (alunoBanco.getIdUsuario() == alunoTela.getIdUsuario()) {
                        tes=true;
                        break;
                    }
                }
                if (tes==false)
                    usuariosRemover.add(alunoBanco);
            }
            grupo.setId(id);
            if (!ano.equals(""))
                grupo.setAno(Integer.valueOf(ano));
            grupo.setSemestre(numSemestre);
            if (!projeto.equals(""))
                grupo.setProjeto(projeto);
            grupo.setStatus(statusGrupo);

            grupo.setDtInclusao(new Date());

            //boolean isValido = projetoBO.validarProjeto(projeto);
            boolean isValidoTurma = grupoBO.validarGrupo(grupo);

            if (isValidoTurma) {
                grupoBO.editaGrupo(grupo);
                if (!usuariosAdicionar.isEmpty())
                    grupoBO.inserirAlunosGrupo(id, usuariosAdicionar);
                if (!usuariosRemover.isEmpty())
                    grupoBO.removerAlunosGrupo(id, usuariosRemover);
                request.getSession().setAttribute("grupo", grupo);
                proxima = "main?acao=listaGrupos";
                request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDIT_GRUPO.replace("?", grupo.getAno()+" / "+ grupo.getSemestre()+" - Projetos "+ grupo.getProjeto()));
            } else {
                request.setAttribute("msgErro", MensagemContantes.MSG_ERR_GRUPO_DADOS_INVALIDOS);
            }

        }catch(Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
