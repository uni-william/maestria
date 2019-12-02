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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.sis.entity.Aluno;
import br.com.sis.entity.Contrato;
import br.com.sis.entity.Responsavel;
import br.com.sis.enuns.DiaSemana;
import br.com.sis.enuns.StatusMatricula;
import br.com.sis.repository.filter.AlunoFilter;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class AlunoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Aluno porId(Long id) {
		return manager.find(Aluno.class, id);
	}
	
	public Aluno salvar(Aluno aluno) {
		return manager.merge(aluno);
	}
	
	@Transactional
	public boolean remover(Aluno aluno) {
		try {
			aluno = porId(aluno.getId());
			manager.remove(aluno);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Aluno não pode ser excluído. " + e.getMessage());
			return false;
		}
	}
	
	public List<Aluno> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aluno> criteriaQuery = builder.createQuery(Aluno.class);
		Root<Aluno> alunoRoot = criteriaQuery.from(Aluno.class);
		criteriaQuery.select(alunoRoot);
		criteriaQuery.orderBy(builder.asc(alunoRoot.get("nome")));
		TypedQuery<Aluno> query = manager.createQuery(criteriaQuery);
		List<Aluno> lista = query.getResultList();
		return lista;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aluno> porDia(DiaSemana diaSemana) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aluno> criteriaQuery = builder.createQuery(Aluno.class);
		Root<Aluno> alunoRoot = criteriaQuery.from(Aluno.class);
		Join<Contrato, Aluno> contratoRoot = (Join) alunoRoot.fetch("cursos");
		criteriaQuery.select(alunoRoot);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(contratoRoot.get("status"), StatusMatricula.MATRICULADO));
		predicates.add(builder.isMember(diaSemana, contratoRoot.get("diasSemana")));
		
		List<DiaSemana> dias = new ArrayList<DiaSemana>();
		dias.clear();
		dias.add(diaSemana);
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));		
		
		criteriaQuery.orderBy(builder.asc(alunoRoot.get("nome")));
		TypedQuery<Aluno> query = manager.createQuery(criteriaQuery);
		List<Aluno> lista = query.getResultList();
		return lista;		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Aluno> filtrados(AlunoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Aluno> criteriaQuery = builder.createQuery(Aluno.class);
		Root<Aluno> alunoRoot = criteriaQuery.from(Aluno.class);
		Join<Responsavel, Aluno> responsavelRoot = (Join) alunoRoot.fetch("responsavel");
		criteriaQuery.select(alunoRoot);
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(filter.getNomeAluno())) {
			predicates
			.add(builder.like(builder.lower(alunoRoot.get("nome")), "%" + filter.getNomeAluno().toLowerCase() + "%"));
		}
		if (StringUtils.isNotBlank(filter.getNomeResponsavel())) {
			predicates
			.add(builder.like(builder.lower(responsavelRoot.get("nome")), "%" + filter.getNomeResponsavel().toLowerCase() + "%"));
		}		
		
		if (StringUtils.isNotBlank(filter.getCpf())) {
			predicates.add(builder.equal(responsavelRoot.get("cpf"), filter.getCpf()));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(alunoRoot.get("nome")));
		TypedQuery<Aluno> query = manager.createQuery(criteriaQuery);
		List<Aluno> lista = query.getResultList();
		return lista;		
	}
	
}
