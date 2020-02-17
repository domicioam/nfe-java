package com.dgsystems.nfeservice.cadastro.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;
import com.dgsystems.nfeservice.cadastro.produto.Produto;
import com.dgsystems.nfeservice.cadastro.repositories.ProdutoRepository;

@Validated
@RestController
@RequestMapping("/empresa/{empresaId}/produto")
public class ProdutoController {
	@Autowired
	ProdutoRepository produtoRepository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EntityModel<Produto>> findByIdAndEmpresaId(@PathVariable("id") Integer id, @PathVariable("empresaId") Integer empresaId) {
		return produtoRepository.findById(id)
				.map(m -> {
					return ResponseEntity.ok(new EntityModel<>(m, 
							linkTo(methodOn(ProdutoController.class).findByIdAndEmpresaId(id, empresaId)).withSelfRel()));
				})
				.orElseThrow(EntityNotFoundException::new);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntityModel<Produto> save(@RequestBody @Valid Produto produto, @PathVariable("empresaId") Integer empresaId) {
		produto = produtoRepository.save(produto);
		return new EntityModel<Produto>(produto,
				linkTo(methodOn(ProdutoController.class).findByIdAndEmpresaId(produto.getId(), empresaId)).withSelfRel());
	}
	
	@PutMapping("/{id}")
	public Produto update(@RequestBody @Valid Produto produto, @PathVariable Integer id) {
		return produtoRepository.findById(id)
				.map(p -> {
					produto.setId(id);
					return produtoRepository.save(produto);
				})
				.orElseThrow(() -> new NoSuchElementException("Produto não encontrado."));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		try {
			produtoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Produto não existe.");
		}
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Produto>>> findAllByEmpresaId(@PathVariable("empresaId") Integer empresaId) {
		Iterable<Produto> produtos = produtoRepository.findAllByEmpresaId(empresaId);
		List<EntityModel<Produto>> produtoResources = new ArrayList<>();
		
		for(Produto p : produtos) {
			EntityModel<Produto> entityModel = new EntityModel<>(p,
					linkTo(methodOn(ProdutoController.class).findByIdAndEmpresaId(p.getId(), empresaId)).withSelfRel());
			produtoResources.add(entityModel);
		}
		
		return ResponseEntity.ok(new CollectionModel<>(produtoResources,
				linkTo(methodOn(ProdutoController.class).findAllByEmpresaId(empresaId)).withSelfRel()));
	}
}
