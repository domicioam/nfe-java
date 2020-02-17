package com.dgsystems.nfeservice.cadastro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import com.dgsystems.nfeservice.cadastro.configuracao.Configuracao;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.repositories.EmpresaRepository;

@Validated
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EntityModel<Empresa>> findById(@PathVariable("id") Integer id) {
		Optional<Empresa> empresa = empresaRepository.findById(id);
		Link selfRel = linkTo(methodOn(EmpresaController.class).findById(id)).withSelfRel();
		
		EntityModel<Empresa> entityModel = new EntityModel<>(empresa.get(), selfRel);
		return ResponseEntity.ok(entityModel);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa save(@RequestBody @Valid Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	@PutMapping("/{id}")
	public Empresa update(@RequestBody @Valid Empresa empresa, @PathVariable Integer id) {
		return empresaRepository.findById(id)
				.map(d -> {
					empresa.setId(id);
					return empresaRepository.save(empresa);
				})
				.orElseThrow(() -> new NoSuchElementException("Empresa não encontrada."));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		try {
			empresaRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Empresa não encontrada.");
		}
	}
}
