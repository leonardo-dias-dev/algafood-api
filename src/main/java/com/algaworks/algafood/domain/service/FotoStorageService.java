package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import org.joda.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	FotoRecuperada recuperar(String filename);
	
	void armazenar(NovaFoto novaFoto);
	
	void remover(String filename);
	
	default String gerarNomeArquivo(String originalFilename) {
		String[] split = originalFilename.split("\\.");
		
		String name = split[0];
		String extension = split[1];
		
		String formatName = String.format("%s_%S", name, new LocalDateTime().toString());
		
		String randomName = UUID.nameUUIDFromBytes(formatName.getBytes()).toString();
		
		return String.format("%s.%s", randomName, extension);
	}
	
	@Getter
	@Builder
	class NovaFoto {
		
		private String nomeArquivo;
		
		private InputStream inputStream;
		
		private String contentType;
		
	}
	
	@Getter
	@Builder
	class FotoRecuperada {
		
		private InputStream inputStream;
		
		private String url;
		
		public boolean temUrl() {
			return url != null;
		}
		
		public boolean temInputStream() {
			return inputStream != null;
		}
		
	}
	
}
