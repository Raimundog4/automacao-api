package steps;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

import automacao.api.support.domain.Usuario;
import io.cucumber.docstring.DocString;
import io.cucumber.java.pt.Ent�o;
import io.cucumber.java.pt.Quando;

public class UsuarioSteps {
	
	private static final String CREATE_USUARIO_ENDPOINT = "/user";
	private static final String CONSULT_USUARIO_ENDPOINT = "/user/{name}";
	
	private Map<String, String> esperadoUsuario = new HashMap<String, String>();

	private Usuario usuario;
	
	@Quando("fa�o um POST para {word} com os seguintes valores:")
	public void fa�oUmPOSTParaVUserComOsSeguintesValores(String endpoint, Map<String, String> user) {
		esperadoUsuario = user;

		given().
			body(user).
		when().
			post(endpoint).
		then().
			statusCode(HttpStatus.SC_OK);
	}

	@Ent�o("quando fa�o um GET para {word}, o usu�rio criado � retornado")
	public void quandoFa�oUmGETParaVUserRaimundogOUsu�rioCriado�Retornado(String endpoint) {
		when().
			get("https://petstore3.swagger.io/api" + endpoint).
		then().
			body("username", is(esperadoUsuario.get("username")));
	}

	@Quando("fa�o um POST para {word} com a seguinte docstring:")
	public void fa�oUmPOSTParaVUserComASeguinteDocstring(String endpoint, DocString docString) {
		esperadoUsuario.put("username", "theUser");
		given().
			body(docString.getContent()).
		when().
			post(endpoint).
		then().
			statusCode(HttpStatus.SC_OK);
	}

	
	@Quando("crio um usu�rio")
	public void crioUmUsu�rio() {
		//Faz um build da classe. Assim pega os valores default.
		//Caso queira passar uma informa��o espec�fica para um elemento fica assim:
		//usuario = Usuario.builder().elemento(valor).build();
		usuario = Usuario.builder().build();
		given().
			body(usuario).
		when().
			post(CREATE_USUARIO_ENDPOINT).
		then().
			statusCode(HttpStatus.SC_OK);
	}

	@Ent�o("o usu�rio � salvo no sistema")
	public void oUsu�rio�SalvoNoSistema() {
		given().
		//Setando a vari�vel do endpoint. 	
			pathParam("name", usuario.getUsername()).
		when().
			get(CONSULT_USUARIO_ENDPOINT).
		then().
			statusCode(HttpStatus.SC_OK).
			body("username", is(usuario.getUsername()));
	}
	
}
