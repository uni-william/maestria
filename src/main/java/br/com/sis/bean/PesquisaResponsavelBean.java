package br.com.sis.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Responsavel;
import br.com.sis.repository.ResponsavelRepository;
import br.com.sis.repository.filter.ResponsavelFilter;
import br.com.sis.security.Seguranca;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaResponsavelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ResponsavelRepository responsavelRepository;
	
	@Inject
	private Seguranca seguranca;

	private List<Responsavel> responsavels;
	private Responsavel responsavel = new Responsavel();
	private ResponsavelFilter filter;

	public List<Responsavel> getResponsavels() {
		return responsavels;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public ResponsavelFilter getFilter() {
		return filter;
	}

	public void setFilter(ResponsavelFilter filter) {
		this.filter = filter;
	}

	public void pesquisar() {
		this.responsavels = responsavelRepository.filtrados(filter);
	}

	public void inicializar() {
		this.filter = new ResponsavelFilter();
	}

	public void excluir() {
		this.responsavelRepository.remover(responsavel);
		this.pesquisar();
		FacesUtil.addInfoMessage("Registro excluído com sucesso!");
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isResponsavelInserir();
	}
	
	public boolean isPermissaoEditar() {
		return seguranca.isResponsavelEditar();
	}
	
	public boolean isPermissaoExcluir() {
		return seguranca.isResponsavelExcluir();
	}	

}
