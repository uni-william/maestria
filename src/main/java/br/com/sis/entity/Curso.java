package br.com.sis.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.sis.util.Extenso;

@Entity
@Table(name = "cursos")
public class Curso implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;
	private BigDecimal valor;
	private BigDecimal matricula;
	private Integer periodo;
	private String ementa;
	private BigDecimal cargaHorariaSemanal;
	private BigDecimal cargaHorariaTotal;
	private String metodologia;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 150)
	@NotEmpty
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	@NotEmpty
	@Column(columnDefinition = "TEXT")
	public String getEmenta() {
		return ementa;
	}

	public void setEmenta(String ementa) {
		this.ementa = ementa;
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

	public String getMetodologia() {
		return metodologia;
	}

	@Column(length = 100)
	public void setMetodologia(String metodologia) {
		this.metodologia = metodologia;
	}

	@Transient
	public String getValorExtenso() {
		if (this.getValor() != null) {
			return new Extenso(this.valor).toString();
		}
		return null;
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
		Curso other = (Curso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
