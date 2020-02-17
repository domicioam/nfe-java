package com.dgsystems.nfeservice.cadastro.empresa;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dgsystems.nfeservice.cadastro.configuracao.Configuracao;
import com.dgsystems.nfeservice.cadastro.destinatario.Destinatario;
import com.dgsystems.nfeservice.cadastro.produto.Produto;

@Entity
public class Empresa {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@NotEmpty
	@Size(max = 100)
	private String razaoSocial;
	@NotEmpty
	@Size(max = 14)
	private String cnpj;
	@NotEmpty
	@Size(max = 100)
	private String nomeFantasia;
	@NotEmpty
	@Size(max = 13)
	private String inscricaoEstadual;
	@NotEmpty
	@Size(max = 13)
	private String inscricaoMunicipal;
	@NotNull
	private RegimeTributario regimeTributario;
	@NotEmpty
	@Size(max = 7)
	private String cnae;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	@NotNull
	private @Valid Endereco endereco;
	@OneToMany(cascade = CascadeType.ALL)
	private List<@Valid Configuracao> configuracoes;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<@Valid Produto> produtos;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<@Valid Destinatario> destinatarios;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	public String getCnae() {
		return cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Empresa(@NotEmpty @Size(max = 100) String razaoSocial, @NotEmpty @Size(max = 14) String cnpj,
			@NotEmpty @Size(max = 100) String nomeFantasia, @NotEmpty @Size(max = 13) String inscricaoEstadual,
			@NotEmpty @Size(max = 13) String inscricaoMunicipal, @NotEmpty RegimeTributario regimeTributario,
			@NotEmpty @Size(max = 7) String cnae, @NotNull Endereco endereco) {
		super();
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.inscricaoEstadual = inscricaoEstadual;
		this.inscricaoMunicipal = inscricaoMunicipal;
		this.regimeTributario = regimeTributario;
		this.cnae = cnae;
		this.endereco = endereco;
	}

	public Empresa() {
		super();
	}
}
