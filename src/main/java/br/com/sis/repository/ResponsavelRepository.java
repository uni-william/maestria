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

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Responsavel;
import br.com.sis.repository.filter.ResponsavelFilter;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class ResponsavelRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Responsavel porId(Long id) {
		return manager.find(Responsavel.class, id);
	}
	
	public Responsavel salvar(Responsavel responsavel) {
		return manager.merge(responsavel);
	}
	
	@Transactional
	public boolean remover(Responsavel responsavel) {
		try {
			responsavel = porId(responsavel.getId());
			manager.remove(responsavel);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Responsavel não pode ser excluído. " + e.getMessage());
			return false;
		}
	}
	
	public List<Responsavel> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Responsavel> criteriaQuery = builder.createQuery(Responsavel.class);
		Root<Responsavel> responsavelRoot = criteriaQuery.from(Responsavel.class);
		criteriaQuery.select(responsavelRoot);
		criteriaQuery.orderBy(builder.asc(responsavelRoot.get("nome")));
		TypedQuery<Responsavel> query = manager.createQuery(criteriaQuery);
		List<Responsavel> lista = query.getResultList();
		return lista;
	}
	
	public Responsavel porCPF(String cpf) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Responsavel> criteriaQuery = builder.createQuery(Responsavel.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<Responsavel> responsavelRoot = criteriaQuery.from(Responsavel.class);
		predicates.add(builder.equal(responsavelRoot.get("cpf"), cpf));
		criteriaQuery.select(responsavelRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Responsavel> query = manager.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Responsavel> filtrados(ResponsavelFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Responsavel> criteriaQuery = builder.createQuery(Responsavel.class);
		Root<Responsavel> responsavelRoot = criteriaQuery.from(Responsavel.class);
		criteriaQuery.select(responsavelRoot);
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(filter.getNomeResponsavel())) {
			predicates
			.add(builder.like(builder.lower(responsavelRoot.get("nome")), "%" + filter.getNomeResponsavel().toLowerCase() + "%"));
		}		
		
		if (StringUtils.isNotBlank(filter.getCpf())) {
			predicates.add(builder.equal(responsavelRoot.get("cpf"), filter.getCpf()));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(responsavelRoot.get("nome")));
		TypedQuery<Responsavel> query = manager.createQuery(criteriaQuery);
		List<Responsavel> lista = query.getResultList();
		return lista;		
	}
}
