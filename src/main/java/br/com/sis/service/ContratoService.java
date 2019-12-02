package br.com.sis.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.sis.entity.Contrato;
import br.com.sis.repository.ContratoRepository;
import br.com.sis.util.jpa.Transactional;

public class ContratoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContratoRepository contratoRepository;

	@Transactional
	public Contrato salvar(Contrato contrato) {
		if (contrato.getId() == null) {
			contrato.setDataCriacao(new Date());
		} else {
			Contrato contratoExistente = contratoRepository.porId(contrato.getId());
			if (!contratoExistente.getStatus().equals(contrato.getStatus())) {
				contrato.setDataAlteracao(new Date());
			}
		}
		return contratoRepository.salvar(contrato);
	}

}
