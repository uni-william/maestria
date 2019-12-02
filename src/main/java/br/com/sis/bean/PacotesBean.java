package br.com.sis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aluno;
import br.com.sis.entity.Curso;
import br.com.sis.repository.CursoRepository;
import br.com.sis.security.Seguranca;

@Named
@ViewScoped
public class PacotesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CursoRepository cursoRepository;
	
	@Inject
	private Seguranca seguranca;

	private List<Curso> cursos = new ArrayList<Curso>();

	private Aluno aluno;

	public List<Curso> getCursos() {
		return cursos;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public void inicializar() {
		cursos = cursoRepository.listarTodos();
	}
	
	public boolean isBotaoEscolha() {
		return this.aluno != null;
	}
	
	public boolean isPermissaoMatricular() {
		return this.seguranca.isPermissaoMatricular();
	}	

}
