package com.dgsystems.nfeservice.cadastro.configuracao;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.dgsystems.nfeservice.cadastro.empresa.Empresa;

@Entity
public class Configuracao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Size(min = 1, max = 8)
	private String serieNfe;
	@Positive
	private long numeroNfe;
	@Size(min = 1, max = 8)
	private String serieNfce;
	@Positive
	private long numeroNfce;
	@Size(min = 6, max = 6)
	private String cscId;
	@Size(min = 36, max = 36)
	private String csc;
	private String emailContabilidade;
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	public String getSerieNfe() {
		return serieNfe;
	}
	public void setSerieNfe(String serieNfe) {
		this.serieNfe = serieNfe;
	}
	public long getNumeroNfe() {
		return numeroNfe;
	}
	public void setNumeroNfe(long numeroNfe) {
		this.numeroNfe = numeroNfe;
	}
	public String getSerieNfce() {
		return serieNfce;
	}
	public void setSerieNfce(String serieNfce) {
		this.serieNfce = serieNfce;
	}
	public long getNumeroNfce() {
		return numeroNfce;
	}
	public void setNumeroNfce(long numeroNfce) {
		this.numeroNfce = numeroNfce;
	}
	public String getCscId() {
		return cscId;
	}
	public void setCscId(String cscId) {
		this.cscId = cscId;
	}
	public String getCsc() {
		return csc;
	}
	public void setCsc(String csc) {
		this.csc = csc;
	}
	public String getEmailContabilidade() {
		return emailContabilidade;
	}
	public void setEmailContabilidade(String emailContabilidade) {
		this.emailContabilidade = emailContabilidade;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Configuracao(String serieNfe, long numeroNfe, String serieNfce, long numeroNfce, String cscId, String csc,
			String emailContabilidade) {
		super();
		this.serieNfe = serieNfe;
		this.numeroNfe = numeroNfe;
		this.serieNfce = serieNfce;
		this.numeroNfce = numeroNfce;
		this.cscId = cscId;
		this.csc = csc;
		this.emailContabilidade = emailContabilidade;
	}
	public Configuracao() {
		super();
	}
}
