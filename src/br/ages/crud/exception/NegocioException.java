package br.ages.crud.exception;

import br.ages.crud.util.MensagemContantes;

/**
 * Exceções de negócio
 * 
 * @author Cássio Trindade
 *
 */
public class NegocioException extends Exception{

	private static final long serialVersionUID = 1L;

	public NegocioException(Exception e) {
		super(traslateErrorMessage(e.getMessage()));
	}

	public NegocioException(String msg) {
		super(traslateErrorMessage(msg));
	}
	
	
    public static String traslateErrorMessage(String msg){
        msg = msg.contains("Email has already been taken") ? MensagemContantes.MSG_ERR_EMAIL_GITLAB : msg; 
        msg = msg.contains("Username has already been taken") ? MensagemContantes.MSG_ERR_USUARIO_GITLAB : msg; 
		return msg;
	}

}
