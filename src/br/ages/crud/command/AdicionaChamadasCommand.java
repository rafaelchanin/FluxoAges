package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.ArquivoBO;
import br.ages.crud.bo.AulaBO;
import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.bo.StakeholderBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Aula;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Stakeholder;
import br.ages.crud.model.StatusProjeto;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.util.Util;

@MultipartConfig
public class AdicionaChamadasCommand implements Command {

	private String proxima;

	private AulaBO aulaBO;

	private ArquivoBO arquivoBO;
	private ProjetoBO projetoBO;
	private UsuarioBO usuarioBO;

	private Usuario usuario;

	private Stakeholder stakeholder;

	@Override
	public String execute(HttpServletRequest request) throws SQLException, ParseException, PersistenciaException {
		aulaBO = new AulaBO();
		projetoBO = new ProjetoBO();
		Turma turma = new Turma();
		proxima = "main?acao=registrarChamada";
		String StringResultado  = request.getParameter("resultado");
		String StringTurma = request.getParameter("turma");
		String[] tempTurma = StringTurma.split("[|]");
		
		String nomeTurma = tempTurma[0];
		int idTurma = Integer.parseInt(tempTurma[1]);
		
		String[] resultados = StringResultado.split("[,]");
		
		try {
		List<Presenca> presencasBanco = aulaBO.listarPresencasTurma(idTurma);
		List<Presenca> presencasTela = new ArrayList<>();
		List<Presenca> aulasExcluir = new ArrayList<>();
		List<Presenca> aulasAdicionar = new ArrayList<>();
		for (String s : resultados) {
			String temp[] = s.split("[:]");
			String tempUsuario = temp[0];
			String tempAula = temp[1];
			String usuario[] = tempUsuario.split("[-]");
			String tempUsuarioId = usuario[0];
			String tempUsuarioMatricula = usuario[1];
			Presenca presenca = new Presenca();
			Usuario aluno = new Usuario();
			aluno.setIdUsuario(Integer.parseInt(tempUsuarioId));
			aluno.setMatricula(tempUsuarioMatricula);
			presenca.setIdTurma(idTurma);
			presenca.setStatus("PRESENTE");
			presenca.setIdAula(Integer.parseInt(tempAula));
			presenca.setAluno(aluno);
			presencasTela.add(presenca);
			boolean teste = true;
			for (Presenca pBancoo : presencasBanco) {
				if (presenca.toString().equals(pBancoo.toString())) {
					teste=false;
					break;
				}
			}
			if (teste == true)
				aulasAdicionar.add(presenca);
		}
		
		for (Presenca pBanco : presencasBanco) {
			boolean verT = false;
			for (Presenca pTela : presencasTela) {
				if (pBanco.toString().equals(pTela.toString())) {
					verT = true;
					break;
				}
			}
			if (verT==false)
				aulasExcluir.add(pBanco);
		}
		
			
		//	boolean isValido = projetoBO.validarProjeto(projeto);
			boolean isValido=true;
			if (isValido) {
				if (aulasAdicionar.size() != 0)
					aulaBO.cadastrarPresencas(aulasAdicionar);
				if (aulasExcluir.size() != 0)
					aulaBO.excluirPresencas(aulasExcluir);
					proxima = "main?acao=registrarChamada";
					request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_AULAS.replace("?", nomeTurma));
			} else {
				request.setAttribute("msgErro", MensagemContantes.MSG_ERR_CADASTRO_AULAS);
			}

		} catch (NegocioException e) {
			request.setAttribute("msgErro", e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("nomeTurma", nomeTurma);
		return proxima;
	}



}
