package br.com.sis.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.sis.entity.Empresa;
import br.com.sis.repository.EmpresaRepository;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class EmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaRepository empresaRepository;

	@Transactional
	public Empresa salvar(Empresa empresa) {
		Empresa empresaExistente = empresaRepository.porCNPJ(empresa.getCnpj());
		if (empresaExistente != null && !empresaExistente.equals(empresa)) {
			FacesUtil.addErroMessage("Já existe empresa cadastrada com esse CNPJ " + empresa.getCnpj());
			return null;
		} else {
			return empresaRepository.salvar(empresa);
		}
	}

}
