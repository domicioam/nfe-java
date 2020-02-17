package com.dgsystems.nfeservice.cadastro.produto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.hateoas.server.core.Relation;

import com.dgsystems.nfeservice.cadastro.empresa.Empresa;

@Entity
@Relation(collectionRelation = "produtos")
public class Produto {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@NotEmpty
	private String descricao;
	@NotEmpty
	private String ncm;
	@Size(min = 2, max = 2)
	private String unidadeComercial;

	@Positive
	private double valorUnitario;
	@OneToMany(cascade = CascadeType.ALL)
	@NotEmpty
	private List<@Valid Imposto> impostos;
	@ManyToOne(fetch = FetchType.LAZY)
	private @Valid Empresa empresa;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNcm() {
		return ncm;
	}
	public void setNcm(String ncm) {
		this.ncm = ncm;
	}
	public String getUnidadeComercial() {
		return unidadeComercial;
	}
	public void setUnidadeComercial(String unidadeComercial) {
		this.unidadeComercial = unidadeComercial;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public List<Imposto> getImpostos() {
		return impostos;
	}
	public void setImpostos(List<Imposto> impostos) {
		this.impostos = impostos;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((impostos == null) ? 0 : impostos.hashCode());
		result = prime * result + ((ncm == null) ? 0 : ncm.hashCode());
		result = prime * result + ((unidadeComercial == null) ? 0 : unidadeComercial.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorUnitario);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (impostos == null) {
			if (other.impostos != null)
				return false;
		} else if (!impostos.equals(other.impostos))
			return false;
		if (ncm == null) {
			if (other.ncm != null)
				return false;
		} else if (!ncm.equals(other.ncm))
			return false;
		if (unidadeComercial == null) {
			if (other.unidadeComercial != null)
				return false;
		} else if (!unidadeComercial.equals(other.unidadeComercial))
			return false;
		if (Double.doubleToLongBits(valorUnitario) != Double.doubleToLongBits(other.valorUnitario))
			return false;
		return true;
	}
	
	public Produto(@NotEmpty String descricao, @NotEmpty String ncm, @Size(min = 2, max = 2) String unidadeComercial,
			@Positive double valorUnitario, @NotEmpty List<Imposto> impostos) {
		super();
		this.descricao = descricao;
		this.ncm = ncm;
		this.unidadeComercial = unidadeComercial;
		this.valorUnitario = valorUnitario;
		this.impostos = impostos;
	}
	
	public Produto() {
		super();
	}
}
