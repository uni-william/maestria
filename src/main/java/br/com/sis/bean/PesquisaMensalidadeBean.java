package br.com.sis.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Mensalidade;
import br.com.sis.enuns.StatusMatricula;
import br.com.sis.repository.MensalidadeRepository;
import br.com.sis.repository.filter.MensalidadeFilter;
import br.com.sis.security.Seguranca;
import br.com.sis.service.MensalidadeService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaMensalidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MensalidadeRepository mensalidadeRepository;
	
	@Inject
	private MensalidadeService mensalidadeService;
	
	@Inject
	private Seguranca seguranca;

	private List<Mensalidade> mensalidades = new ArrayList<Mensalidade>();
	private MensalidadeFilter mensalidadeFilter;
	private Mensalidade mensalidade;

	public List<Mensalidade> getMensalidades() {
		return mensalidades;
	}

	public MensalidadeFilter getMensalidadeFilter() {
		return mensalidadeFilter;
	}

	public void setMensalidadeFilter(MensalidadeFilter mensalidadeFilter) {
		this.mensalidadeFilter = mensalidadeFilter;
	}

	public Mensalidade getMensalidade() {
		return mensalidade;
	}

	public void setMensalidade(Mensalidade mensalidade) {
		this.mensalidade = mensalidade;
	}


	public void inicializar() {
		mensalidade = new Mensalidade();
		mensalidadeFilter = new MensalidadeFilter();
	}

	public void pesquisar() {
		mensalidades = mensalidadeRepository.filtrados(mensalidadeFilter);
	}

	public void excluir() {
		this.mensalidadeRepository.remover(mensalidade);
		FacesUtil.addInfoMessage("Registro excluído com sucesso!");
		this.pesquisar();
	}

	public StatusMatricula[] getStatusMatriculas() {
		return StatusMatricula.values();
	}
	
	public void limparPagamento() {
		this.mensalidade.setPagamento(null);
		this.mensalidade.setValorPago(null);
		this.salvar();
	}
	
	public void salvar() {
		this.mensalidade = mensalidadeService.salvar(this.mensalidade);
		this.pesquisar();
	}
	
	public boolean isPermissaoEditar() {
		return this.seguranca.isMensalidadeEditar();
	}
	
	public boolean isPermissaoLimparPagamento() {
		return this.seguranca.isMensalidadeLimparPagamento();
	}
	
	public boolean isPermissaoExcluir() {
		return this.seguranca.isMensalidadeExcluir();
	}

}
