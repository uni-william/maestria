package br.com.sis.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aluno;
import br.com.sis.repository.AlunoRepository;
import br.com.sis.repository.filter.AlunoFilter;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaAlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoRepository alunoRepository;

	private List<Aluno> alunos;
	private Aluno aluno = new Aluno();
	private AlunoFilter filter;

	public AlunoFilter getFilter() {
		return filter;
	}

	public void setFilter(AlunoFilter filter) {
		this.filter = filter;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public void pesquisar() {
		this.alunos = alunoRepository.filtrados(filter);
	}

	public void inicializar() {
		this.filter = new AlunoFilter();
	}

	public void excluir() {
		this.alunoRepository.remover(aluno);
		this.pesquisar();
		FacesUtil.addInfoMessage("Registro excluído com sucesso!");
	}

}
