package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        Mensagem mensagem = Mensagem.builder()
                .assunto(String.format("%s - Pedido cancelado", pedido.getRestaurante().getNome()))
                .corpo("emails/pedido-cancelado.html")
                .destinatario(pedido.getCliente().getEmail())
                .variavel("pedido", pedido)
                .build();

        envioEmailService.enviar(mensagem);
    }

}
