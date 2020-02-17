package com.dgsystems.nfeservice.cadastro.destinatario;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.server.core.Relation;

@Entity
@Relation(collectionRelation = "destinatarios")
public class PessoaFisica extends Destinatario {
	@NotEmpty
	private String cpf;

	public PessoaFisica(String nome, String telefone, String email, String cpf, Endereco endereco) {
		super(nome, telefone, email, endereco);
		this.cpf = cpf;
	}

	public PessoaFisica() {
		super();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		PessoaFisica other = (PessoaFisica) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
}
