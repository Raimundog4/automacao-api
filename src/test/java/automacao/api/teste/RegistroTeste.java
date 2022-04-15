package automacao.api.teste;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.apache.http.HttpStatus;
import org.junit.Test;

import automacao.api.dominio.Usuario;

//Se a API rejeitar açguma propriedade por ser para ea desconhecida, o jackson permite ignorar
public class RegistroTeste extends BaseTeste{

	private static final String REGISTRO_USUARIO_ENDPOINT = "/register";
	
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
			.statusCode(HttpStatus.SC_BAD_REQUEST)
			.body("error", is("Missing password"));
	}
	
}
