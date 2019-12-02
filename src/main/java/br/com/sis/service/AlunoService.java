package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.Aluno;
import br.com.sis.repository.AlunoRepository;
import br.com.sis.util.jpa.Transactional;

public class AlunoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoRepository alunoRepository;

	@Transactional
	public Aluno salvar(Aluno aluno) {
		return alunoRepository.salvar(aluno);
	}

}
