package automacao.api.teste;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import org.apache.http.HttpStatus;
import org.junit.Test;

import automacao.api.dominio.Usuario;

public class Usu�rioTeste extends BaseTeste{

	/* final quer dizer que uma vez que ela for definida n�o ter� como mud�-la programaticamente */
	private static final String LISTA_USUARIOS_ENDPOINTS = "/users";
	private static final String CRIAR_USUARIO_ENDPOINTS = "/user";
	
	@Test
	public void testeListaMetadadosUsuario() {
		given()
		/* Aqui estou setando o par�metro Page, que possibilita */
			.params("page", "2")
		
		/* A A��o */
		.when()
			.get(LISTA_USUARIOS_ENDPOINTS)
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
		/* Preparando a a��o */
		Usuario usuario = new Usuario("Johnson", "motorista", null, null);
		given()
		
		/* Como o contentType � um JSON ele tenta mapear a classe Usuario em um JSON e fazer
		 * a serializa��o */
	//	.contentType(ContentType.JSON)
		/* Para refatorar o 'set' foi criada uma classe Usuario com os par�metros
		 * e foi intanciada nesse m�todo para que serializasse o objeto */
		.body(usuario)
//		.body("{\"name\": \"Johnson\", \"job\": \"motorista\"}")

		.when()
			.post(CRIAR_USUARIO_ENDPOINTS)
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
		.get(LISTA_USUARIOS_ENDPOINTS + "/2")
	.then()
	.statusCode(HttpStatus.SC_OK)
	.body("data.id", is(2))
	.body("data.email", is("janet.weaver@reqres.in"))
	.body("data.first_name", is("Janet"));
	}
	
}
