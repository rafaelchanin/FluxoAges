package br.ages.crud.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		String idTurma = request.getParameter("idTurma");
		proxima = "main?acao=telaTurma&id_turma=" + idTurma + "&isEdit=true";
		
		String ano = request.getParameter("ano");
		String[] alunos = request.getParameterValues("alunos");
		String semestre = request.getParameter("semestre");
		String ages = request.getParameter("ages");
		String numero = request.getParameter("numero");
		String statusTurma = request.getParameter("statusTurma");

		int id = Integer.parseInt(idTurma);
		
		int numSemestre=0;

		if (semestre.equals("primeiro"))
			numSemestre=1;
		if (semestre.equals("segundo"))
			numSemestre=2;
		
		Turma turma = new Turma();
		
		try{
			Turma existente = turmaBO.buscarTurma(id);
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
							//turma.setAlunos(alunosTela);
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
						boolean isValidoTurma = turmaBO.validarTurma(turma);
						boolean isValidoAlunos = turmaBO.validarAlunos(id, usuariosAdicionar, usuariosRemover);
						
						if (isValidoTurma && isValidoAlunos) {
							turmaBO.editaTurma(turma);
							if (!usuariosAdicionar.isEmpty())
								turmaBO.inserirAlunosTurma(id, usuariosAdicionar);
							if (!usuariosRemover.isEmpty())
								turmaBO.removerAlunosTurma(id, usuariosRemover);
							request.getSession().setAttribute("turma", turma);
							proxima = "main?acao=listaTurmas";
							request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDIT_TURMA.replace("?", turma.getAno()+" / "+ turma.getSemestre()+" - AGES "+ turma.getAges()+" - "+ turma.getNumero()));
						} else {
							request.setAttribute("msgErro", MensagemContantes.MSG_ERR_TURMA_DADOS_INVALIDOS);
						}

		}catch(Exception e){
			request.setAttribute("msgErro", e.getMessage());
		}		
		
		return proxima;		
	}

}
