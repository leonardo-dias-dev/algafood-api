package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private UsuarioService usuarioService;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());
		Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = buscar(id);
		
		restaurante.ativar();
	}
	
	@Transactional
	public void ativar(List<Long> ids) {
		ids.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(Long id) {
		Restaurante restaurante = buscar(id);
		
		restaurante.inativar();
	}
	
	@Transactional
	public void inativar(List<Long> ids) {
		ids.forEach(this::inativar);
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscar(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void abrir(Long id) {
		Restaurante restaurante = buscar(id);
		
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long id) {
		Restaurante restaurante = buscar(id);
		
		restaurante.fechar();
	}
	
	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscar(restauranteId);
	    Usuario usuario = usuarioService.buscar(usuarioId);
	    
	    restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscar(restauranteId);
	    Usuario usuario = usuarioService.buscar(usuarioId);
	    
	    restaurante.removerResponsavel(usuario);
	}

}
