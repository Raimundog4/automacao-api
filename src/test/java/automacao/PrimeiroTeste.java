package automacao;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PrimeiroTeste {
	
	@BeforeClass
	public static void setUp() {
		/* Caso o teste falhe o Rest Assured irá habilitar o Log de resposta de requisições*/
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	public void testeListaMetadadosUsuario() {
		/* A Ação */
		when()
			.get("https://reqres.in/api/users?page=2")
		.then()
		.statusCode(200)
		.statusCode(HttpStatus.SC_OK)
		.body("page", is(2))
		.body("total", is(12))
		.body("data[0].email", is("michael.lawson@reqres.in"))
		.body("data.size", is(6))
		.body("data", is(notNullValue()));
	}
	
	@Test
	public void testeCriarUsuarioComSucesso() {
		/* Preparando a ação */
		given()
		.body("{\"name\": \"Johnson\", \"job\": \"motorista\"}")
		.contentType(ContentType.JSON)
//			.params("name", "Johnson")
//			.params("job", "motorista")
		
		.when()
			.post("https://reqres.in/api/users")
		.then()
			.statusCode(HttpStatus.SC_CREATED)
			.body("name", is("Johnson"))
			.body("id", is(notNullValue()))
			.body("createdAt", is(notNullValue()))
			.body("job", is("motorista"));
	}
	
	@Test
	public void testeMetadadosUsuarioUnico() {
		when()
		.get("https://reqres.in/api/users/2")
	.then()
	.statusCode(HttpStatus.SC_OK)
	.body("data.id", is(2))
	.body("data.email", is("janet.weaver@reqres.in"))
	.body("data.first_name", is("Janet"));
	}
	
}
