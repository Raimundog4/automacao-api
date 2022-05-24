package automacao.api.teste;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;

import automacao.api.dominio.Usuario;

public class TesteUsuario extends TesteBase{

	/* final quer dizer que uma vez que ela for definida n�o ter� como mud�-la programaticamente */
	private static final String LISTA_USUARIOS_ENDPOINTS = "/users";
	private static final String CRIAR_USUARIO_ENDPOINTS = "/user";
	private static final String MOSTRAR_USUARIO_ENDPOINTS = "/users/{userId}";
	
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
	
	@Test
	public void testeMetadadosUsuarioUnicoDeserializacao() {
		Usuario usuario = given().
			pathParam("userId", "2").//Define uma vari�vel de ambiente
		when().
//			get("/users/{userId}").//Usando a vari�vel de ambiente
			get(MOSTRAR_USUARIO_ENDPOINTS).
		then().
			statusCode(HttpStatus.SC_OK).
			/* Estou dizendo para extrair o body usando um jsonPath.
			 * E voc� vai pegar um objeto, onde o path dele � data, e o
			 * objeto � Usuario.class */
		extract().//Ele n�o reconhece o campo ID - ler no Usario.java
			body().jsonPath().getObject("data", Usuario.class);
			assertThat(usuario.getEmail(), containsString("@reqres.in"));
			assertThat(usuario.getName(), is("Janet"));
			assertThat(usuario.getLastName(), is("Weaver"));
	}
	
	@Test
	public void testeTamanhoDosItensMostradosIgualAoPerPage() {
		int paginaEsperada = 2;
		int perPageEsperado = retornaPerPageEsperado(paginaEsperada);
		
		given().
			params("page", paginaEsperada).
		when().
			get(LISTA_USUARIOS_ENDPOINTS).
		then().
			statusCode(HttpStatus.SC_OK).
			body(
					"page", is(paginaEsperada),
					/* Usando o Groovy collection */
					"data.size()", is(perPageEsperado),
					/* Comando do Groovy. O it � de iterator (faz um For de todos
					 * os itens do data). Cada item ser� um it. O comando startsWith() �
					 * para verificar se o dado do item come�a com um valor determinado */
					"data.findAll { it.avatar.startsWith('https://reqres.in/img/faces') }.size()", is(perPageEsperado)
					//Acima verifica se o size est� correto.
			);
	}

	private int retornaPerPageEsperado(int pagina) {
		/* Funcionalidade Extract.
		 * Como quero o perPage e ele � um inteiro, declaro uma vari�vel int.
		 * Para garantir que foi feito a solicita��o j� colocamos um statusCode.
		 * Ap�s a solicita��o n�s informamos no extract que queremos extrair o body,
		 * quero extrair um path e o declaramos. Ele retorna e coloca na vari�vel. */
		int perPageEsperado = given().
				params("page", pagina).//H� duas possibilidades de usar o param
				//Uma � s� o param (adicionar somente um)
				//Outra � usar params (mais de um)
				//Ainda tem o .and
				//param().and.param()
			when().
				get(LISTA_USUARIOS_ENDPOINTS).
			then().
				statusCode(HttpStatus.SC_OK).
			extract().
				path("per_page");
		return perPageEsperado;
	}	
}
