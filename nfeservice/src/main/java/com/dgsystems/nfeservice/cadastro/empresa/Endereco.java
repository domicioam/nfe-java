package com.dgsystems.nfeservice.cadastro.empresa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity(name = "EmpresaEndereco")
@Table(name = "EmpresaEndereco")
public class Endereco {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@NotEmpty
	@Size(max = 10)
	private String cep;
	@NotEmpty
	@Size(max = 100)
	private String logradouro;
	@NotEmpty
	@Size(max = 50)
	private String bairro;
	@NotEmpty
	@Size(max = 2)
	private String uf;
	@NotEmpty
	@Size(max = 50)
	private String municipio;
	@NotEmpty
	@Size(max = 10)
	private String numero;
	@OneToOne
	private Empresa empresa;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Endereco(@NotEmpty @Size(max = 10) String cep, @NotEmpty @Size(max = 100) String logradouro,
			@NotEmpty @Size(max = 50) String bairro, @NotEmpty @Size(max = 2) String uf,
			@NotEmpty @Size(max = 50) String municipio, @NotEmpty @Size(max = 10) String numero) {
		super();
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.uf = uf;
		this.municipio = municipio;
		this.numero = numero;
	}
	public Endereco() {
		super();
	}
}
