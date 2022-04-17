package automacao.api.teste;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import automacao.api.dominio.Usuario;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;

//Se a API rejeitar açguma propriedade por ser para ea desconhecida, o jackson permite ignorar
public class RegistroTeste extends BaseTeste{

	/* Criando esse reponseEspecification para checar o statusCode de todos
	 * os testes, pois são esperados o mesmo status de todos eles.
	 * O ideal é colocar aqui testes que contenha o mesmo statusCode.*/
	@BeforeClass
	public static void setUpRegistro() {
		RestAssured.responseSpecification = new ResponseSpecBuilder()
				.expectStatusCode(HttpStatus.SC_BAD_REQUEST)
				.build();
	}
	
	private static final String REGISTRO_USUARIO_ENDPOINT = "/register";
	private static final String LOGIN_USUARIO_ENDPOINT = "/login";
	
	@Test
	public void testeNaoEfetuarRegistroComSenhaFaltando() {
		Usuario usuario = new Usuario();
		usuario.setEmail("raimundog4@hotmail.com");
		
		given()
			//.contentType(ContentType.JSON)
			.body(usuario)
		.when()
			.post(REGISTRO_USUARIO_ENDPOINT)
		.then()
//			.statusCode(HttpStatus.SC_BAD_REQUEST)
			.body("error", is("Missing password"));
	}
	
	@Test
	public void testeNaoEfetuarLoginComSenhaFaltando() {
		Usuario usuario = new Usuario();
		usuario.setEmail("raimundog4@hotmail.com");
		
		given().
			body(usuario).
		when().
			post(LOGIN_USUARIO_ENDPOINT).
		then().
			body("error", is("Missing password"));
	}
	
}
