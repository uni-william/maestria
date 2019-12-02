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

import br.com.sis.entity.Colaborador;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class ColaboradorRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Colaborador porId(Long id) {
		return manager.find(Colaborador.class, id);
	}
	
	public Colaborador salvar(Colaborador colaborador) {
		return manager.merge(colaborador);
	}
	
	@Transactional
	public boolean remover(Colaborador colaborador) {
		try {
			colaborador = porId(colaborador.getId());
			manager.remove(colaborador);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Colaborador não pode ser excluído. " + e.getMessage());
			return false;
		}
	}
	
	public List<Colaborador> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Colaborador> criteriaQuery = builder.createQuery(Colaborador.class);
		Root<Colaborador> colaboradorRoot = criteriaQuery.from(Colaborador.class);
		criteriaQuery.select(colaboradorRoot);
		TypedQuery<Colaborador> query = manager.createQuery(criteriaQuery);
		List<Colaborador> lista = query.getResultList();
		return lista;
	}	
	
	public Colaborador porCPF(String cpf) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Colaborador> criteriaQuery = builder.createQuery(Colaborador.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Colaborador> colaboradorRoot = criteriaQuery.from(Colaborador.class);
		predicates.add(builder.equal(colaboradorRoot.get("cpf"), cpf));
		criteriaQuery.select(colaboradorRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Colaborador> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	

}
