package com.algaworks.algafood.core.validation.annotation.impl;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.algaworks.algafood.core.validation.annotation.Multiplo;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number>{
	
	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		numeroMultiplo = constraintAnnotation.numero();
	}

	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		
		var valorDecimal = BigDecimal.valueOf(value.doubleValue());
		var multiploDecimal = BigDecimal.valueOf(numeroMultiplo);
		
		var resto = valorDecimal.remainder(multiploDecimal);
		
		return BigDecimal.ZERO.compareTo(resto) == 0;
	}
	
}
