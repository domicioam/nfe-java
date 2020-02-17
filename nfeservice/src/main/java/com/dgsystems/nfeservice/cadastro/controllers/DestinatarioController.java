package com.dgsystems.nfeservice.cadastro.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.repositories.DestinatarioRepository;
import com.dgsystems.nfeservice.cadastro.repositories.EmpresaRepository;

@Validated
@RestController
@RequestMapping("/empresa/{empresaId}/destinatario")
public class DestinatarioController {
	@Autowired
	private DestinatarioRepository destinatarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EntityModel<Destinatario>> findByIdAndEmpresaId(@PathVariable("id") Integer id, @PathVariable("empresaId") Integer empresaId) {
		java.util.Optional<Destinatario> destinatario = destinatarioRepository.findByIdAndEmpresaId(id, empresaId);
		Link selfRel = linkTo(methodOn(DestinatarioController.class).findByIdAndEmpresaId(id, empresaId)).withSelfRel();
		
		EntityModel<Destinatario> entityModel = new EntityModel<>(destinatario.get(), selfRel);
		
		return ResponseEntity.ok(entityModel);
	}
	
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Destinatario>>> findAllByEmpresaId(@PathVariable("empresaId") Integer empresaId) {
		Iterable<Destinatario> destinatarios = destinatarioRepository.findAll();
		List<EntityModel<Destinatario>> destinatarioResources = new ArrayList<>();
		
		for(Destinatario d : destinatarios) {
			EntityModel<Destinatario> entityModel = new EntityModel<>(d, 
					linkTo(methodOn(DestinatarioController.class).findByIdAndEmpresaId(d.getId(), empresaId)).withSelfRel());
			
			destinatarioResources.add(entityModel);
		}
		
		return ResponseEntity.ok(new CollectionModel<>(destinatarioResources, 
				linkTo(methodOn(DestinatarioController.class).findAllByEmpresaId(empresaId)).withSelfRel()));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Destinatario save(@RequestBody @Valid Destinatario destinatario, @PathVariable("empresaId") Integer empresaId) {
		Empresa empresa = new Empresa();
		empresa.setId(empresaId);
		destinatario.setEmpresa(empresa);
		return destinatarioRepository.save(destinatario);
	}
	
	@PutMapping("/{id}")
	public Destinatario update(@RequestBody @Valid Destinatario destinatario, @PathVariable Integer id) {
		return destinatarioRepository.findById(id)
				.map(d -> {
					destinatario.setId(id);
					return destinatarioRepository.save(destinatario);
				})
				.orElseThrow(() -> new NoSuchElementException("Destinatário não encontrado."));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		try {
			destinatarioRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (EmptyResultDataAccessException e) {
			throw new NoSuchElementException("Destinatário não existe.");
		}
	}
}
