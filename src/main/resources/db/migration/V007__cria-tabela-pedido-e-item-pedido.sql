create table pedido (
	id bigint not null auto_increment,
    
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,
    
    data_criacao datetime not null,
    data_confirmacao datetime null,
    data_cancelamento datetime null,
    data_entrega datetime null,
    
    status varchar(10) not null,
    
    endereco_cep varchar(9) not null,
    endereco_logradouro varchar(100) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(60) null,
    endereco_bairro varchar(60) not null,
    endereco_cidade_id bigint not null,
    
    forma_pagamento_id bigint not null,
    restaurante_id bigint not null,
    usuario_cliente_id bigint not null,
    
    primary key (id),
    
    constraint fk_pedido__cidade foreign key (endereco_cidade_id) references cidade (id),
    constraint fk_pedido__forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id),
    constraint fk_pedido__restaurante foreign key (restaurante_id) references restaurante (id),
    constraint fk_pedido__usuario foreign key (usuario_cliente_id) references usuario (id)
) engine=InnoDB default charset=utf8;

create table item_pedido (
	id bigint not null auto_increment,
    
    quantidade smallint(6) not null,
    
    preco_unitario decimal(10, 2) not null,
    preco_total decimal(10, 2) not null,
    
    observacao varchar(255) null,
    
    pedido_id bigint not null,
    produto_id bigint not null,
    
    primary key (id),
    
    constraint fk_item_pedido__pedido foreign key (pedido_id) references pedido (id),
    constraint fk_item_pedido__produto foreign key (produto_id) references produto (id)
) engine=InnoDB default charset=utf8;