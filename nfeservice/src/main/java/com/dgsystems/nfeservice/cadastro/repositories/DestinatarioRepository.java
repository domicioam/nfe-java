package com.dgsystems.nfeservice.cadastro.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;

public interface DestinatarioRepository extends CrudRepository<Destinatario, Integer> {
	Optional<Destinatario> findByIdAndEmpresaId(Integer id, Integer empresaId);
	Iterable<Destinatario> findAllByEmpresaId(Integer empresaId);
}
