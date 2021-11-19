package com.algaworks.algafood.core.email;

import com.algaworks.algafood.core.email.EmailProperties.Implementacao;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.MockEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        Implementacao implementacao = emailProperties.getImpl();
        EnvioEmailService envioEmailService = null;

        if (implementacao.equals(Implementacao.MOCK)) {
            envioEmailService = new MockEnvioEmailService();
        } else if (implementacao.equals(Implementacao.SANDBOX)) {
            envioEmailService = new SandboxEnvioEmailService();
        } else if (implementacao.equals(Implementacao.SMTP)) {
            envioEmailService = new SmtpEnvioEmailService();
        }

        return envioEmailService;
    }

}
