package br.com.sis.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sis.entity.Material;
import br.com.sis.repository.MaterialRepository;

@Named
@ViewScoped
public class PesquisaMaterialBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MaterialRepository materialRepository;
		
	private List<Material> materiais;
	private Material material = new Material();

	public List<Material> getMateriais() {
		return materiais;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void inicializar() {
		this.materiais = materialRepository.listarTodos();
	}
	
	public void excluir() {
		this.materialRepository.remover(material);
	}
	
}
