package br.ages.managebeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ages.crud.model.Usuario;
import br.ages.crud.util.Util;

@ManagedBean
@SessionScoped
public class Utilidades {

	public String getVersao(){

		String v = Util.getVersion();
		String ver = v.equals("1.0.${build.number}") ? "homologação"  : v;
		
		return ver;
	}
	
	public Usuario getUsuario(){
		Usuario usuario = new Usuario();
		return usuario;
	}
}
