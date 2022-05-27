package steps;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

import automacao.api.support.domain.Usuario;
import io.cucumber.docstring.DocString;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class UsuarioSteps {
	
	private static final String CREATE_USUARIO_ENDPOINT = "/user";
	private static final String CONSULT_USUARIO_ENDPOINT = "/user/{name}";
	
	private Map<String, String> esperadoUsuario = new HashMap<String, String>();

	private Usuario usuario;
	
	@Quando("faço um POST para {word} com os seguintes valores:")
	public void façoUmPOSTParaVUserComOsSeguintesValores(String endpoint, Map<String, String> user) {
		esperadoUsuario = user;

		given().
			body(user).
		when().
			post(endpoint).
		then().
			statusCode(HttpStatus.SC_OK);
	}

	@Então("quando faço um GET para {word}, o usuário criado é retornado")
	public void quandoFaçoUmGETParaVUserRaimundogOUsuárioCriadoÉRetornado(String endpoint) {
		when().
			get("https://petstore3.swagger.io/api" + endpoint).
		then().
			body("username", is(esperadoUsuario.get("username")));
	}

	@Quando("faço um POST para {word} com a seguinte docstring:")
	public void façoUmPOSTParaVUserComASeguinteDocstring(String endpoint, DocString docString) {
		esperadoUsuario.put("username", "theUser");
		given().
			body(docString.getContent()).
		when().
			post(endpoint).
		then().
			statusCode(HttpStatus.SC_OK);
	}

	
	@Quando("crio um usuário")
	public void crioUmUsuário() {
		//Faz um build da classe. Assim pega os valores default.
		//Caso queira passar uma informação específica para um elemento fica assim:
		//usuario = Usuario.builder().elemento(valor).build();
		usuario = Usuario.builder().build();
		given().
			body(usuario).
		when().
			post(CREATE_USUARIO_ENDPOINT).
		then().
			statusCode(HttpStatus.SC_OK);
	}

	@Então("o usuário é salvo no sistema")
	public void oUsuárioÉSalvoNoSistema() {
		given().
		//Setando a variável do endpoint. 	
			pathParam("name", usuario.getUsername()).
		when().
			get(CONSULT_USUARIO_ENDPOINT).
		then().
			statusCode(HttpStatus.SC_OK).
			body("username", is(usuario.getUsername()));
	}
	
}
