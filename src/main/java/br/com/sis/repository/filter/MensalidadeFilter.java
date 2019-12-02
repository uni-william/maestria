package br.com.sis.repository.filter;

import java.util.Date;

import br.com.sis.enuns.StatusMatricula;

public class MensalidadeFilter {

	private String nomeResponsavel;
	private String cpf;
	private String nomeAluno;
	private Date dataVenctoIni;
	private Date dataVenctoFim;
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

	public Date getDataVenctoIni() {
		return dataVenctoIni;
	}

	public void setDataVenctoIni(Date dataVenctoIni) {
		this.dataVenctoIni = dataVenctoIni;
	}

	public Date getDataVenctoFim() {
		return dataVenctoFim;
	}

	public void setDataVenctoFim(Date dataVenctoFim) {
		this.dataVenctoFim = dataVenctoFim;
	}

	public StatusMatricula getStatusMatricula() {
		return statusMatricula;
	}

	public void setStatusMatricula(StatusMatricula statusMatricula) {
		this.statusMatricula = statusMatricula;
	}

}
