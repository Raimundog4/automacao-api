package automacao.api;

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
		
		/* Como a URI será a mesma para todos, aqui o Rest Assured permite que salvemos.
		 * Assim também o path, que é o /api */
		baseURI = "https://reqres.in";
		basePath = "/api";
	}

	@Test
	public void testeListaMetadadosUsuario() {
		given()
		/* Aqui estou setando o parâmetro Page, que possibilita */
			.params("page", "2")
		
		/* A Ação */
		.when()
			.get("/users")
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
			.post("/users")
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
		.get("/users/2")
	.then()
	.statusCode(HttpStatus.SC_OK)
	.body("data.id", is(2))
	.body("data.email", is("janet.weaver@reqres.in"))
	.body("data.first_name", is("Janet"));
	}
	
}
