package br.ages.crud.command;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Status;
import br.ages.crud.model.TipoUsuario;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class AddUserCommand implements Command {


	private String proxima;

	private UsuarioBO usuarioBO;

	@Override
	public String execute(HttpServletRequest request) {
		usuarioBO = new UsuarioBO();
		proxima = "main?acao=telaUser";

		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String matricula = request.getParameter("matricula");
		String usuario = request.getParameter("usuario");
		String senha = request.getParameter("senha");
		String statusUsuario = request.getParameter("statusUsuario"); 
		String perfilAcesso = request.getParameter("perfilAcesso");
		String tipoUsuario = request.getParameter("tipoUsuario");
		String usuarioGitLab = request.getParameter("usuarioGitLab");
		
		try {
			Usuario user = new Usuario();
			user.setNome(nome);
			user.setEmail(email);
			user.setMatricula(matricula);
			user.setUsuario(usuario);
			user.setSenha(senha);
			user.setStatus(Status.valueOf(statusUsuario));
			user.setPerfilAcesso(PerfilAcesso.valueOf(perfilAcesso));
			user.setUsuarioGitLab(usuarioGitLab);
			TipoUsuario tUser = new TipoUsuario();
			
			tUser = usuarioBO.consultaTipoUsuario(tipoUsuario);
			user.setTipoUsuario(tUser);
			
			boolean isValido = usuarioBO.validaUsuario(user);
			if (isValido == false) {
				request.setAttribute("msgErro", MensagemContantes.MSG_ERR_USUARIO_DADOS_INVALIDOS);
			} else { // cadastro de pessoa com sucesso
				if (!user.getUsuarioGitLab().equals(""))
					usuarioBO.addUsuarioGitLab(user);
				usuarioBO.cadastraUsuario(user);
				proxima = "main?acao=listUser";
				request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_USUARIO.replace("?", user.getNome()));

			}
		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
			
		}

		return proxima;
	}
}
