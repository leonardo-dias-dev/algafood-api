package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockEnvioEmailService implements EnvioEmailService {
    @Override

    public void enviar(Mensagem mensagem) {
        log.info("[MOCK E-MAIL] Enviando e-mail para {}", mensagem.getDestinatarios());
    }

}
