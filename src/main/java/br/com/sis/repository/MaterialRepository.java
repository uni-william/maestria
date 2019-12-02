package br.com.sis.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.sis.entity.Material;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class MaterialRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Material porId(Long id) {
		return manager.find(Material.class, id);
	}
	
	public Material salvar(Material material) {
		return manager.merge(material);
	}
	
	@Transactional
	public boolean remover(Material material) {
		try {
			material = porId(material.getId());
			manager.remove(material);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Material não pode ser excluído. " + e.getMessage());
			return false;
		}
	}
	
	public List<Material> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Material> criteriaQuery = builder.createQuery(Material.class);
		Root<Material> materialRoot = criteriaQuery.from(Material.class);
		criteriaQuery.select(materialRoot);
		TypedQuery<Material> query = manager.createQuery(criteriaQuery);
		List<Material> lista = query.getResultList();
		return lista;
	}
	
	public Material porDescricao(String descricao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Material> criteriaQuery = builder.createQuery(Material.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Material> materialRoot = criteriaQuery.from(Material.class);
		predicates.add(builder.equal(builder.lower(materialRoot.get("descricao")), descricao.toLowerCase()));
		criteriaQuery.select(materialRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Material> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}		
	
}
