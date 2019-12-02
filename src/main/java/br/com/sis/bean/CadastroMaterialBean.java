package br.com.sis.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Material;
import br.com.sis.enuns.Estado;
import br.com.sis.service.MaterialService;
import br.com.sis.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroMaterialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MaterialService materialService;
	
	
	private Material material;


	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void inicializar() {
		if (material == null) {
			material = new Material();
		}
	}

	public void salvar() {
		materialService.salvar(material);
		FacesUtil.addInfoMessage("Registro realizado com sucesso!");
		material = new Material();
	}

	public boolean isEditando() {
		return this.material.getId() != null;
	}
	
	public Estado[] getEstados() {
		return Estado.values();
	}
}
