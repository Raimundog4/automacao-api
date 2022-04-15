package automacao.api.teste;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public class BaseTeste {

	@BeforeClass
	public static void setUp() {
		/* Caso o teste falhe o Rest Assured ir� habilitar o Log de resposta de requisi��es*/
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		/* Como a URI ser� a mesma para todos, aqui o Rest Assured permite que salvemos.
		 * Assim tamb�m o path, que � o /api */
		baseURI = "https://reqres.in";
		basePath = "/api";
		
		/* Cria um requestSpecification para todos os testes, e que ele vai criar um
		 * contentType JSON  e que ele vai criar esse classe - o build faz isso */
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.build();
	}
	
}
