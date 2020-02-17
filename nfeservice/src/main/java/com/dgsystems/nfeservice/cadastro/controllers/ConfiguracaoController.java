package com.dgsystems.nfeservice.cadastro.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dgsystems.nfeservice.cadastro.configuracao.Configuracao;
import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.produto.Produto;
import com.dgsystems.nfeservice.cadastro.repositories.ConfiguracaoRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@Validated
@RequestMapping("/empresa/{empresaId}/configuracao")
public class ConfiguracaoController {
	@Autowired
	private ConfiguracaoRepository configuracaoRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Configuracao>> findByIdAndEmpresaId(@PathVariable("empresaId") Integer empresaId, @PathVariable Integer id) {
		Optional<Configuracao> configuracao = configuracaoRepository.findByIdAndEmpresaId(empresaId, id);
		Link selfRel = linkTo(methodOn(ConfiguracaoController.class).findByIdAndEmpresaId(empresaId, id)).withSelfRel();
		
		EntityModel<Configuracao> entityModel = new EntityModel<>(configuracao.get(), selfRel);
		return ResponseEntity.ok(entityModel);
	}
	
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Configuracao>>> findAllByEmpresaId(@PathVariable("empresaId") Integer empresaId) {
		Iterable<Configuracao> configuracoes = configuracaoRepository.findAllByEmpresaId(empresaId);
		List<EntityModel<Configuracao>> configuracaoResources = new ArrayList<>();
		
		for(Configuracao c : configuracoes) {
			EntityModel<Configuracao> entityModel = new EntityModel<>(c, 
					linkTo(methodOn(ConfiguracaoController.class).findByIdAndEmpresaId(empresaId, c.getId())).withSelfRel());
			
			configuracaoResources.add(entityModel);
		}
		
		return ResponseEntity.ok(new CollectionModel<>(configuracaoResources, 
				linkTo(methodOn(ConfiguracaoController.class).findAllByEmpresaId(empresaId)).withSelfRel()));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntityModel<Configuracao> save(@RequestBody @Valid Configuracao configuracao, @PathVariable("empresaId") Integer empresaId) {
		configuracao = configuracaoRepository.save(configuracao);
		return new EntityModel<Configuracao>(configuracao,
				linkTo(methodOn(ConfiguracaoController.class).findByIdAndEmpresaId(configuracao.getId(), empresaId)).withSelfRel());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		try {
			configuracaoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Configuração não encontrada.");
		}
	}
	
	@PutMapping("/{id}")
	public EntityModel<Configuracao> update(@RequestBody @Valid Configuracao configuracao, @PathVariable Integer id, @PathVariable("empresaId") Integer empresaId) {
		return configuracaoRepository.findById(id)
				.map(d -> {
					configuracao.setId(id);
					Configuracao newConfiguracao = configuracaoRepository.save(configuracao);
					return new EntityModel<Configuracao>(newConfiguracao, 
							linkTo(methodOn(ConfiguracaoController.class).findByIdAndEmpresaId(configuracao.getId(), empresaId)).withSelfRel());
				})
				.orElseThrow(() -> new NoSuchElementException("Configuração não encontrada."));
	}

}
