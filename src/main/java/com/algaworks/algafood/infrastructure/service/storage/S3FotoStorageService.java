package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String filename) {
        String bucket = storageProperties.getS3().getBucket();
        String caminhoArquivo = getCaminhoArquivo(filename);

        URL url = amazonS3.getUrl(bucket, caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String bucket = storageProperties.getS3().getBucket();
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
            InputStream inputStream = novaFoto.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(bucket, caminhoArquivo, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar o arquivo para Amazon S3.", e);
        }
    }

    @Override
    public void remover(String filename) {
        String bucket = storageProperties.getS3().getBucket();
        String caminhoArquivo = getCaminhoArquivo(filename);

        var deleteObjectRequest = new DeleteObjectRequest(bucket, caminhoArquivo);

        amazonS3.deleteObject(deleteObjectRequest);
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}
