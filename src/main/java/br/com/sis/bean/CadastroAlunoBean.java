package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aluno;
import br.com.sis.entity.Responsavel;
import br.com.sis.enuns.Estado;
import br.com.sis.service.AlunoService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroAlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoService alunoService;

	private Aluno aluno;
	private Responsavel responsavel;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public void inicializar() {
		if (this.aluno == null && this.responsavel != null) {
			this.aluno = new Aluno();
			this.aluno.setResponsavel(this.responsavel);
		} else if (this.responsavel == null && this.aluno == null) {
			FacesUtil.redirecionarPagina("/Erro.xhtml");
		}
	}

	public void salvar() {
		boolean isEdit = this.isEditando(); 
		aluno = alunoService.salvar(aluno);
		if (isEdit) {
			FacesUtil.addInfoMessage("Registro realizado com sucesso!");
		} else {
			String url = "/responsaveis/CadastroResponsavel.xhtml?responsavel=" + this.aluno.getResponsavel().getId().toString();
			FacesUtil.redirecionarPagina(url);
		}
	}

	public boolean isEditando() {
		return this.aluno.getId() != null;
	}

	public Estado[] getEstados() {
		return Estado.values();
	}
	
	public String getCaptionSalvar() {
		if (this.isEditando()) {
			return "Salvar aluno";
		} else {
			return "Salvar e retornar para responsável";
		}
	}

	public void carregarDadosResponsavel() {
		if (this.aluno.isAlunoEResponsavel() == true) {
			this.aluno.setNome(this.aluno.getResponsavel().getNome());
			this.aluno.setEmail(this.aluno.getResponsavel().getEmail());
			this.aluno.setContato(this.aluno.getResponsavel().getCelular());
		}
	}
}
