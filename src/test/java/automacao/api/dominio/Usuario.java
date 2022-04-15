package automacao.api.dominio;

public class Usuario {

	private String name;
	private String job;
	private String email;
	private String password;
	
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
	
	
}
