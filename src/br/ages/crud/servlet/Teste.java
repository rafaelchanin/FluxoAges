package br.ages.crud.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Servlet implementation class Teste
 */
@WebServlet("/Teste")
public class Teste extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Teste() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.print(getProjectsGitLAB());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public String getProjectsGitLAB() {
		Client c = Client.create();
		WebResource wr = c.resource(
				"http://www.tools.ages.pucrs.br/api/v3/projects/all?&private_token=cT5xMaSvdvxBrwvY4EFK");
		return wr.get(String.class);
	}

	public String gsonStreamExample() {

		StringBuilder sb = new StringBuilder();
		try {			
			String barras = "\\";
			String json = getProjectsGitLAB().replaceAll("\"" , barras+"\\\"");
			JsonReader reader = new JsonReader(new StringReader(json));
			
			
			reader.beginObject();

			while (reader.hasNext()) {

				String name = reader.nextName();

				if (name.equals("id")) {

					sb.append(reader.nextString());

				} else if (name.equals("http_url_to_repo")) {

					sb.append(reader.nextInt());

				} else if (name.equals("created_at")) {

					sb.append(reader.nextString());

				} else {
					reader.skipValue(); // avoid some unhandle events
				}
			}

			reader.endObject();
			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();

	}

	public static void main(String[] args) {
		
	}
}
