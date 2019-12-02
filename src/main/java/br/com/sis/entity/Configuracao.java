package br.com.sis.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Configuracao implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal multa;
	private BigDecimal jurosDia;
	private String textoParagrafoUnico;

	@Column(precision = 10, scale = 2)
	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getJurosDia() {
		return jurosDia;
	}

	public void setJurosDia(BigDecimal jurosDia) {
		this.jurosDia = jurosDia;
	}

	@Column(columnDefinition = "TEXT")
	public String getTextoParagrafoUnico() {
		return textoParagrafoUnico;
	}

	public void setTextoParagrafoUnico(String textoParagrafoUnico) {
		this.textoParagrafoUnico = textoParagrafoUnico;
	}

}
