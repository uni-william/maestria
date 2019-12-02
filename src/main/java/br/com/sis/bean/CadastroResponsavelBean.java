package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aluno;
import br.com.sis.entity.Responsavel;
import br.com.sis.enuns.Estado;
import br.com.sis.repository.ContratoRepository;
import br.com.sis.security.Seguranca;
import br.com.sis.service.ResponsavelService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroResponsavelBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ResponsavelService responsavelService;
	
	@Inject
	private ContratoRepository contratoRepository;
	
	@Inject
	private Seguranca seguranca;
	
	private Responsavel responsavel;

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public void inicializar() {
		if (responsavel == null) {
			responsavel = new Responsavel();
		}
	}

	public void salvar() {
		responsavel = responsavelService.salvar(responsavel);
		FacesUtil.addInfoMessage("Registro realizado com sucesso!");
	}

	public boolean isEditando() {
		return this.responsavel.getId() != null;
	}

	public Estado[] getEstados() {
		return Estado.values();
	}
	
	public boolean jaPossuiCotnratoAtivo(Aluno aluno) {
		return contratoRepository.jaPossuiContratoAtivo(aluno);
	}
	
	public String captionBotaoMatricular(Aluno aluno) {
		if (contratoRepository.jaPossuiContratoAtivo(aluno)) {
			return "Matriculado";
		} else {
			return "Matricular";
		}
	}
	
	public boolean isPermissaoNovo() {
		return seguranca.isResponsavelInserir();
	}
	
	public boolean isPermissaoSalvar() {
		return (this.isEditando() && this.seguranca.isResponsavelEditar()) || (!this.isEditando() && this.seguranca.isResponsavelInserir());
	}
	
	public boolean isPermissaoNovoAluno() {
		return this.seguranca.isAlunoInserir();
	}
	
	public boolean isPermissaoMatricular() {
		return this.seguranca.isPermissaoMatricular();
	}
	
}
