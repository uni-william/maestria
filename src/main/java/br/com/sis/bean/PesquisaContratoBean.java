package br.com.sis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Contrato;
import br.com.sis.entity.Curso;
import br.com.sis.enuns.StatusMatricula;
import br.com.sis.repository.ContratoRepository;
import br.com.sis.repository.CursoRepository;
import br.com.sis.repository.filter.ContratoFilter;
import br.com.sis.security.Seguranca;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaContratoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContratoRepository contratoRepository;
	@Inject
	private CursoRepository cursoRepository;
	
	@Inject
	private Seguranca seguranca;

	private List<Contrato> contratos = new ArrayList<Contrato>();
	private List<Curso> cursos = new ArrayList<Curso>();
	private ContratoFilter contratoFilter;
	private Contrato contrato;

	public List<Contrato> getContratos() {
		return contratos;
	}

	public ContratoFilter getContratoFilter() {
		return contratoFilter;
	}

	public void setContratoFilter(ContratoFilter contratoFilter) {
		this.contratoFilter = contratoFilter;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void inicializar() {
		contrato = new Contrato();
		contratoFilter = new ContratoFilter();
		cursos = cursoRepository.listarTodos();
	}

	public void pesquisar() {
		contratos = contratoRepository.filtrados(contratoFilter);
	}

	public void excluir() {
		this.contratoRepository.remover(contrato);
		FacesUtil.addInfoMessage("Registro excluído com sucesso!");
		this.pesquisar();
	}

	public StatusMatricula[] getStatusMatriculas() {
		return StatusMatricula.values();
	}
	
	public boolean isPermissaoEditar() {
		return seguranca.isPermissaoMatricular();
	}
	
	public boolean isPermissaoExcluir() {
		return seguranca.isContratoExcluir();
	}	

}
