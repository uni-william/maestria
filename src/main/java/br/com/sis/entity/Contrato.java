package br.com.sis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sis.enuns.DiaSemana;
import br.com.sis.enuns.StatusMatricula;
import br.com.sis.enuns.TipoMensalidade;

@Entity
@Table(name = "contratos")
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Aluno aluno = new Aluno();
	private Curso curso = new Curso();
	private StatusMatricula status;
	private BigDecimal valor;
	private BigDecimal matricula;
	private BigDecimal cargaHorariaSemanal;
	private BigDecimal cargaHorariaTotal;
	private TipoMensalidade tipoMensalidade;
	private List<Mensalidade> mensalidades = new ArrayList<Mensalidade>();
	private List<DiaSemana> diasSemana;
	private Date dataCriacao;
	private Date dataAlteracao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "aluno_id", nullable = false)
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@ManyToOne
	@JoinColumn(name = "curso_id", nullable = false)
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public StatusMatricula getStatus() {
		return status;
	}

	public void setStatus(StatusMatricula status) {
		this.status = status;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getMatricula() {
		return matricula;
	}

	public void setMatricula(BigDecimal matricula) {
		this.matricula = matricula;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getCargaHorariaSemanal() {
		return cargaHorariaSemanal;
	}

	public void setCargaHorariaSemanal(BigDecimal cargaHorariaSemanal) {
		this.cargaHorariaSemanal = cargaHorariaSemanal;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getCargaHorariaTotal() {
		return cargaHorariaTotal;
	}

	public void setCargaHorariaTotal(BigDecimal cargaHorariaTotal) {
		this.cargaHorariaTotal = cargaHorariaTotal;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	public TipoMensalidade getTipoMensalidade() {
		return tipoMensalidade;
	}

	public void setTipoMensalidade(TipoMensalidade tipoMensalidade) {
		this.tipoMensalidade = tipoMensalidade;
	}

	@OneToMany(mappedBy = "contrato")
	public List<Mensalidade> getMensalidades() {
		return mensalidades;
	}

	public void setMensalidades(List<Mensalidade> mensalidades) {
		this.mensalidades = mensalidades;
	}

	@ElementCollection
	@CollectionTable(name = "dias_aula", joinColumns = @JoinColumn(name = "contrato_id"))
	@Column(name = "dia_semana")
	@Enumerated(EnumType.STRING)
	public List<DiaSemana> getDiasSemana() {
		return diasSemana;
	}

	public void setDiasSemana(List<DiaSemana> diasSemana) {
		this.diasSemana = diasSemana;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contrato other = (Contrato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
