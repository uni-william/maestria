package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Empresa;
import br.com.sis.enuns.Estado;
import br.com.sis.security.Seguranca;
import br.com.sis.service.EmpresaService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaService empresaService;
	
	@Inject
	private Seguranca seguranca;
	
	
	private Empresa empresa;


	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void inicializar() {
		if (empresa == null) {
			empresa = new Empresa();
		}
	}

	public void salvar() {
		empresaService.salvar(empresa);
		FacesUtil.addInfoMessage("Registro realizado com sucesso!");
		empresa = new Empresa();
	}

	public boolean isEditando() {
		return this.empresa.getId() != null;
	}
	
	public Estado[] getEstados() {
		return Estado.values();
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isEmpresaInserir();
	}
	
	public boolean isPermissaoSalvar() {
		return (this.isEditando() && this.seguranca.isEmpresaEditar()) || (!this.isEditando() && this.seguranca.isEmpresaInserir());
	}

}
