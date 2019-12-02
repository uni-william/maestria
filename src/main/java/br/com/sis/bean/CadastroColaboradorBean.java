package br.com.sis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import br.com.sis.entity.Empresa;
import br.com.sis.entity.Perfil;
import br.com.sis.entity.Usuario;
import br.com.sis.enuns.Estado;
import br.com.sis.repository.EmpresaRepository;
import br.com.sis.repository.PerfilRepository;
import br.com.sis.security.Seguranca;
import br.com.sis.service.UsuarioService;
import br.com.sis.util.Utils;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroColaboradorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService service;
	@Inject
	private PerfilRepository perfilRepository;

	@Inject
	private EmpresaRepository empresaRepository;
	
	@Inject
	private Seguranca seguranca;

	private Usuario usuario;

	private List<Empresa> empresas = new ArrayList<Empresa>();

	private List<String> listaPerfis = new ArrayList<>();
	private List<String> listaPerfisUsuario = new ArrayList<>();

	private DualListModel<String> perfisModel;

	public DualListModel<String> getPerfisModel() {
		return perfisModel;
	}

	public void setPerfisModel(DualListModel<String> perfisModel) {
		this.perfisModel = perfisModel;
	}

	public List<String> getListaPerfis() {
		return listaPerfis;
	}

	public List<String> getListaPerfisUsuario() {
		return listaPerfisUsuario;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void inicializar() {
		empresas = empresaRepository.listarTodos();
		listaPerfisUsuario.clear();
		listaPerfis.clear();
		List<Perfil> listAllPerfis = perfilRepository.listAll();
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setAtivo(true);
			usuario.getColaborador().getEndereco().setEstado(Estado.AM);
			usuario.getColaborador().getEndereco().setCidade("Manaus");
		} else {
			for (Perfil p : usuario.getPerfis()) {
				listaPerfisUsuario.add(p.getId().toString());
			}
		}
		for (Perfil p : listAllPerfis) {
			if (!listaPerfisUsuario.contains(p.getId().toString())) {
				listaPerfis.add(p.getId().toString());
			}
		}
		perfisModel = new DualListModel<>(listaPerfis, listaPerfisUsuario);
	}

	public boolean isEditando() {
		return usuario != null && usuario.getId() != null;
	}

	public Estado[] getEstados() {
		return Estado.values();
	}

	public void salvar() {
		List<Perfil> lista = new ArrayList<>();
		for (String id : perfisModel.getTarget()) {
			Perfil p = perfilRepository.porId(Long.parseLong(id));
			lista.add(p);
		}
		usuario.setPerfis(lista);
		usuario = service.salvar(usuario);
		if (usuario != null) {
			FacesUtil.addInfoMessage("Registro salvo com sucesso!");
			inicializar();
		}

	}

	public String descricaoPerfil(String id) {
		Perfil p = perfilRepository.porId(Long.parseLong(id));
		return p.getDescricao();
	}

	public void atualizarLogin() {
		if (usuario.getLogin() == null && usuario.getId() == null && usuario.getColaborador().getNome() != null) {
			usuario.setLogin(Utils.retornaPrimeiroEUltimoNome(usuario.getColaborador().getNome()));
		}
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isFuncionarioInserir();
	}
	
	public boolean isPermissaoSalvar() {
		return (this.isEditando() && this.seguranca.isFuncionarioEditar()) || (!this.isEditando() && this.seguranca.isFuncionarioInserir());
	}
	

}
