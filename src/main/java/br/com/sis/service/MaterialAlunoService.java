package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.MaterialAluno;
import br.com.sis.repository.MaterialAlunoRepository;
import br.com.sis.util.jpa.Transactional;

public class MaterialAlunoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MaterialAlunoRepository materialAlunoRepository;

	@Transactional
	public MaterialAluno salvar(MaterialAluno materialAluno) {
		return materialAlunoRepository.salvar(materialAluno);
	}

}
