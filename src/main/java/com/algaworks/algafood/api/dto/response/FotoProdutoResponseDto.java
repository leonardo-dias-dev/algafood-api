package com.algaworks.algafood.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoResponseDto {
	
	private String nomeArquivo;
	
	private String descricao;
	
	private String contentType;
	
	private Long tamanho;
	
}
