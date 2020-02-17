package com.dgsystems.nfeservice.cadastro.produto;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Pis extends Imposto {
	@NotNull
	private Regime regime;

	public Regime getRegime() {
		return regime;
	}

	public void setRegime(Regime regime) {
		this.regime = regime;
	}
	
	public Pis(@PositiveOrZero double aliquota, @NotEmpty String cst, @NotNull Regime regime) {
		super(aliquota, cst);
		this.regime = regime;
	}
	
	public Pis() {
		super();
	}
}
