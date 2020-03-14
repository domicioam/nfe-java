package com.dgsystems.nfeservice.cadastro.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequestMapping("/empresa/{empresaId}/certificado")
public class CertificadoController {
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@PathVariable("empresaId") Integer empresaId, @RequestParam("certificado") MultipartFile certificado) {
		
	}
}
