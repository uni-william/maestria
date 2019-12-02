package br.com.sis.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Aluno;
import br.com.sis.enuns.DiaSemana;
import br.com.sis.repository.AlunoRepository;

@Named
@ViewScoped
public class ConsultaAlunosDiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoRepository alunoRepository;
	private Date data;

	private List<Aluno> alunos;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public void inicializar() {
		this.data = new Date();
		this.pesquisa();
	}

	public DiaSemana[] DiasSemana() {
		return DiaSemana.values();
	}

	public void pesquisa(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		DiaSemana diaSemana = DiaSemana.SEGUNDA;
		int dia = cal.get(Calendar.DAY_OF_WEEK);
		switch (dia) {
		case 1:
			diaSemana = DiaSemana.DOMINGO;
			break;
		case 2:
			diaSemana = DiaSemana.SEGUNDA;
			break;
		case 3:
			diaSemana = DiaSemana.TERCA;
			break;
		case 4:
			diaSemana = DiaSemana.QUARTA;
			break;
		case 5:
			diaSemana = DiaSemana.QUINTA;
			break;
		case 6:
			diaSemana = DiaSemana.SEXTA;
			break;
		case 7:
			diaSemana = DiaSemana.SABADO;
			break;
		}
		
		alunos = alunoRepository.porDia(diaSemana);
	}

}
