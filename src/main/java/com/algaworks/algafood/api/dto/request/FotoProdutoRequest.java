package com.algaworks.algafood.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.annotation.FileContentType;
import com.algaworks.algafood.core.validation.annotation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoRequest {
	
	@NotNull
	@FileSize(max = "20MB")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	private MultipartFile multipartFile;
	
	@NotBlank
	private String descricao;
	
}