package br.ages.crud.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import br.ages.crud.bo.ArquivoBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Stakeholder;
import br.ages.crud.model.StatusProjeto;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Constantes;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.util.Util;

public class EditaTurmaCommand implements Command{
	
	private TurmaBO turmaBO;
	
	private UsuarioBO usuarioBO;

	private Usuario usuario;
	
	private ArquivoBO arquivoBO;
	
	private String proxima;

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		turmaBO =  new TurmaBO();
		proxima = "turma/editTurma.jsp";
		String idTurma = request.getParameter("idTurma");
		String ano = request.getParameter("ano");
		String[] alunos = request.getParameterValues("alunos");
		String semestre = request.getParameter("semestre");
		String ages = request.getParameter("ages");
		String numero = request.getParameter("numero");
		String statusTurma = request.getParameter("statusTurma");

		int id = Integer.parseInt(idTurma);
		
		int numSemestre=0;

		if (semestre.equals("Primeiro"))
			numSemestre=1;
		if (semestre.equals("Segundo"))
			numSemestre=2;
		
		Turma turma = new Turma();
		
		try{
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
							turma.setAlunos(usuarios);
						}
						
						//turma.setId(idTurma);
						
					/*	for (IdNomeUsuarioDTO i : alunos) {
							usuario = new Usuario();
							usuario.setIdUsuario(i.getId());
							usuario.setMatricula(i.getMatricula());
							usuarios.add(usuario);
						}*/
					
						turma.setId(id);
						
						if (!ano.equals(""))
							turma.setAno(Integer.valueOf(ano));
						turma.setSemestre(numSemestre);
						if (!ages.equals(""))
							turma.setAges(Integer.valueOf(ages));
						if (!numero.equals(""))
							turma.setNumero(Integer.valueOf(numero));
						turma.setStatus(statusTurma);
						
						turma.setDtInclusao(new Date());
						
						//boolean isValido = projetoBO.validarProjeto(projeto);
						boolean isValido = turmaBO.validarTurma(turma);
						
						if (isValido) {
							turmaBO.editaTurma(turma);
							request.getSession().setAttribute("turma", turma);
							proxima = "turma/listTurma.jsp";
							request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDIT_TURMA.replace("?", numero));
						} else {
							request.setAttribute("msgErro", MensagemContantes.MSG_ERR_TURMA_DADOS_INVALIDOS);
						}

		}catch(Exception e){
			request.setAttribute("msgErro", e.getMessage());
		}		
		
		return proxima;		
	}

}
