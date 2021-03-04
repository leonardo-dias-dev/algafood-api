package com.algaworks.algafood.core.validation.annotation.impl;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.annotation.FileContentType;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {
	
	private List<String> contentTypes;

	@Override
	public void initialize(FileContentType constraintAnnotation) {
		contentTypes = Arrays.asList(constraintAnnotation.allowed());
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		return multipartFile == null || contentTypes.contains(multipartFile.getContentType());
	}

}
