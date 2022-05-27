package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class Config {

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		baseURI = "https://petstore3.swagger.io";
		basePath = "/api/v3";
		
//		authentication = basic("usuario", "senha");
		
		requestSpecification = new RequestSpecBuilder().
				addHeader("Authorization", getToken()).
				setContentType("application/json").
				build();
		
		responseSpecification = new ResponseSpecBuilder().
				expectContentType("application/json").
				build();
	}
	
	private String getToken() {
		return "Teste";
	}
	
	@After
	public void tearDown() {
		RestAssured.reset();
	}
	
}
