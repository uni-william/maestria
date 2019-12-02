package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.Curso;
import br.com.sis.repository.CursoRepository;
import br.com.sis.util.jpa.Transactional;

public class CursoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CursoRepository cursoRepository;

	@Transactional
	public Curso salvar(Curso curso) {
		return cursoRepository.salvar(curso);
	}

}
