package com.algaworks.algafood.core.validation.annotation.impl;

import com.algaworks.algafood.core.validation.annotation.Multiplo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

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
