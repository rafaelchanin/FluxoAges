package br.ages.crud.test;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.GitlabAPIException;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class TestGitLab {
	private static final String TEST_URL = "http://www.tools.ages.pucrs.br";
	private static final String TEST_TOKEN = "cT5xMaSvdvxBrwvY4EFK";
	static GitlabAPI api;

	@Before
	public void setup() throws IOException {
		api = GitlabAPI.connect(TEST_URL, TEST_TOKEN);
		/*try {
		api.dispatch().with("cassio.trindade", "INVALID").with("password", rand).to("session", GitlabUser.class);
		} catch (ConnectException e) {
			Assume.assumeNoException("GITLAB not running on '" + TEST_URL + "', skipping...", e);
		} catch (GitlabAPIException e) {
			final String message = e.getMessage();
			if (!message.equals("{\"message\":\"401 Unauthorized\"}")) {
				throw new AssertionError("Expected an unauthorized message", e);
			} else if (e.getResponseCode() != 401) {
				throw new AssertionError("Expected 401 code", e);
			}
		}*/
	}

	@Test
	public void testAllProjects() throws IOException {
		api.getAllProjects();
	}
	
	
	public static void main(String[] args) {
		api = GitlabAPI.connect(TEST_URL, TEST_TOKEN);
		
		
		
		try {
		List<GitlabProject> gitlabProjects = api.getAllProjects();
		
		gitlabProjects.forEach(g -> System.out.println(g.getName()));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
