package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String filename) {
        try {
            Path path = getPath(filename);

            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(path))
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo.", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path path = getPath(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(path));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path path = getPath(nomeArquivo);

            Files.deleteIfExists(path);
        } catch (Exception e) {
            throw new StorageException("Não foi possível remover o arquivo.", e);
        }
    }

    private Path getPath(String filename) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(filename));
    }

}
