package br.ages.crud.command;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import br.ages.crud.bo.TimeBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Time;


public class ListaTimesCommand implements Command{
	private String proxima;
	private TimeBO timeBO;
	@Override
	public String execute(HttpServletRequest request){
		this.timeBO = new TimeBO();
		proxima = "time/listTime.jsp";

		try {
			List<Time> listaTimes = timeBO.listarTimes();
			request.setAttribute("listaTimes", listaTimes);
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}
		return proxima;
	}

}
