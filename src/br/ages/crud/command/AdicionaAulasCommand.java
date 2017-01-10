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
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Stakeholder;
import br.ages.crud.model.StatusProjeto;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.util.Util;

@MultipartConfig
public class AdicionaAulasCommand implements Command {

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
		proxima = "main?acao=listaTurmas";
		String dias = request.getParameter("dias");
		String idTurma = request.getParameter("turma");
		
		try {
		int id = Integer.parseInt(idTurma);
		String[] aulas = dias.split("[,]");
		ArrayList<Aula> diasAulas = new ArrayList<>();
		turma.setId(id);
		for (String s : aulas) {
			Date data = new Date();
			data = Util.stringToDate(s);
			Aula aula = new Aula();
			aula.setData(data);
			aula.setIdTurma(id);
			aula.setDtInclusao(new Date());
			aula.setStatus("AULA");
			aula.setObservacao("");
			diasAulas.add(aula);
		}
		turma.setAulas(diasAulas);
		
				
		//	boolean isValido = projetoBO.validarProjeto(projeto);
			boolean isValido=true;
			if (isValido) {
			aulaBO.cadastrarDiasAulasTurma(turma);
			proxima = "main?acao=listaTurmas";
				request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_AULAS.replace("?", "nomeTurma"));
			} else {
				request.setAttribute("msgErro", MensagemContantes.MSG_ERR_CADASTRO_AULAS);
			}

		} catch (NegocioException e) {
			request.setAttribute("msgErro", e.getMessage());
			e.printStackTrace();
		}

		return proxima;
	}



}
