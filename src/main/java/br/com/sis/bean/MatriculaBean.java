package br.com.sis.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.sis.converter.DiaSemanaConverter;
import br.com.sis.entity.Aluno;
import br.com.sis.entity.Contrato;
import br.com.sis.entity.Curso;
import br.com.sis.enuns.DiaSemana;
import br.com.sis.enuns.StatusMatricula;
import br.com.sis.enuns.TipoMensalidade;
import br.com.sis.report.ExecutorRelatorio;
import br.com.sis.security.Seguranca;
import br.com.sis.service.ContratoService;
import br.com.sis.util.Extenso;
import br.com.sis.util.Utils;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class MatriculaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Curso curso;
	private Aluno aluno;
	private Contrato contrato;

	@Inject
	private ContratoService contratoService;

	@Inject
	private DiaSemanaConverter diaSemanaConverter;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private FacesContext facesContext;	
	
	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;
	
	@Inject
	private Seguranca seguranca;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public DiaSemanaConverter getDiaSemanaConverter() {
		return diaSemanaConverter;
	}

	public void setDiaSemanaConverter(DiaSemanaConverter diaSemanaConverter) {
		this.diaSemanaConverter = diaSemanaConverter;
	}

	public void inicializar() {
		if (this.contrato == null) {
			this.contrato = new Contrato();
			this.contrato.setAluno(this.aluno);
			this.contrato.setCurso(this.curso);
			this.contrato.setCargaHorariaSemanal(this.curso.getCargaHorariaSemanal());
			this.contrato.setCargaHorariaTotal(this.curso.getCargaHorariaTotal());
			this.contrato.setMatricula(this.curso.getMatricula());
			this.contrato.setValor(this.curso.getValor());
			this.contrato.setStatus(StatusMatricula.MATRICULADO);
			this.contrato.setTipoMensalidade(TipoMensalidade.MENSAL);
		}
		if ("true".equals(request.getParameter("geradas"))) {
			FacesUtil.addInfoMessage("Mensalidade(s) geradas com sucesso!");
		}
	}

	public TipoMensalidade[] getTiposMensalidade() {
		return TipoMensalidade.values();
	}

	public DiaSemana[] getDiasSemana() {
		return DiaSemana.values();
	}
	
	public StatusMatricula[] getStatusMatricula() {
		return StatusMatricula.values();
	}

	public void salvar() {
		contrato = contratoService.salvar(contrato);
		FacesUtil.addInfoMessage("Registro realizado com sucesso!");
	}

	public boolean isJaSalvo() {
		return contrato.getId() != null;
	}
	
	public boolean isMatriculado() {
		return contrato.getStatus().equals(StatusMatricula.MATRICULADO);
	}
	
	public boolean isSalvoEMatriculado() {
		return this.isJaSalvo() && this.isMatriculado();
	}
	
	public boolean isPermissaoGerarMensalidades() {
		return this.seguranca.isMensalidadeGerar();
	}
	
	private String numeroFormatado(Long id) {
		return String.format("%06d", id);
	}	
	
	public void emitirCarne() {
		Long idEmpresa = seguranca.getUsuarioLogado().getUsuario().getColaborador().getEmpresa().getId();
		String nomeRel = "Carne_" +  numeroFormatado(contrato.getId()) + ".pdf";
		String imagem = FacesUtil.localFotos() + "logo.png";
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("contrato", contrato.getId());
		parametros.put("logo", imagem);
		parametros.put("empresa", idEmpresa);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/Carne.jasper", this.response, parametros, nomeRel);
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		facesContext.responseComplete();
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil
					.addErroMessage("A execução do relatório não retornou dados.");
		}		
	}	
	
	public void emitirContrato() {
		Long idEmpresa = seguranca.getUsuarioLogado().getUsuario().getColaborador().getEmpresa().getId();
		String nomeRel = "Contrato_" +  numeroFormatado(contrato.getId()) + ".pdf";
		String logo = FacesUtil.localFotos() + "logo.png";
		String dataExtenso = Utils.dataPorExtenso(new Date());
		String matriculaExtenso = "(" + new Extenso(this.contrato.getMatricula()).toString() + ")";
		String mensalidadeExtenso = "(" + new Extenso(this.contrato.getValor()).toString() + ")";
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("contrato", contrato.getId());
		parametros.put("logo", logo);
		parametros.put("dataExtenso", dataExtenso);
		parametros.put("extensoMatricula", matriculaExtenso);
		parametros.put("extensoMensalidade", mensalidadeExtenso);
		parametros.put("empresa", idEmpresa);
		
		ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/Contrato.jasper", this.response, parametros, nomeRel);
		
		Session session = manager.unwrap(Session.class);
		session.doWork(executor);
		
		facesContext.responseComplete();
		
		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil
					.addErroMessage("A execução do relatório não retornou dados.");
		}		
	}	

}
