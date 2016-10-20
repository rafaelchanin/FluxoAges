package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PontoBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Ponto;
import br.ages.crud.model.StatusPonto;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.util.Util;

public class AddPontoCommand implements Command {


	private String proxima;
	private UsuarioBO usuarioBO;
	private PontoBO pontoBO;

	@SuppressWarnings("unused")
	@Override
	public String execute(HttpServletRequest request) throws SQLException, NegocioException {

		String pagina = request.getServletPath() +"?"+request.getQueryString();
		
		pontoBO = new PontoBO();
		proxima = "main?acao=registrarPonto";

		String idAluno = request.getParameter("idAluno");
		String idResponsavel = request.getParameter("idResponsavel");
		String dataEntradaString = request.getParameter("dtEntradaRegistro");
		String dataSaidaString = request.getParameter("dtSaidaRegistro");
		String senhaResponsavel = request.getParameter("senhaResponsavel");
		String isEdit = request.getParameter("isEdit");

		try {

			Ponto ponto = new Ponto();

			usuarioBO = new UsuarioBO();
			Usuario aluno = usuarioBO.buscaUsuarioId(Integer.parseInt(idAluno));
			ponto.setAluno(aluno);

			usuarioBO = new UsuarioBO();
			Usuario responsavel = usuarioBO.buscaUsuarioId(Integer.parseInt(idResponsavel));
			ponto.setResponsavel(responsavel);

			Date dataEntrada = Util.stringToDateTime(dataEntradaString);
			Date dataSaida = dataSaidaString.equals("") ? null : Util.stringToDateTime(dataSaidaString);

			ponto.setDataEntrada(dataEntrada);
			ponto.setDataSaida(dataSaida);

			StatusPonto statusPonto = pontoBO.validaStatusPonto(responsavel, senhaResponsavel, dataSaidaString);
			ponto.setStatus(statusPonto);

			boolean isValido;
			if (dataEntrada == null)
				isValido = false;
			else {
				if (dataSaida != null) {
					isValido = pontoBO.validaPonto(ponto, responsavel, senhaResponsavel);
				} else {
					isValido = true;
				}
			}

			if (isValido != false) {
				proxima = "main?acao=listaAluno";
				if (isEdit != null && !"".equals(isEdit)) { // edita ponto
					int idPonto = Integer.valueOf(request.getParameter("idPonto"));
					ponto.setIdPonto(idPonto);
					pontoBO.editaPonto(ponto);
					request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDITA_PONTO.replace("?", ponto.getAluno().getNome()));
				} else { // cadastro ponto
					pontoBO.cadastrarPonto(ponto);
					request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_PONTO.replace("?", ponto.getAluno().getNome()));
				}
			} else {
					//request.setAttribute("msgErro", MensagemContantes.MSG_ERR_CADASTRO_PONTO.replace("?", ponto.getAluno().getNome()));
				throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PONTO.replace("?", ponto.getAluno().getNome()));
			
			}
		} catch (NegocioException | PersistenciaException | ParseException e) {
			request.setAttribute("msgErro", e.getMessage());
			e.printStackTrace();
		}
		
		return proxima;
	}
}
