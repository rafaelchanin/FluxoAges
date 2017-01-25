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
		proxima = "main?acao=registrarAulas";
		String dias = request.getParameter("dias");
		String idTurma = request.getParameter("turma");
		String[] tempId = idTurma.split("[|]");
		String nomeTurma = tempId[0];
		try {
		int id = Integer.parseInt(tempId[1]);
		String[] aulas = dias.split("[,]");
		List<Aula> diasAulas = new ArrayList<>();
		turma.setId(id);
		ArrayList<Aula> aulasTurmas = new ArrayList<>();
		List<Aula> aulasExistentes = aulaBO.listarDiasAulasTurma(id);
		List<Aula> aulasExcluir = new ArrayList<>();
		if (!aulas[0].equals("")) {
		for (String s : aulas) {
			Date data = new Date();
			data = Util.stringToDate(s);
			Aula aula = new Aula();
			aula.setData(data);
			aula.setIdTurma(id);
			aula.setDtInclusao(new Date());
			aula.setStatus("AULA");
			aula.setObservacao("");
			aulasTurmas.add(aula);
			boolean teste = false;
			for (Aula aulaVerificada : aulasExistentes) {
				if (aula.toString().equals(aulaVerificada.toString())) {
					teste = true;
					break;
				}
			}
			if (teste == false)
				diasAulas.add(aula);
			turma.setAulas(aulasTurmas);
		}
		} else 
			turma.setAulas(aulasTurmas);
		
		for (Aula aulaBanco : aulasExistentes) {
			boolean teste = false;
			for (Aula aulaTela : turma.getAulas()) {
				if (aulaBanco.toString().equals(aulaTela.toString())) {
					teste = true;
					break;
				}
			}
			if (teste == false)
				aulasExcluir.add(aulaBanco);
		}
		
				
		//	boolean isValido = projetoBO.validarProjeto(projeto);
			boolean isValido=true;
			if (isValido) {
			if(!diasAulas.isEmpty())
				aulaBO.cadastrarDiasAulas(diasAulas);
			if(!aulasExcluir.isEmpty())
				aulaBO.removerDiasAulas(aulasExcluir);
			
			proxima = "main?acao=registrarAulas";
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
