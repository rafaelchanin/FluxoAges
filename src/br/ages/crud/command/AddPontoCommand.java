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

		String pagina = request.getServletPath() + "?" + request.getQueryString();

		pontoBO = new PontoBO();
		//if (isEdit != null && !"".equals(isEdit)) {
			//proxima = "main?acao=
		//} else {
			proxima = "main?acao=registrarPonto";
		//}
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

			StringBuilder msg = new StringBuilder();
			boolean isValido = true;

			// Valida data de entrada
			if (dataEntrada == null) {
				msg.append(MensagemContantes.MSG_ERR_CADASTRO_PONTO_DATA_INVALIDA + "<br>");
				isValido = false;
			}
			// Valida se data de entrada é vaior que data de saida e se não
			// existe data de saida
			if (!pontoBO.validaDataPonto(ponto)) {
				msg.append(MensagemContantes.MSG_ERR_CADASTRO_PONTO_DATA_INVALIDA + "<br>");
				isValido = false;
			}
			// Valida se existe responsável e se a senha bate.
			if (ponto.getResponsavel().getIdUsuario() != 0	& !usuarioBO.validaUsuarioResponsavel(ponto.getResponsavel().getUsuario(), senhaResponsavel)) {
				msg.append(MensagemContantes.MSG_ERR_CADASTRO_PONTO_SENHA_RESPONSAVEL_INVALIDA + "<br>");
				//TESTE
				if (isEdit.equals("true")) {
					proxima= "main?acao=registrarPonto&id_ponto="+  Integer.valueOf(request.getParameter("idPonto"))+"&isEdit=true" ;
				}
				//FIM DO TESTE
				isValido = false;
			}

			// O ponto do aluno só é valido se tiver Aluno, DataEntrada,
			// DataSaida e Responsável
			if (isValido != false) {
				if (isEdit != null && !"".equals(isEdit)) { // edita ponto
					int idPonto = Integer.valueOf(request.getParameter("idPonto"));
					ponto.setIdPonto(idPonto);
					pontoBO.editaPonto(ponto);
					request.setAttribute("msgSucesso",
							MensagemContantes.MSG_SUC_EDITA_PONTO.replace("?", ponto.getAluno().getNome()));
					proxima = "main?acao=listaAluno";
				} else { // cadastro ponto
					pontoBO.cadastrarPonto(ponto);
					request.setAttribute("msgSucesso",
							MensagemContantes.MSG_SUC_CADASTRO_PONTO.replace("?", ponto.getAluno().getNome()));
					proxima = "main?acao=listaAluno";
				}
			} else {
				throw new NegocioException(msg.toString());
			}

		} catch (NegocioException | PersistenciaException |

				ParseException e) {
			request.setAttribute("msgErro", e.getMessage());
			e.printStackTrace();
		}

		return proxima;
	}
}
