package com.dgsystems.nfeservice.cadastro.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dgsystems.nfeservice.cadastro.empresa.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, Integer> {

}
