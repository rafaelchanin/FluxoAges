package br.ages.crud.command;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;


@ManagedBean
@SessionScoped
public class LoginCommand implements Command {

	private UsuarioBO usuarioBO;

	private String proxima;

	private Util util;
	
	private Usuario user = new Usuario();
	
	private Usuario usuarioDTO;

	@Override
	public String execute(HttpServletRequest request) {
		// seta a mesma pagina, para o caso de erro/exceção
		proxima = "login.jsp";
		usuarioBO = new UsuarioBO();
		util = new Util();

		String usuario = request.getParameter("login");
		String senha = request.getParameter("senha");

		usuarioDTO = new Usuario(usuario, senha);

		try {
			if (getUsuario() != null) {
				request.getSession().setAttribute("usuarioSessao", getUsuario());
				request.getSession().setAttribute("versao", util.getVersion());
				proxima = "main?acao=listaProjetos";
			
			}
		} catch (NegocioException e) {
			request.setAttribute("msgErro", e.getMessage());
			return proxima;
		}
		
		return proxima;
	}
	
	public Usuario getUsuario() throws NegocioException{
		
		return usuarioBO.validaLogin(usuarioDTO);
		
	}
	
}
