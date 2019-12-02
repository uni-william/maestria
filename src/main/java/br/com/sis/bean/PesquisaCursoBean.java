package br.com.sis.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Curso;
import br.com.sis.repository.CursoRepository;
import br.com.sis.security.Seguranca;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaCursoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CursoRepository cursoRepository;
	
	@Inject
	private Seguranca seguranca;
	
	private List<Curso> cursos;
	private Curso curso = new Curso();

	public List<Curso> getCursos() {
		return cursos;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public void inicializar() {
		this.pesquisar();
	}
	
	public void pesquisar() {
		this.cursos = cursoRepository.listarTodos();
	}
	
	public void excluir() {
		this.cursoRepository.remover(curso);
		FacesUtil.addInfoMessage("Registro excluído com sucesso!");
		this.pesquisar();
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isCursoInserir();
	}
	
	public boolean isPermissaoEditar() {
		return seguranca.isCursoEditar();
	}
	
	public boolean isPermissaoExcluir() {
		return seguranca.isCursoExcluir();
	}	

}
