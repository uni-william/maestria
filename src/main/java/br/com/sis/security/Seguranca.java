package br.com.sis.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.sis.entity.Empresa;
import br.com.sis.enuns.Funcionalidade;

@Named
@RequestScoped
public class Seguranca {

	@Inject
	private ExternalContext externalContext;

	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();

		}
		return usuario;
	}

	public String getNomeUsuario() {
		String nome = null;
		UsuarioSistema user = getUsuarioLogado();
		if (user != null) {
			nome = user.getUsuario().getColaborador().getNome();
		}
		return nome;
	}

	public String getNomeEmpresaUsuario() {
		String nomeCreche = null;
		UsuarioSistema user = getUsuarioLogado();
		nomeCreche = user.getUsuario().getColaborador().getEmpresa().getNomeFantasia();
		return nomeCreche;
	}

	public Empresa getCrecheUsuarioLogado() {
		Empresa empresa = null;
		UsuarioSistema user = getUsuarioLogado();
		empresa = user.getUsuario().getColaborador().getEmpresa();

		return empresa;
	}

	public boolean isPerfilInserir() {
		return externalContext.isUserInRole(Funcionalidade.PERFIL_INSERT.toString());
	}

	public boolean isPerfilEditar() {
		return externalContext.isUserInRole(Funcionalidade.PERFIL_EDIT.toString());
	}

	public boolean isPerfilExcluir() {
		return externalContext.isUserInRole(Funcionalidade.PERFIL_DEL.toString());
	}

	public boolean isFuncionarioInserir() {
		return externalContext.isUserInRole(Funcionalidade.FUNC_INSERT.toString());
	}

	public boolean isFuncionarioEditar() {
		return externalContext.isUserInRole(Funcionalidade.FUNC_EDIT.toString());
	}

	public boolean isFuncionarioExcluir() {
		return externalContext.isUserInRole(Funcionalidade.FUNC_DEL.toString());
	}

	public boolean isEmpresaInserir() {
		return externalContext.isUserInRole(Funcionalidade.EMPRESA_INSERT.toString());
	}

	public boolean isEmpresaEditar() {
		return externalContext.isUserInRole(Funcionalidade.EMPRESA_EDIT.toString());
	}

	public boolean isEmpresaExcluir() {
		return externalContext.isUserInRole(Funcionalidade.EMPRESA_DEL.toString());
	}

	public boolean isAlunoInserir() {
		return externalContext.isUserInRole(Funcionalidade.ALUNO_INSERT.toString());
	}

	public boolean isAlunoEditar() {
		return externalContext.isUserInRole(Funcionalidade.ALUNO_EDIT.toString());
	}

	public boolean isAlunoExcluir() {
		return externalContext.isUserInRole(Funcionalidade.ALUNO_DEL.toString());
	}

	public boolean isCursoInserir() {
		return externalContext.isUserInRole(Funcionalidade.CURSO_INSERT.toString());
	}

	public boolean isCursoEditar() {
		return externalContext.isUserInRole(Funcionalidade.CURSO_EDIT.toString());
	}

	public boolean isCursoExcluir() {
		return externalContext.isUserInRole(Funcionalidade.CURSO_DEL.toString());
	}

	public boolean isResponsavelInserir() {
		return externalContext.isUserInRole(Funcionalidade.RESPONSAVEL_INSERT.toString());
	}

	public boolean isResponsavelEditar() {
		return externalContext.isUserInRole(Funcionalidade.RESPONSAVEL_EDIT.toString());
	}

	public boolean isResponsavelExcluir() {
		return externalContext.isUserInRole(Funcionalidade.RESPONSAVEL_DEL.toString());
	}

	public boolean isMensalidadeGerar() {
		return externalContext.isUserInRole(Funcionalidade.MENSALIDADE_GERAR.toString());
	}

	public boolean isMensalidadeEditar() {
		return externalContext.isUserInRole(Funcionalidade.MENSALIDADE_EDIT.toString());
	}

	public boolean isMensalidadeExcluir() {
		return externalContext.isUserInRole(Funcionalidade.MENSALIDADE_DEL.toString());
	}
	
	public boolean isMensalidadeLimparPagamento() {
		return externalContext.isUserInRole(Funcionalidade.MENSALIDADE_CLEAR.toString());
	}

	public boolean isPagarParcela() {
		return externalContext.isUserInRole(Funcionalidade.PAGAR_PARCELA.toString());
	}
	
	public boolean isPermissaoMatricular() {
		return externalContext.isUserInRole(Funcionalidade.MATRICULA.toString());
	}
	
	public boolean isContratoExcluir() {
		return externalContext.isUserInRole(Funcionalidade.CONTRATO_DEL.toString());
	}

}
