package com.algaworks.algafood.core.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.algaworks.algafood.core.validation.annotation.impl.FileContentTypeValidator;

@Retention(RUNTIME)
@Constraint(validatedBy = { FileContentTypeValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
public @interface FileContentType {
	
	String message() default "arquivo inválido";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String[] allowed();
	
}
