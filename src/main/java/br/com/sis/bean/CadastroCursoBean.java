package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Curso;
import br.com.sis.security.Seguranca;
import br.com.sis.service.CursoService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCursoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CursoService cursoService;
	
	@Inject
	private Seguranca seguranca;
	
	private Curso curso;


	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public void inicializar() {
		if (curso == null) {
			curso = new Curso();
		}
	}

	public void salvar() {
		cursoService.salvar(curso);
		FacesUtil.addInfoMessage("Registro realizado com sucesso!");
		curso = new Curso();
	}

	public boolean isEditando() {
		return this.curso.getId() != null;
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isCursoInserir();
	}
	
	public boolean isPermissaoSalvar() {
		return (this.isEditando() && this.seguranca.isCursoEditar()) || (!this.isEditando() && this.seguranca.isCursoInserir());
	}	
	
}
