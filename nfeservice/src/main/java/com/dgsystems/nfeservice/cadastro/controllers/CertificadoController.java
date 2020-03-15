package com.dgsystems.nfeservice.cadastro.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dgsystems.nfeservice.cadastro.certificado.Certificado;
import com.dgsystems.nfeservice.cadastro.empresa.Empresa;
import com.dgsystems.nfeservice.cadastro.repositories.CertificadoRepository;

@RestController
@Validated
@RequestMapping("/empresa/{empresaId}/certificado")
public class CertificadoController {
	@Autowired
	private CertificadoRepository certificadoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@PathVariable("empresaId") Integer empresaId, @RequestParam("certificado") MultipartFile certificado) {
		try {
			Empresa empresa = new Empresa();
			empresa.setId(empresaId);
			
			Certificado entity;
			entity = new Certificado(certificado.getOriginalFilename(), certificado.getContentType(), certificado.getBytes());
			entity.setEmpresa(empresa);
			certificadoRepository.save(entity);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
}
