package com.algaworks.algafood.core.validation.annotation.impl;

import com.algaworks.algafood.core.validation.annotation.ValorZeroIncluiDescricao;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;

    private String descricaoField;

    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        valorField = constraintAnnotation.valorField();
        descricaoField = constraintAnnotation.descricaoField();
        descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(value.getClass(), valorField).getReadMethod().invoke(value);
            String descricao = (String) BeanUtils.getPropertyDescriptor(value.getClass(), descricaoField).getReadMethod().invoke(value);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                return descricao.toLowerCase().contains(descricaoObrigatoria);
            }

            return true;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

}
