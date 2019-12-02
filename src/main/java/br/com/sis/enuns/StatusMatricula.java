package br.com.sis.enuns;

public enum StatusMatricula {

	MATRICULADO("Matriculado"),
	TRANCADO("Trancado"),
	CANCELADO("Cancelado"),
	CONCLUIDO("Concluído");

	private String descricao;

	StatusMatricula(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
