package com.algaworks.algafood.core.validation.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.annotation.FileSize;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile>{
	
	private DataSize dataSize;
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		dataSize = DataSize.parse(constraintAnnotation.max());
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || value.getSize() <= dataSize.toBytes();
	}
	
}
