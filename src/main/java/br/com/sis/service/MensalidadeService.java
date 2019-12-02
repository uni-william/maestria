package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.Mensalidade;
import br.com.sis.repository.MensalidadeRepository;
import br.com.sis.util.jpa.Transactional;

public class MensalidadeService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MensalidadeRepository mensalidadeRepository;

	@Transactional
	public Mensalidade salvar(Mensalidade mensalidade) {
		return mensalidadeRepository.salvar(mensalidade);
	}

}
