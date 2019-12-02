package br.com.sis.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Aluno;
import br.com.sis.entity.Contrato;
import br.com.sis.entity.Responsavel;
import br.com.sis.enuns.StatusMatricula;
import br.com.sis.repository.filter.ContratoFilter;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class ContratoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Contrato porId(Long id) {
		return manager.find(Contrato.class, id);
	}
	
	public Contrato salvar(Contrato contrato) {
		return manager.merge(contrato);
	}
	
	@Transactional
	public boolean remover(Contrato contrato) {
		try {
			contrato = porId(contrato.getId());
			manager.remove(contrato);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Matrícula não pode ser excluída. " + e.getMessage());
			return false;
		}
	}
	
	public List<Contrato> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Contrato> criteriaQuery = builder.createQuery(Contrato.class);
		Root<Contrato> contratoRoot = criteriaQuery.from(Contrato.class);
		criteriaQuery.select(contratoRoot);
		TypedQuery<Contrato> query = manager.createQuery(criteriaQuery);
		List<Contrato> lista = query.getResultList();
		return lista;
	}	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Contrato> filtrados(ContratoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Contrato> criteriaQuery = builder.createQuery(Contrato.class);
		Root<Contrato> contratoRoot = criteriaQuery.from(Contrato.class);
		Join<Aluno, Contrato> alunoRoot = (Join) contratoRoot.fetch("aluno");
		Join<Responsavel, Aluno> respRoot = (Join) alunoRoot.fetch("responsavel");
		contratoRoot.fetch("curso", JoinType.INNER);
		criteriaQuery.select(contratoRoot);
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(filter.getNomeAluno())) {
			predicates
			.add(builder.like(builder.lower(alunoRoot.get("nome")), "%" + filter.getNomeAluno().toLowerCase() + "%"));
		}
		if (StringUtils.isNotBlank(filter.getNomeResponsavel())) {
			predicates
			.add(builder.like(builder.lower(respRoot.get("nome")), "%" + filter.getNomeAluno().toLowerCase() + "%"));
		}		
		
		if (StringUtils.isNotBlank(filter.getCpf())) {
			predicates.add(builder.equal(respRoot.get("cpf"), filter.getCpf()));
		}
		
		if (filter.getCurso() != null) {
			predicates.add(builder.equal(contratoRoot.get("curso"), filter.getCurso()));
		}
		
		if (filter.getStatusMatricula() != null) {
			predicates.add(builder.equal(contratoRoot.get("status"), filter.getStatusMatricula()));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.desc(respRoot.get("nome")));
		TypedQuery<Contrato> query = manager.createQuery(criteriaQuery);
		List<Contrato> lista = query.getResultList();
		return lista;
	}
	
	
	
	public boolean jaPossuiContratoAtivo(Aluno aluno) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Contrato> contratoRoot = criteriaQuery.from(Contrato.class);
		criteriaQuery.select(builder.count(criteriaQuery.from(Contrato.class)));
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(contratoRoot.get("aluno"), aluno));
		predicates.add(builder.equal(contratoRoot.get("status"), StatusMatricula.MATRICULADO));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Long> query = manager.createQuery(criteriaQuery);
		Long resultado = query.getSingleResult();
		
		return resultado > 0;		
	}
}
