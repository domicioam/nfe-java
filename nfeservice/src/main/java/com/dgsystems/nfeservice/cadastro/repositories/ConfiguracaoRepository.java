package com.dgsystems.nfeservice.cadastro.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dgsystems.nfeservice.cadastro.configuracao.Configuracao;

public interface ConfiguracaoRepository extends CrudRepository<Configuracao, Integer> {
	Optional<Configuracao> findByIdAndEmpresaId(Integer id, Integer empresaId);
	Iterable<Configuracao> findAllByEmpresaId(Integer empresaId);
}
