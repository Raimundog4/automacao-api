package automacao.api.support.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder //Para poder gerar uma inst�ncia da classe.
public class Usuario {

	@Builder.Default //Aqui definimos um valor padr�o para o elemento, mas pode setar o valor no teste que ir� alterar aqui.
	private int id = 11;
	@Builder.Default
	private String username = "raimundog6";
	@Builder.Default
	private String firstName = "Raimundo";
	@Builder.Default
	private String lastName = "Johnson";
	@Builder.Default
	private String email = "johnson@hotmail.com";
	@Builder.Default
	private String password = "12345678";
	@Builder.Default
	private String phone = "98899888888";
	@Builder.Default
	private int userStatus = 1;
	
}
