package br.com.sis.enuns;

public enum TipoMensalidade {

	MENSAL("Mensal", "com vencimento a cada 30 dias a partir da assinatura deste contrato"),
	UNICA("Parcela Única", "em parcela única");

	private String descricao;
	private String descricaoLonga;

	TipoMensalidade(String descricao, String descricaoLonga) {
		this.descricao = descricao;
		this.descricaoLonga = descricaoLonga;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoLonga() {
		return descricaoLonga;
	}

}
