package br.ages.crud.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.ages.crud.command.AddPontoCommand;
import br.ages.crud.command.AddSkillCommand;
import br.ages.crud.command.AddStakeholderCommand;
import br.ages.crud.command.AddUserCommand;
import br.ages.crud.command.AdicionaAulasCommand;
import br.ages.crud.command.AdicionaProjetoCommand;
import br.ages.crud.command.AdicionaTimeCommand;
import br.ages.crud.command.AdicionaTurmaCommand;
import br.ages.crud.command.Command;
import br.ages.crud.command.CreateScreenPontoCommand;
import br.ages.crud.command.CreateScreenProjectCommand;
import br.ages.crud.command.CreateScreenSkillCommand;
import br.ages.crud.command.CreateScreenStakeholderCommand;
import br.ages.crud.command.CreateScreenTimeCommand;
import br.ages.crud.command.CreateScreenTurmaCommand;
import br.ages.crud.command.CreateScreenUserCommand;
import br.ages.crud.command.EditStakeholderCommand;
import br.ages.crud.command.EditUserCommand;
import br.ages.crud.command.EditaProjetoCommand;
import br.ages.crud.command.EditaTimeCommand;
import br.ages.crud.command.EditaTurmaCommand;
import br.ages.crud.command.ListPontoTotalHorasCommand;
import br.ages.crud.command.ListStakeholdersCommand;
import br.ages.crud.command.ListUserCommand;
import br.ages.crud.command.ListaAlunoCommand;
import br.ages.crud.command.ListaProjetosCommand;
import br.ages.crud.command.ListaTimesCommand;
import br.ages.crud.command.ListaTurmasCommand;
import br.ages.crud.command.LoginCommand;
import br.ages.crud.command.LogoutCommand;
import br.ages.crud.command.RegistraAulasTurmaCommand;
import br.ages.crud.command.RegistraChamadaCommand;
import br.ages.crud.command.RemoveProjetoCommand;
import br.ages.crud.command.RemoveStakeholderCommand;
import br.ages.crud.command.RemoveTurmaCommand;
import br.ages.crud.command.RemoveUserCommand;
import br.ages.crud.command.RemoverPontoAlunoCommand;
import br.ages.crud.command.SenhaCommand;
import br.ages.crud.command.UploadProjetoCommand;
import br.ages.crud.command.ValidaPontoHoraCommand;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.LogParametrosSession;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

	Logger logger = Logger.getLogger("servlet.MainServlet");
	private static final long serialVersionUID = 1L;
	private Map<String, Command> comandos = new HashMap<String, Command>();

	@Override
	public void init() throws ServletException {
		
		comandos.put("login", new LoginCommand());
		comandos.put("logout", new LogoutCommand());
		comandos.put("recuperarSenha", new SenhaCommand());
		
		//COMANDOS DE USUARIO
		
		comandos.put("telaUser", new CreateScreenUserCommand());
		comandos.put("listUser", new ListUserCommand());
		comandos.put("addUser", new AddUserCommand());
		comandos.put("editUser", new EditUserCommand());
		comandos.put("removerUsuario", new RemoveUserCommand());
		
		//COMANDOS DE PROJETO
		
		comandos.put("telaProjeto", new CreateScreenProjectCommand());
		comandos.put("listaProjetos", new ListaProjetosCommand());
		comandos.put("adicionaProjeto", new AdicionaProjetoCommand());
		comandos.put("editaProjeto", new EditaProjetoCommand());
		comandos.put("removeProjeto", new RemoveProjetoCommand());
		comandos.put("uploadArquivoProjeto", new UploadProjetoCommand());
		
		//COMANDOS DE TURMA
		
		comandos.put("telaTurma", new CreateScreenTurmaCommand());
		comandos.put("listaTurmas", new ListaTurmasCommand());
		comandos.put("adicionaTurma", new AdicionaTurmaCommand());
		comandos.put("editaTurma", new EditaTurmaCommand());
		comandos.put("removeTurma", new RemoveTurmaCommand());
		comandos.put("registrarAulas", new RegistraAulasTurmaCommand());
		comandos.put("adicionaAulas", new AdicionaAulasCommand());
		comandos.put("registrarChamada", new RegistraChamadaCommand());
	
		
		//COMANDOS DE TIME
		comandos.put("telaTime", new CreateScreenTimeCommand());
		comandos.put("listaTimes", new ListaTimesCommand());
		comandos.put("adicionaTime", new AdicionaTimeCommand());
		comandos.put("editaTime", new EditaTimeCommand());
		
		//COMANDOS DE STAKEHOLDER
		
		comandos.put("telaStakeholder", new CreateScreenStakeholderCommand());
		comandos.put("listaStakeholders", new ListStakeholdersCommand());
		comandos.put("addStakeholder", new AddStakeholderCommand());
		comandos.put("editaStakeholder", new EditStakeholderCommand());
		comandos.put("removeStakeholder", new RemoveStakeholderCommand());

		//COMANDOS ALUNO
		comandos.put("registrarPonto", new CreateScreenPontoCommand());
		comandos.put("adicionaPonto", new AddPontoCommand());
		comandos.put("listaAluno", new ListaAlunoCommand());
		comandos.put("validaPontoHora", new ValidaPontoHoraCommand());
		comandos.put("listaPontoHora", new ListPontoTotalHorasCommand());
		comandos.put("adicionaSkill", new AddSkillCommand());
		comandos.put("skills", new CreateScreenSkillCommand());
		comandos.put("removerPontoAluno", new RemoverPontoAlunoCommand());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse reponse) throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String proxima = null;

		try {
			Command comando = verificarComando(acao);
			proxima = comando.execute(request);
			Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSessao");
			if(usuario != null)
				logger.debug("User: " +usuario.getUsuario() + " - comando " + comando.toString() + " acao: " +acao );
		} catch (NegocioException | SQLException | ParseException | PersistenciaException e) {
			request.setAttribute("msgErro", e.getMessage());
		}
	
		LogParametrosSession.logParametros(request);
		
		request.getRequestDispatcher(proxima).forward(request, reponse);
		
	}

	private Command verificarComando(String acao) {
		Command comando = null;
		for (String key : comandos.keySet()) {
			if (key.equalsIgnoreCase(acao)) {
				comando = comandos.get(key);
			}
		}
		return comando;
	}
}

