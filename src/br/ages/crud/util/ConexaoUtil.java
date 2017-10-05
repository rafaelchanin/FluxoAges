package br.ages.crud.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 
 * @author cassio trindade
 *
 */
public class ConexaoUtil {

	private static ResourceBundle configDB = ResourceBundle.getBundle(Constantes.AMBIENTE_PROPERTIES);

	public static Connection getConexao() throws ClassNotFoundException, SQLException {
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("jdbc/MySQLAges");
			Connection conn = ds.getConnection();
			return conn;
		} catch (NamingException e) {
			e.printStackTrace();
		}


		return null;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getConexao());
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
	}
}
