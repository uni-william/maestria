package br.com.sis.enuns;

public enum Funcionalidade {
	EMPRESA_ACCESS("Empresa - Acesso"),
	EMPRESA_INSERT("Empresa - Permissão para inserir"),
	EMPRESA_EDIT("Empresa - Permissão para editar"),
	EMPRESA_DEL("Empresa - Permissão para excluir"),
	PERFIL_ACCESS("Perfis - Acesso"),
	PERFIL_INSERT("Perfis - Permissão para inserir"),
	PERFIL_EDIT("Perfis - Permissão para editar"),
	PERFIL_DEL("Perfis - Permissão para excluir"),	
	FUNC_ACCESS("Colaborador - Acesso"),
	FUNC_INSERT("Colaborador - Permissão para inserir"),
	FUNC_EDIT("Colaborador - Permissão para editar"),
	FUNC_DEL("Colaborador - Permissão para excluir"),
	ALUNO_ACCESS("Aluno - Acesso"),
	ALUNO_INSERT("Aluno - Permissão para inserir"),
	ALUNO_EDIT("Aluno - Permissão para editar"),
	ALUNO_DEL("Aluno - Permissão para excluir"),
	CURSO_ACCESS("Curso - Acesso"),
	CURSO_INSERT("Curso - Permissão para inserir"),
	CURSO_EDIT("Curso - Permissão para editar"),
	CURSO_DEL("Curso - Permissão para excluir"),
	RESPONSAVEL_ACCESS("Responsável - Acesso"),
	RESPONSAVEL_INSERT("Responsável - Permissão para inserir"),
	RESPONSAVEL_EDIT("Responsável - Permissão para editar"),
	RESPONSAVEL_DEL("Responsável - Permissão para excluir"),	
	PES_CONTRATO("Contrato - Permissão para Pesquisa"),
	PACOTES("Pacotes - Acesso"),
	MATRICULA("Matrículas - Manutenção de Matrículas"),
	CONTRATO_DEL("Contrato - Excluir Contrato"),
	MENSALIDADE_ACCESS("Mensalidades - Pesquisar mensalidades"),
	MENSALIDADE_EDIT("Mensalidades - Permissão para editar"),
	MENSALIDADE_DEL("Mensalidades - Permissão para excluir"),
	MENSALIDADE_CLEAR("Mensalidades - Limpar pagamento"),
	MENSALIDADE_GERAR("Mensalidades - Geração de Mensalidades"),
	PAGAR_PARCELA("Parcelas - Permissão para efetuar o pagamento"),
	CONS_ALUO_DIA("Consulta - Lista de alunos por dia"),
	MATERIAL_ACCESS("Material - Acesso"),
	MATERIAL_INSERT("Material - Permissão para inserir"),
	MATERIAL_EDIT("Material - Permissão para editar"),
	MATERIAL_DEL("Material - Permissão para excluir");

	Funcionalidade(String descricao) {
		this.descricao = descricao;
	}
	
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}	


}
