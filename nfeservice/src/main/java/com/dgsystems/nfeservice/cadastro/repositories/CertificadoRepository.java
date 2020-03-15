package com.dgsystems.nfeservice.cadastro.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dgsystems.nfeservice.cadastro.certificado.Certificado;

@Repository
public interface CertificadoRepository extends CrudRepository<Certificado, Integer> {

}
