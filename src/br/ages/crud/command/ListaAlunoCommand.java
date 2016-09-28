package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PontoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Ponto;
import br.ages.crud.util.Util;

public class ListaAlunoCommand implements Command {
private String proxima;
private PontoBO pontoBO;
	@Override
	public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
		this.pontoBO= new PontoBO();
		proxima = "aluno/listAluno.jsp";
		
		String dataEntrada, dataSaida;
		Date dataEntradaDate, dataSaidaDate;

		try {
			
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
			
			List<Ponto> listaAlunos = pontoBO.listarAlunos(dataEntradaDate, dataSaidaDate);
			request.setAttribute("listaAlunos", listaAlunos);
			request.setAttribute("dtEntrada", dataEntrada);
			request.setAttribute("dtSaida", dataSaida);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}
		return proxima;
	}

}
