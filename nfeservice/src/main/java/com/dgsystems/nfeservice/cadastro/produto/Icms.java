package com.dgsystems.nfeservice.cadastro.produto;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Icms extends Imposto {
	@PositiveOrZero
	private double reducao;

	public double getReducao() {
		return reducao;
	}

	public void setReducao(double reducao) {
		this.reducao = reducao;
	}

	public Icms(@PositiveOrZero double aliquota, @NotEmpty String cst, @PositiveOrZero double reducao) {
		super(aliquota, cst);
		this.reducao = reducao;
	}

	public Icms() {
		super();
	}
}
