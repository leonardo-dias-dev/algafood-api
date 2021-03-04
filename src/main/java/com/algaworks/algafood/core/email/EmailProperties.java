package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {
	
	@NotNull
	private String remetente;
	
	private Implementacao impl = Implementacao.MOCK;
	
	private Sandbox sandbox = new Sandbox();
	
	public enum Implementacao {
		MOCK, SMTP, SANDBOX
	}

	@Getter
	@Setter
	public class Sandbox {
		
		private String destinatario;
		
	}
	
}
