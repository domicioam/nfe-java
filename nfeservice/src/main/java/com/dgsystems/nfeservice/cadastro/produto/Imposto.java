package com.dgsystems.nfeservice.cadastro.produto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "tipo"
)
@JsonSubTypes({
	@Type(value = Cofins.class, name = "cofins"),
	@Type(value = Icms.class, name = "icms"),
	@Type(value = Ipi.class, name = "ipi"),
	@Type(value = Pis.class, name = "pis"),
	@Type(value = Imposto.class, name = "imposto")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Imposto {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;
    @PositiveOrZero
    private double aliquota;
    @NotEmpty
    private String cst;
    
	public double getAliquota() {
		return aliquota;
	}
	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCst() {
		return cst;
	}
	public void setCst(String cst) {
		this.cst = cst;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(aliquota);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((cst == null) ? 0 : cst.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		Imposto other = (Imposto) obj;
		if (Double.doubleToLongBits(aliquota) != Double.doubleToLongBits(other.aliquota))
			return false;
		if (cst == null) {
			if (other.cst != null)
				return false;
		} else if (!cst.equals(other.cst))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}
	
	public Imposto(@PositiveOrZero double aliquota, @NotEmpty String cst) {
		super();
		this.aliquota = aliquota;
		this.cst = cst;
	}
	public Imposto() {
		super();
	}
}

