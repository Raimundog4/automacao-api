package automacao.api.teste;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;

import automacao.api.dominio.Usuario;

public class TesteUsuario extends TesteBase{

	/* final quer dizer que uma vez que ela for definida não terá como mudá-la programaticamente */
	private static final String LISTA_USUARIOS_ENDPOINTS = "/users";
	private static final String CRIAR_USUARIO_ENDPOINTS = "/user";
	private static final String MOSTRAR_USUARIO_ENDPOINTS = "/users/{userId}";
	
	@Test
	public void testeListaMetadadosUsuario() {
		given()
		/* Aqui estou setando o parâmetro Page, que possibilita */
			.params("page", "2")
		
		/* A Ação */
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
		/* Preparando a ação */
		Usuario usuario = new Usuario("Johnson", "motorista", null, null);
		given()
		
		/* Como o contentType é um JSON ele tenta mapear a classe Usuario em um JSON e fazer
		 * a serialização */
	//	.contentType(ContentType.JSON)
		/* Para refatorar o 'set' foi criada uma classe Usuario com os parâmetros
		 * e foi intanciada nesse método para que serializasse o objeto */
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
			pathParam("userId", "2").//Define uma variável de ambiente
		when().
//			get("/users/{userId}").//Usando a variável de ambiente
			get(MOSTRAR_USUARIO_ENDPOINTS).
		then().
			statusCode(HttpStatus.SC_OK).
			/* Estou dizendo para extrair o body usando um jsonPath.
			 * E você vai pegar um objeto, onde o path dele é data, e o
			 * objeto é Usuario.class */
		extract().//Ele não reconhece o campo ID - ler no Usario.java
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
					/* Comando do Groovy. O it é de iterator (faz um For de todos
					 * os itens do data). Cada item será um it. O comando startsWith() é
					 * para verificar se o dado do item começa com um valor determinado */
					"data.findAll { it.avatar.startsWith('https://reqres.in/img/faces') }.size()", is(perPageEsperado)
					//Acima verifica se o size está correto.
			);
	}

	private int retornaPerPageEsperado(int pagina) {
		/* Funcionalidade Extract.
		 * Como quero o perPage e ele é um inteiro, declaro uma variável int.
		 * Para garantir que foi feito a solicitação já colocamos um statusCode.
		 * Após a solicitação nós informamos no extract que queremos extrair o body,
		 * quero extrair um path e o declaramos. Ele retorna e coloca na variável. */
		int perPageEsperado = given().
				params("page", pagina).//Há duas possibilidades de usar o param
				//Uma é só o param (adicionar somente um)
				//Outra é usar params (mais de um)
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
