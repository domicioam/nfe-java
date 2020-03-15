package com.dgsystems.nfeservice.cadastro.certificado;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.util.StringUtils;

import com.dgsystems.nfeservice.cadastro.empresa.Empresa;

@Entity
public class Certificado {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotEmpty
	private String fileName;
	@NotEmpty
	private String fileType;
	@Lob
	private byte[] data;
	@ManyToOne(fetch = FetchType.LAZY)
	private @Valid Empresa empresa;
	
	public byte[] getData() {
		return data;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public Certificado() {
		super();
	}
	public Certificado(String fileName, String fileType, byte[] data) {
		super();
		this.fileName = StringUtils.cleanPath(fileName);
		this.fileType = fileType;
		this.data = data;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
