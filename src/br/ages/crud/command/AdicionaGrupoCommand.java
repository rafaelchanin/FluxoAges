package br.ages.crud.command;

import br.ages.crud.bo.*;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Grupo;
import br.ages.crud.model.Stakeholder;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class AdicionaGrupoCommand implements Command {

    private String proxima;

    private GrupoBO grupoBO;

    private ArquivoBO arquivoBO;

    private UsuarioBO usuarioBO;

    private Usuario usuario;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, ParseException, PersistenciaException {
        grupoBO = new GrupoBO();
        proxima = "main?acao=telaGrupo";

        String ano = request.getParameter("ano");
        String[] alunos = request.getParameterValues("alunos");
        String semestre = request.getParameter("semestre");
        String projeto = request.getParameter("projeto");
        String statusGrupo = request.getParameter("statusGrupo");
        int numSemestre=0;


        if (semestre.equals("primeiro"))
            numSemestre=1;
        if (semestre.equals("segundo"))
            numSemestre=2;

        Grupo grupo = new Grupo();

        try {
            // cria o array de usuarios com o array de String do request
            if (alunos != null) {
                ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
                for (String s : alunos) {
                    String[] temp = s.split(" ");
                    usuario = new Usuario();
                    usuario.setIdUsuario(Integer.valueOf(temp[0]));
                    usuario.setMatricula(temp[1]);
                    usuarios.add(usuario);
                }
                grupo.setAlunos(usuarios);
            }

		/*	for (IdNomeUsuarioDTO i : alunos) {
				usuario = new Usuario();
				usuario.setIdUsuario(i.getId());
				usuario.setMatricula(i.getMatricula());
				usuarios.add(usuario);
			}*/


            if (!ano.equals(""))
                grupo.setAno(Integer.valueOf(ano));
                grupo.setSemestre(numSemestre);
            if (!projeto.equals(""))
                grupo.setProjeto(projeto);
            grupo.setStatus(statusGrupo);

            grupo.setDtInclusao(new Date());

            //boolean isValido = projetoBO.validarProjeto(projeto);
            boolean isValido = grupoBO.validarGrupo(grupo);

            if (isValido) {
                grupoBO.cadastrarGrupo(grupo);
                request.getSession().setAttribute("grupo", grupo);
                //proxima = "turma/listTurma.jsp";
                proxima = "main?acao=listaGrupos";
                request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_GRUPO.replace("?", grupo.getAno()+" / "+ grupo.getSemestre()+" - Projeto "+ grupo.getProjeto()));
            } else {
                request.setAttribute("msgErro", MensagemContantes.MSG_ERR_GRUPO_DADOS_INVALIDOS);
            }

        } catch (NegocioException e) {
            request.setAttribute("msgErro", e.getMessage());
            e.printStackTrace();
        }

        return proxima;
    }
}
