package com.dgsystems.nfeservice.cadastro.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dgsystems.nfeservice.cadastro.produto.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer>{
	Optional<Produto> findByIdAndEmpresaId(Integer id, Integer empresaId);
	Iterable<Produto> findAllByEmpresaId(Integer empresaId);
}
