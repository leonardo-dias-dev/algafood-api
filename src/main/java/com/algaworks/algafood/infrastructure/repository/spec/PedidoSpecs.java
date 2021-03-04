package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;

public class PedidoSpecs {
	
	public static Specification<Pedido> usandoFiltro(PedidoFilter pedidoFilter) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			
			if (query.getResultType().equals(Pedido.class)) {
				root.fetch("restaurante").fetch("cozinha");
				root.fetch("cliente");
			}
			
			if (pedidoFilter.getClienteId() != null) {
				Predicate predicate = builder.equal(root.get("cliente"), pedidoFilter.getClienteId());
				
				predicates.add(predicate);
			}
			
			if (pedidoFilter.getRestauranteId() != null) {
				Predicate predicate = builder.equal(root.get("restaurante"), pedidoFilter.getClienteId());
				
				predicates.add(predicate);
			}
			
			if (pedidoFilter.getDataCriacaoInicio() != null) {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoInicio());
				
				predicates.add(predicate);
			}
			
			if (pedidoFilter.getDataCriacaoFim() != null) {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("dataCriacao"), pedidoFilter.getDataCriacaoFim());
				
				predicates.add(predicate);
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
