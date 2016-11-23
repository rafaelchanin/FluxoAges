package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.ResumoPonto;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;

public class ListPontoTotalHorasCommand implements Command {

	private String proxima;
	private UsuarioBO usuarioBO;
	private List<Usuario> usuarios;
	private PontoBO pontoBO;
	private ArrayList<ResumoPonto> listaPontos;

	@Override
	public String execute(HttpServletRequest request) throws SQLException, ParseException {
		pontoBO = new PontoBO();
		usuarioBO = new UsuarioBO();
		usuarios = new ArrayList<>();
		//proxima = "aluno/listPontoHora.jsp";
		proxima = "aluno/horasVisaoProfessor.jsp";
		String dataEntrada, dataSaida;
		Date dataEntradaDate, dataSaidaDate;

		try {

			Integer idUsuario = Integer.valueOf(request.getParameter("id_usuario"));
			
			 dataEntrada = request.getParameter("dtEntrada");
			 dataSaida = request.getParameter("dtSaida");
		
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

			listaPontos = pontoBO.listaPontoAlunos(idUsuario, dataEntradaDate, dataSaidaDate);
			//listaPontosInvalidos = pontoBO.listaPontoInvalidoAlunos(idUsuario);
			
			//pode ser retirado no futuro
			request.setAttribute("listaPontos", listaPontos);
			//request.setAttribute("listaPontosInvalidos", listaPontosInvalidos);
			
			//request.setAttribute("totalHorasAluno", pontoBO.calculatotalHorasAluno(listaPontos));
			//request.setAttribute("totalHorasInvalidoAluno", pontoBO.calculatotalHorasAluno(listaPontosInvalidos));
			request.setAttribute("dtEntrada", dataEntrada);
			request.setAttribute("dtSaida", dataSaida);
		
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
