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

import br.ages.crud.command.*;
import org.apache.log4j.Logger;

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
		comandos.put("horasProfessor", new RelatorioHorasProfessoresCommand());
		
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

		//COMANDOS DE GRUPO
		comandos.put("telaGrupo", new CreateScreenGrupoCommand());
		comandos.put("listaGrupos", new ListaGruposCommand());
		comandos.put("adicionaGrupo", new AdicionaGrupoCommand());
		comandos.put("editaGrupo", new EditaGrupoCommand());
		
		//COMANDOS DE TURMA
		
		comandos.put("telaTurma", new CreateScreenTurmaCommand());
		comandos.put("listaTurmas", new ListaTurmasCommand());
		comandos.put("adicionaTurma", new AdicionaTurmaCommand());
		comandos.put("editaTurma", new EditaTurmaCommand());
		comandos.put("removeTurma", new RemoveTurmaCommand());
		comandos.put("registrarAulas", new RegistraAulasTurmaCommand());
		comandos.put("adicionaAulas", new AdicionaAulasCommand());
		comandos.put("registrarChamada", new RegistraChamadaCommand());
		comandos.put("adicionaChamada", new AdicionaChamadasCommand());
		comandos.put("relatorioHoras", new RelatorioHorasCommand());
	
		
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

		//COMANDOS PROFESSOR
		comandos.put("listaRelatoriosSemanaisProfessor", new ListarRelatorioSemanalProfessor());
		comandos.put("aceitarRelatorio", new AceitarRelatorioCommand());
		comandos.put("rejeitarRelatorio", new RecusarRelatorioCommand());
		comandos.put("visualizarRelatorio", new VisualizarRelatorioCommand());

		//COMANDOS ALUNO
		comandos.put("registrarPonto", new CreateScreenPontoCommand());
		comandos.put("adicionaPonto", new AddPontoCommand());
		comandos.put("listaAluno", new ListaAlunoCommand());
		
		comandos.put("listaPontoHora", new ListPontoTotalHorasCommand());
		comandos.put("adicionaSkill", new AddSkillCommand());
		comandos.put("skills", new CreateScreenSkillCommand());
		comandos.put("removerPontoAluno", new RemoverPontoAlunoCommand());

		comandos.put("horasAluno", new HorasAlunoCommand());

		//COMANDOS RELATORIO SEMANAL
		comandos.put("relatorioSemanal", new CreateScreenRelatorioSemanalCommand());
		comandos.put("adicionaRelatorio", new EnviarRelatorioCommand());
		comandos.put("listaRelatorios", new ListarRelatorioSemanalCommand());
		comandos.put("editaRelatorio", new EditaRelatorioCommand());

		//COMANDOS EQUIPAMENTOS
		comandos.put("listaEquipamentos", new ListarEquipamentosCommand());
		comandos.put("telaEquipamento", new CreateScreenEquipamentoCommand());
		comandos.put("listaTiposEquipamentos", new ListarTipoEquipamentoCommand());
		comandos.put("addEquipamento", new AddEquipamentoCommand());
		comandos.put("editEquipamento", new EditEquipamentoCommand());
		comandos.put("removerEquipamento", new RemoverEquipamentoCommand());
		comandos.put("telaTipoEquipamento", new CreateScreenTipoEquipamentoCommand());
		comandos.put("addTipoEquipamento", new AddTipoEquipamentoCommand());
		comandos.put("removerTipoEquipamento", new RemoverTipoEquipamentoCommand());
		comandos.put("editTipoEquipamento", new EditTipoEquipamentoCommand());
		//LISTAR ALUNOS + NOTEBOOKS
		comandos.put("listAlunosEquipamentos", new ListAlunosEquipamentosCommand());
		comandos.put("entregaEquipamento", new EntregaEquipamentoCommand());
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

