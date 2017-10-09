package br.ages.crud.command;

import br.ages.crud.bo.*;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.*;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateScreenGrupoCommand implements Command {

    private String proxima;

    private ProjetoBO projetoBO;
    private GrupoBO grupoBO;
    private UsuarioBO usuarioBO;
    private StakeholderBO stakeholderBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException {
        //TODO utilizar stakeholderBO.listaStakeholders ap?s a implementa??o do mesmo
        proxima = "main?acao=listaGrupos";
        Usuario currentUser = (Usuario)request.getSession().getAttribute("usuarioSessao");

        try{

            if( !currentUser.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);
            String isEdit = request.getParameter("isEdit");

            if (isEdit != null && !"".equals(isEdit)) {
                proxima = "turma/editGrupo.jsp";
                grupoBO = new GrupoBO();
                usuarioBO = new UsuarioBO();
                int idGrupo = Integer.parseInt(request.getParameter("id_grupo"));
                Grupo grupo = grupoBO.buscarGrupo(idGrupo);

                //List<Usuario> alunosTurma = turma.getAluno();
				/*List<Usuario> usuarios = usuarioBO.listarUsuario();


				for(int i = 0; i < usuarioProjeto.size(); i++){
					for(int j = 0; j < usuarios.size(); j++){
						if(usuarios.get(j).getIdUsuario() == usuarioProjeto.get(i).getIdUsuario()){
							usuarios.remove(j);
							break;
						}
					}
				}*/
                List<IdNomeUsuarioDTO> alunosProjeto = new ArrayList<>();
                for (Usuario aluno : grupo.getAlunos()) {
                    IdNomeUsuarioDTO dto = new IdNomeUsuarioDTO();
                    dto.setId(aluno.getIdUsuario());
                    dto.setMatricula(aluno.getMatricula());
                    dto.setNome(aluno.getNome());
                    alunosProjeto.add(dto);
                }
                List<IdNomeUsuarioDTO> alunosDisponiveis = usuarioBO.alunosElegiveis();
                request.setAttribute("grupo", grupo);
                request.setAttribute("alunosProjeto", alunosProjeto);
                request.setAttribute("alunos", alunosDisponiveis);
                //	request.setAttribute("listaStakeholders", stakeholders);


            } else {
                //TODO implementar StakeholderBO e DAO pra fazer essa parte
                proxima = "grupo/addGrupo.jsp";

                usuarioBO = new UsuarioBO();
                List<IdNomeUsuarioDTO> alunos = usuarioBO.alunosElegiveis();

                Calendar hoje = Calendar.getInstance();
                String ano = Integer.toString(hoje.get(hoje.YEAR));
                System.out.println(ano);
                request.setAttribute("ano", ano);

                request.setAttribute("alunos", alunos);
            }
        } catch(Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
