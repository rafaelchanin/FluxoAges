package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.AulaBO;
import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;

public class RelatorioHorasCommand implements Command {

	private String proxima;
	private UsuarioBO usuarioBO;
	private List<Usuario> usuarios;
	private PontoBO pontoBO;
	private TurmaBO turmaBO;
	private AulaBO aulaBO;
	private ArrayList<ResumoPonto> listaPontos;

	@Override
	public String execute(HttpServletRequest request) throws SQLException, ParseException {
		pontoBO = new PontoBO();
		turmaBO = new TurmaBO();
		aulaBO = new AulaBO();
		usuarioBO = new UsuarioBO();
		usuarios = new ArrayList<>();

		//proxima = "aluno/listPontoHora.jsp";
		proxima = "turma/relatorioHoras.jsp";

		String dataEntrada, dataSaida;
		Date dataEntradaDate, dataSaidaDate;
		List<Turma> turmasAtivas;
		try {

			//Integer idUsuario = Integer.valueOf(request.getParameter("id_usuario"));
			Integer idUsuario=1;
			 //dataEntrada = request.getParameter("dtEntrada");
			 //dataSaida = request.getParameter("dtSaida");
			 turmasAtivas = turmaBO.listarTurmasAtivas();
			 
			 for (Turma turma : turmasAtivas) {
				 String presencasTurma = "";
				 for (Usuario aluno : turma.getAlunos()) {
					 String presencasAluno = "";
					 for (Presenca presenca : aulaBO.listarPresencasAlunoTurma(aluno.getIdUsuario(), turma.getId())) {
						if (presencasAluno.equals("")) {
							presencasAluno = aluno.getIdUsuario() + "-" + aluno.getMatricula() + ":" + presenca.getIdAula();
						}
						else {
							presencasAluno += "," + presenca.getIdAula(); 
						}
					 }
					 if (!presencasAluno.equals(""))
						 presencasTurma += presencasAluno + ";";
				 }
				 turma.setPresencas(presencasTurma);
			 }
		
			//if (dataSaida == null || dataEntrada == null ) {
	   		//	 dataEntradaDate = Util.getDataInicialSemestre();
			//	 dataSaidaDate = new Date();
			//	 dataEntrada = Util.dateToString(dataEntradaDate);
			//	 dataSaida = Util.dateToString(dataSaidaDate);
			//}else {
			//	dataEntradaDate = Util.stringToDate(dataEntrada);
			//	dataSaidaDate = Util.stringToDate(dataSaida);
			//}
					
			//usuarios = usuarioBO.listarUsuarioAlunos();

			//request.setAttribute("usuarios", usuarios);

			//listaPontos = pontoBO.listaPontoAlunos(idUsuario, dataEntradaDate, dataSaidaDate);
			//listaPontosInvalidos = pontoBO.listaPontoInvalidoAlunos(idUsuario);
			
			//pode ser retirado no futuro
			//request.setAttribute("listaPontos", listaPontos);
			request.setAttribute("turmasAtivas", turmasAtivas);

			//request.setAttribute("listaPontosInvalidos", listaPontosInvalidos);
			
			//request.setAttribute("totalHorasAluno", pontoBO.calculatotalHorasAluno(listaPontos));
			//request.setAttribute("totalHorasInvalidoAluno", pontoBO.calculatotalHorasAluno(listaPontosInvalidos));
			if (request.getAttribute("nomeTurma") == null || request.getAttribute("nomeTurma").equals(""))
				request.setAttribute("nomeTurma", "");
			if (request.getAttribute("mesString") == null || request.getAttribute("mesString").equals(""))
				request.setAttribute("mesString", "");
			//request.setAttribute("dtSaida", dataSaida);
		
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
