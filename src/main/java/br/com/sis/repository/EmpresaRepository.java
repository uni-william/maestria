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

import br.com.sis.entity.Empresa;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class EmpresaRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Empresa porId(Long id) {
		return manager.find(Empresa.class, id);
	}
	
	public Empresa salvar(Empresa empresa) {
		return manager.merge(empresa);
	}
	
	@Transactional
	public boolean remover(Empresa empresa) {
		try {
			empresa = porId(empresa.getId());
			manager.remove(empresa);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Empresa não pode ser excluída. " + e.getMessage());
			return false;
		}
	}
	
	public List<Empresa> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteriaQuery = builder.createQuery(Empresa.class);
		Root<Empresa> empresaRoot = criteriaQuery.from(Empresa.class);
		criteriaQuery.select(empresaRoot);
		criteriaQuery.orderBy(builder.asc(empresaRoot.get("nomeFantasia")));
		TypedQuery<Empresa> query = manager.createQuery(criteriaQuery);
		List<Empresa> lista = query.getResultList();
		return lista;
	}
	
	public Empresa porCNPJ(String cnpj) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Empresa> criteriaQuery = builder.createQuery(Empresa.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Empresa> empresaRoot = criteriaQuery.from(Empresa.class);
		predicates.add(builder.equal(empresaRoot.get("cnpj"), cnpj));
		criteriaQuery.select(empresaRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Empresa> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	

}
