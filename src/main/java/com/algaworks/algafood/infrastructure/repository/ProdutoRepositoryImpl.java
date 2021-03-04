package com.algaworks.algafood.infrastructure.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public FotoProduto save(FotoProduto foto) {
		return entityManager.merge(foto);
	}

	@Override
	public void deleteFotoProduto(FotoProduto fotoProduto) {
		entityManager.remove(fotoProduto);
	}
	
}
