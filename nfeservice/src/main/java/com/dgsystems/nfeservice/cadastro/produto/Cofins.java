package com.dgsystems.nfeservice.cadastro.produto;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Cofins extends Imposto {
	@NotNull
	private Regime regime;

	public Regime getRegime() {
		return regime;
	}

	public void setRegime(Regime regime) {
		this.regime = regime;
	}
}
