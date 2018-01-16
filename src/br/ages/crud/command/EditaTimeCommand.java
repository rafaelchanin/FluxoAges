package br.ages.crud.command;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.TimeBO;

import br.ages.crud.model.Time;

import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class EditaTimeCommand implements Command{

	private TimeBO timeBO;
	private Usuario usuario;
	private String proxima;

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		timeBO =  new TimeBO();
		proxima = "time/editTime.jsp";
		String idTime = request.getParameter("idTime");
		String ano = request.getParameter("ano");
		String[] alunos = request.getParameterValues("alunos");
		String semestre = request.getParameter("semestre");
		String orientador = request.getParameter("orientador");
		String statusTime = request.getParameter("statusTime");
		String projeto = request.getParameter("projeto");
		String primeiroDia = request.getParameter("dataPrimeiroDia");
		SimpleDateFormat textFormat = new SimpleDateFormat("dd/MM/yyyy");



		int id = Integer.parseInt(idTime);

		int numSemestre=0;
		if (semestre.equals("primeiro"))
			numSemestre=1;
		else
			numSemestre=2;

		Time time = new Time();

		try{
			if (alunos != null) {
				ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
				for (String s : alunos) {
					String[] temp = s.split(" ");
					usuario = new Usuario();
					usuario.setIdUsuario(Integer.valueOf(temp[0]));
					usuario.setMatricula(temp[1]);
					usuarios.add(usuario);
				}
				time.setAlunos(usuarios);
			}


			time.setId(id);

			if (!ano.equals(""))
				time.setAno(Integer.valueOf(ano));
			time.setSemestre(numSemestre);
			if (!orientador.equals(""))
				time.setOrientador(Integer.valueOf(orientador));
			time.setStatus(statusTime);
			if (!projeto.equals(""))
				time.setProjeto(Integer.valueOf(projeto));
			time.setDtInclusao(new Date());
			if(!primeiroDia.equals(""))
				time.setPrimeiroDia(textFormat.parse(primeiroDia));

			boolean isValido = timeBO.validarTime(time);

			if (isValido) {
				timeBO.editarTime(time);
				request.getSession().setAttribute("time", time);
				proxima = "main?acao=listaTimes";
				request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_CADASTRO_TIME.replace("?", time.getAno()+" / "+ time.getSemestre()+" - Projeto "+ time.getProjeto()+" - "+ time.getOrientador()));
			} else {
				request.setAttribute("msgErro", MensagemContantes.MSG_ERR_TIME_DADOS_INVALIDOS);
			}

		}catch(Exception e){
			request.setAttribute("msgErro", e.getMessage());
		}		

		return proxima;		
	}

}
