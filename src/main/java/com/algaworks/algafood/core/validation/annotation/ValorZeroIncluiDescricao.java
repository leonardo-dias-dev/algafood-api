package com.algaworks.algafood.core.validation.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.algaworks.algafood.core.validation.annotation.impl.ValorZeroIncluiDescricaoValidator;

@Retention(RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluiDescricaoValidator.class })
@Target({ ElementType.TYPE })
public @interface ValorZeroIncluiDescricao {

	String message() default "descrição obrigatória inválida";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	String valorField();
	
	String descricaoField();
	
	String descricaoObrigatoria();

}
