package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.AulaBO;
import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.TimePontoDTOBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.TimePontoDTO;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;

public class RelatorioHorasCommand implements Command {

	private String proxima;
	private UsuarioBO usuarioBO;
	private AulaBO aulaBO;
	private List<Usuario> usuarios;
	private TimePontoDTOBO timePontoDTOBO;
	private TurmaBO turmaBO;
	private List<TimePontoDTO> listaPontos;
	private HashMap<Integer,Integer> horasEsperadas;

	@Override
	public String execute(HttpServletRequest request) throws SQLException, ParseException, PersistenciaException {
		timePontoDTOBO = new TimePontoDTOBO();
		usuarioBO = new UsuarioBO();
		aulaBO = new AulaBO();
		usuarios = new ArrayList<>();
		turmaBO = new TurmaBO();
		horasEsperadas = new HashMap<>();

		proxima = "turma/relatorioHoras.jsp";
		//proxima = "aulasSemestre/registraAulasSemestre.jsp";

		String dataEntrada, dataSaida;
		Date dataEntradaDate, dataSaidaDate;

		try {
			
	
			//int weekNumber2 = data2.get(weekFields.weekOfWeekBasedYear());
			//Depois temos que tirar essa requisicao de usuário :D
			Integer idUsuario = 0;
			
			
			//Como tava antes	
			listaPontos = timePontoDTOBO.listarTimes();

			//Pegar Primeiro dia de Aula de Cada Time
			ArrayList<Integer> timeId = new ArrayList<>();
			for(TimePontoDTO time: listaPontos){
				timeId.add(time.getId());
			}

			LocalDate hoje = LocalDate.now();
			WeekFields weekFields = WeekFields.of(Locale.getDefault());
			int weekNumberHoje = hoje.get(weekFields.weekOfWeekBasedYear());
			int semestre = 0;
			if (hoje.getMonthValue() <=6)
				semestre = 1;
			else
				semestre = 2;

			for(Integer time: timeId) {
				LocalDate primeiraAula = aulaBO.primeiroDia(semestre, hoje.getYear(),time);

				int weekNumberPrimeiraAula = primeiraAula.get(weekFields.weekOfWeekBasedYear());

				int horaEsperada = (weekNumberHoje - weekNumberPrimeiraAula + 1) * 4 * 60;

				horasEsperadas.put(time,horaEsperada);
			}
			
			//Sets
			request.setAttribute("listaPontos", listaPontos);
			request.setAttribute("horasEsperadas", horasEsperadas);
		
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
