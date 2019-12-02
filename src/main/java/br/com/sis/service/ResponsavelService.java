package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.Responsavel;
import br.com.sis.repository.ResponsavelRepository;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class ResponsavelService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ResponsavelRepository responsavelRepository;

	@Transactional
	public Responsavel salvar(Responsavel responsavel) {
		Responsavel responsavelExistente = responsavelRepository.porCPF(responsavel.getCpf());
		if (responsavelExistente != null && !responsavelExistente.equals(responsavel)) {
			FacesUtil.addErroMessage("Já existe responsável cadastrado com esse CPF " + responsavel.getCpf());
			return null;
		} else {
			return responsavelRepository.salvar(responsavel);
		}
	}

}
