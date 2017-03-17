package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PeriodoBO;
import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Periodo;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;

public class RegistraAulasTurmaCommand implements Command {

	private String proxima;
	private UsuarioBO usuarioBO;
	private List<Usuario> usuarios;
	private PontoBO pontoBO;
	private TurmaBO turmaBO;
	private PeriodoBO periodoBO;
	private ArrayList<ResumoPonto> listaPontos;

	@Override
	public String execute(HttpServletRequest request) throws SQLException, ParseException, PersistenciaException {
		pontoBO = new PontoBO();
		turmaBO = new TurmaBO();
		usuarioBO = new UsuarioBO();
		usuarios = new ArrayList<>();
		periodoBO = new PeriodoBO();
		//proxima = "aluno/listPontoHora.jsp";
		proxima = "aulasSemestre/registraAulasSemestre.jsp";
		ArrayList<Periodo> periodos = new ArrayList<Periodo>();
		String dataEntrada, dataSaida;
		Date dataEntradaDate, dataSaidaDate;
		List<Turma> turmasAtivas;
		try {

			//Integer idUsuario = Integer.valueOf(request.getParameter("id_usuario"));
			Integer idUsuario=1;
			 dataEntrada = request.getParameter("dtEntrada");
			 dataSaida = request.getParameter("dtSaida");
			 turmasAtivas = turmaBO.listarTurmasAtivas();
			 periodos = periodoBO.listaPeriodo();
			if (dataSaida == null || dataEntrada == null ) {
	   			 dataEntradaDate = Util.getDataInicialSemestre();
				 dataSaidaDate = new Date();
				 dataEntrada = Util.dateToString(dataEntradaDate);
				 dataSaida = Util.dateToString(dataSaidaDate);
			}else {
				dataEntradaDate = Util.stringToDate(dataEntrada);
				dataSaidaDate = Util.stringToDate(dataSaida);
			}
			
					
			//usuarios = usuarioBO.listarUsuarioAlunos();

			//request.setAttribute("usuarios", usuarios);

			// listaPontos = pontoBO.listaPontoAlunos(idUsuario, dataEntradaDate, dataSaidaDate);
			//listaPontosInvalidos = pontoBO.listaPontoInvalidoAlunos(idUsuario);
			
			//pode ser retirado no futuro
			// request.setAttribute("listaPontos", listaPontos);
			request.setAttribute("turmasAtivas", turmasAtivas);
			request.setAttribute("periodos", periodos);
			

			//request.setAttribute("listaPontosInvalidos", listaPontosInvalidos);
			
			//request.setAttribute("totalHorasAluno", pontoBO.calculatotalHorasAluno(listaPontos));
			//request.setAttribute("totalHorasInvalidoAluno", pontoBO.calculatotalHorasAluno(listaPontosInvalidos));
			if (request.getAttribute("nomeTurma") == null || request.getAttribute("nomeTurma").equals(""))
				request.setAttribute("nomeTurma", "");
			// request.setAttribute("dtSaida", dataSaida);
		
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
