package automacao.api.dominio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Propriedade do Jackson que digo para que ele ignore propriedade desconhecidas.
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

	@JsonAlias("first_name")//Propriedade do Jackson que informa que uma variável pode ser outro nome
	private String name;
	private String job;
	private String email;
	private String password;
	@JsonAlias("last_name")
	private String lastName;
	
	public Usuario() {
	}
	
	public Usuario(String name, String job, String email, String password) {
		this.name = name;
		this.job = job;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getJob() {
		return job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	//Get é usado para serialização, transformando um objeto em um JSon
	//Set é usado para deserialização, transformando um JSon em um objeto
	
}
