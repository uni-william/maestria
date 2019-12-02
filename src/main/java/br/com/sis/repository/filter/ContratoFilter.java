package br.com.sis.repository.filter;

import br.com.sis.entity.Curso;
import br.com.sis.enuns.StatusMatricula;

public class ContratoFilter {

	private String nomeResponsavel;
	private String nomeAluno;
	private String cpf;
	private Curso curso;
	private StatusMatricula statusMatricula;

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public StatusMatricula getStatusMatricula() {
		return statusMatricula;
	}

	public void setStatusMatricula(StatusMatricula statusMatricula) {
		this.statusMatricula = statusMatricula;
	}

}
