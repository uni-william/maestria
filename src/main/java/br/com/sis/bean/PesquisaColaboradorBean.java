package br.com.sis.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Usuario;
import br.com.sis.repository.UsuarioRepository;
import br.com.sis.repository.filter.UsuarioFilter;
import br.com.sis.security.Seguranca;
import br.com.sis.service.UsuarioService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaColaboradorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository repository;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private Seguranca seguranca;
	
	private List<Usuario> listaFiltrada;
	private Usuario colaboradorSelecionado;
	private UsuarioFilter filter;


	public Usuario getUsuarioSelecionado() {
		return colaboradorSelecionado;
	}

	public void setUsuarioSelecionado(Usuario colaboradorSelecionado) {
		this.colaboradorSelecionado = colaboradorSelecionado;
	}

	public UsuarioFilter getFilter() {
		return filter;
	}

	public void setFilter(UsuarioFilter filter) {
		this.filter = filter;
	}

	public List<Usuario> getListaFiltrada() {
		return listaFiltrada;
	}


	public Usuario getColaboradorSelecionado() {
		return colaboradorSelecionado;
	}

	public void setColaboradorSelecionado(Usuario colaboradorSelecionado) {
		this.colaboradorSelecionado = colaboradorSelecionado;
	}

	public void inicializar() {
		filter = new UsuarioFilter();

	}

	public void pesquisar() {
		listaFiltrada = repository.filtrados(filter);
	}

	public void excluir() {
		if (usuarioService.removerUsuario(colaboradorSelecionado) == true) {
			colaboradorSelecionado = new Usuario();
			listaFiltrada = repository.filtrados(filter);
			FacesUtil.addInfoMessage("Registro excluído com sucesso!");
		}
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isFuncionarioInserir();
	}
	
	public boolean isPermissaoEditar() {
		return seguranca.isFuncionarioEditar();
	}
	
	public boolean isPermissaoExcluir() {
		return seguranca.isFuncionarioExcluir();
	}	
}
