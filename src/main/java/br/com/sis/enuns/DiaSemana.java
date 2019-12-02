package br.com.sis.enuns;

public enum DiaSemana {

	DOMINGO("Domingo"),
	SEGUNDA("Segunda"),
	TERCA("Terça"),
	QUARTA("Quarta"),
	QUINTA("Quinta"),
	SEXTA("Sexta"),
	SABADO("Sábado"),;

	private String descricao;

	DiaSemana(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
