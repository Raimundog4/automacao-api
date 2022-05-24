package automacao.api.teste;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

public class TesteBase {

	@BeforeClass
	public static void setUp() {
		/* Caso o teste falhe o Rest Assured irá habilitar o Log de resposta de requisições*/
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		/* Como a URI será a mesma para todos, aqui o Rest Assured permite que salvemos.
		 * Assim também o path, que é o /api */
		baseURI = "https://reqres.in";
		basePath = "/api";
		
		/* Cria um requestSpecification para todos os testes, e que ele vai criar um
		 * contentType JSON  e que ele vai criar esse classe - o build faz isso */
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.build();
		
		/* Cria um responseSpecification para todos os testes.
		 * Ele checa se os testes estão retornando o valor esperado nos itens declarados aqui */
		RestAssured.responseSpecification = new ResponseSpecBuilder()
//				.expectStatusCode(HttpStatus.SC_OK) - Como nem todos os testes são esperados o code 200, o teste irá falhar
				.expectContentType(ContentType.JSON)
				.build();
	}
	
}
