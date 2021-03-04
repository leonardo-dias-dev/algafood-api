package com.algaworks.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDto;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<VendaDiariaDto> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset) {
		var criteriaBuilder = entityManager.getCriteriaBuilder();
		var criteriaQuery = criteriaBuilder.createQuery(VendaDiariaDto.class);
		var root = criteriaQuery.from(Pedido.class);
		
		var functionDateDataCriacao = functionDateDataCriacao(criteriaBuilder, root, timeOffset);
		var countId = criteriaBuilder.count(root.get("id"));
		var sumValorTotal = criteriaBuilder.sum(root.get("valorTotal"));
		
		var compoundSelection = criteriaBuilder.construct(VendaDiariaDto.class, functionDateDataCriacao, countId, sumValorTotal);
		
		criteriaQuery.select(compoundSelection);
		criteriaQuery.where(getRestrictions(criteriaBuilder, root, vendaDiariaFilter));
		criteriaQuery.groupBy(functionDateDataCriacao);
		
		return entityManager.createQuery(criteriaQuery).getResultList();
	}
	
	private Expression<Date> functionDateDataCriacao(CriteriaBuilder criteriaBuilder, Root<Pedido> root, String timeOffset) {
		var functionConvertTz = criteriaBuilder.function("convert_tz", Date.class, root.get("dataCriacao"), criteriaBuilder.literal("+00:00"), criteriaBuilder.literal(timeOffset));
		var functionDateDataCriacao = criteriaBuilder.function("date", Date.class, functionConvertTz);
		
		return functionDateDataCriacao;
	}

	private Predicate[] getRestrictions(CriteriaBuilder criteriaBuilder, Root<Pedido> root, VendaDiariaFilter vendaDiariaFilter) {
		List<Predicate> restrictions = new ArrayList<>();
		
		if (vendaDiariaFilter.getRestauranteId() != null) {
			Predicate predicate = criteriaBuilder.equal(root.get("restaurante"), vendaDiariaFilter.getRestauranteId());
			
			restrictions.add(predicate);
		}
		
		if (vendaDiariaFilter.getDataCriacaoInicio() != null) {
			Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), vendaDiariaFilter.getDataCriacaoInicio());
			
			restrictions.add(predicate);
		}
		
		if (vendaDiariaFilter.getDataCriacaoFim() != null) {
			Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), vendaDiariaFilter.getDataCriacaoFim());
			
			restrictions.add(predicate);
		}
		
		In<StatusPedido> inStatusPedido = criteriaBuilder.in(root.get("status"));
		inStatusPedido.value(StatusPedido.CONFIRMADO);
		inStatusPedido.value(StatusPedido.ENTREGUE);
		
		restrictions.add(inStatusPedido);
		
		return restrictions.toArray(new Predicate[0]);
	}

}
