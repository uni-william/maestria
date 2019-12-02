package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.Material;
import br.com.sis.repository.MaterialRepository;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class MaterialService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MaterialRepository materialRepository;

	@Transactional
	public Material salvar(Material material) {
		Material materialExistente = materialRepository.porDescricao(material.getDescricao());
		if (materialExistente != null && !materialExistente.equals(material)) {
			FacesUtil.addErroMessage("Já existe material cadastrado com essa descrição");
			return null;
		} else {
			return materialRepository.salvar(material);
		}
	}

}
