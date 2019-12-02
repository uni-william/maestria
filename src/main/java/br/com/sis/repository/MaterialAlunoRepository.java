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

import br.com.sis.entity.Aluno;
import br.com.sis.entity.MaterialAluno;
import br.com.sis.util.jpa.Transactional;
import br.com.sis.util.jsf.FacesUtil;

public class MaterialAlunoRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public MaterialAluno porId(Long id) {
		return manager.find(MaterialAluno.class, id);
	}
	
	public MaterialAluno salvar(MaterialAluno materialAluno) {
		return manager.merge(materialAluno);
	}
	
	@Transactional
	public boolean remover(MaterialAluno materialAluno) {
		try {
			materialAluno = porId(materialAluno.getId());
			manager.remove(materialAluno);
			manager.flush();
			return true;
		} catch (PersistenceException e) {
			FacesUtil.addErroMessage("Material do Aluno não pode ser excluído. " + e.getMessage());
			return false;
		}
	}
	
	public List<MaterialAluno> listarTodos() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<MaterialAluno> criteriaQuery = builder.createQuery(MaterialAluno.class);
		Root<MaterialAluno> materialAlunoRoot = criteriaQuery.from(MaterialAluno.class);
		criteriaQuery.select(materialAlunoRoot);
		TypedQuery<MaterialAluno> query = manager.createQuery(criteriaQuery);
		List<MaterialAluno> lista = query.getResultList();
		return lista;
	}
	
	public List<MaterialAluno> porAluno(Aluno aluno) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<MaterialAluno> criteriaQuery = builder.createQuery(MaterialAluno.class);
		List<Predicate> predicates = new ArrayList<>();
		Root<MaterialAluno> materialAlunoRoot = criteriaQuery.from(MaterialAluno.class);
		predicates.add(builder.equal(builder.lower(materialAlunoRoot.get("aluno")), aluno));
		criteriaQuery.select(materialAlunoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<MaterialAluno> query = manager.createQuery(criteriaQuery);
		List<MaterialAluno> lista = query.getResultList();
		return lista;	
	}
	
	public Long numeroMaxiArquivo(Aluno aluno) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<MaterialAluno> materialAlunoRoot = criteriaQuery.from(MaterialAluno.class);
		criteriaQuery.select(builder.max(materialAlunoRoot.get("id")));
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(materialAlunoRoot.get("aluno"), aluno));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		Long resultado;
		TypedQuery<Long> query = manager.createQuery(criteriaQuery);
		try {
			resultado = query.getSingleResult();
			if (resultado == null) {
				resultado = (long) 0;
			}
		} catch (NoResultException e) {
			resultado = (long) 0;
		}

		return resultado;
	}	
	
}
