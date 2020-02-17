package com.dgsystems.nfeservice.cadastro.destinatario;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.server.core.Relation;

@Entity
@Relation(collectionRelation = "destinatarios")
public class Estrangeiro extends Destinatario {
	@NotEmpty
	private String idEstrangeiro;

	public String getIdEstrangeiro() {
		return idEstrangeiro;
	}

	public void setIdEstrangeiro(String idEstrangeiro) {
		this.idEstrangeiro = idEstrangeiro;
	}
	
	public Estrangeiro(String nome, String telefone, String email, String idEstrangeiro, Endereco endereco) {
		super(nome, telefone, email, endereco);
		this.idEstrangeiro = idEstrangeiro;
	}

	public Estrangeiro() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idEstrangeiro == null) ? 0 : idEstrangeiro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estrangeiro other = (Estrangeiro) obj;
		if (idEstrangeiro == null) {
			if (other.idEstrangeiro != null)
				return false;
		} else if (!idEstrangeiro.equals(other.idEstrangeiro))
			return false;
		return true;
	}
	
}
