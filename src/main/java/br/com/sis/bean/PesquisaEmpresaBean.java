package br.com.sis.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Empresa;
import br.com.sis.repository.EmpresaRepository;
import br.com.sis.security.Seguranca;

@Named
@ViewScoped
public class PesquisaEmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmpresaRepository empresaRepository;
	
	@Inject
	private Seguranca seguranca;
	
	private List<Empresa> empresas;
	private Empresa empresa = new Empresa();

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void inicializar() {
		this.empresas = empresaRepository.listarTodos();
	}
	
	public void excluir() {
		this.empresaRepository.remover(empresa);
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isEmpresaInserir();
	}
	
	public boolean isPermissaoEditar() {
		return seguranca.isEmpresaEditar();
	}
	
	public boolean isPermissaoExcluir() {
		return seguranca.isEmpresaExcluir();
	}

}
